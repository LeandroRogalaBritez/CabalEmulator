package cabal;

import cabal.captcha.CaptchaReader;
import cabal.crypt.RSA;
import cabal.crypt.CabalEncryptor;
import cabal.crypt.EP8KeyFactory;
import cabal.crypt.Key;
import cabal.crypt.KeyFactory;
import cabal.packet.Header;
import cabal.packet.Packet;
import cabal.packet.builder.EP8PacketBuilderFactory;
import cabal.packet.builder.PacketBuilderFactory;
import cabal.packet.builder.ServerPacket;
import cabal.packet.handler.AuthHandler;
import cabal.packet.handler.chain.PacketHandler;
import cabal.types.UInt16;
import cabal.types.UInt32;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientSession implements Runnable {
    private Socket clientSocket;
    private KeyFactory KEY_FACTORY;
    private Key key;
    private CabalEncryptor cabalEncryptor;
    private ByteBuffer bufferHeader;
    final static short PACKET_SIGNATURE = (short)0xB7E2;
    private final static ThreadLocal<ClientSession> CONTEXT_HOLDER;
    private AuthState state;
    private static PacketBuilderFactory PACKET_BUILDER_FACTORY;
    private UInt16 userIdx;
    private final static AtomicInteger sessionIndexGenerator;
    private UInt32 authKey;
    private final static SecureRandom authKeyGenerator;
    protected final PacketHandler packetHandler;
    private CaptchaReader captchaReader;
    private static RSA rsa;

    static{
        authKeyGenerator = new SecureRandom();
        CONTEXT_HOLDER = new ThreadLocal();
        sessionIndexGenerator = new AtomicInteger();

        try {
            rsa = new RSA();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RSA getRSA() {
        return rsa;
    }

    public static PacketBuilderFactory getPacketBuilderFactory() {
        return PACKET_BUILDER_FACTORY;
    }

    public KeyFactory getKEY_FACTORY() {
        return KEY_FACTORY;
    }

    protected static void unregisterClientSession(ClientSession session){
        synchronized(CONTEXT_HOLDER) {
            CONTEXT_HOLDER.remove();
        }
    }

    public ClientSession(Socket clientSocket) {
        this.packetHandler = new AuthHandler();
        this.userIdx = UInt16.valueOf(sessionIndexGenerator.incrementAndGet());
        PACKET_BUILDER_FACTORY = new EP8PacketBuilderFactory();
        this.state = AuthState.CONNECTED;
        this.KEY_FACTORY = new EP8KeyFactory();
        this.key = KEY_FACTORY.create();
        this.cabalEncryptor = new CabalEncryptor();
        this.clientSocket = clientSocket;
        this.bufferHeader = ByteBuffer.allocate(4);
        this.bufferHeader.order(ByteOrder.LITTLE_ENDIAN);
        try {
            captchaReader= new CaptchaReader();
            captchaReader.readCaptchaFile("D:\\Backup\\Programacao\\Projetos\\Intellij\\Games\\CabalEmulator\\src\\main\\resources\\6UM30.jpg");
            captchaReader.setName("6UM30");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        synchronized(authKeyGenerator) {
            this.authKey = UInt32.valueOf(authKeyGenerator.nextInt());
        }
    }

    public CaptchaReader getCaptchaReader() {
        return captchaReader;
    }

    public UInt32 getAuthKey() {
        return authKey;
    }

    public UInt16 getUserIdx() {
        return userIdx;
    }

    private InputStream read(InputStream source) {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int readCount = 0;
        try {
            readCount = source.read(readBuffer.array());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (readCount > 0) {
            return new ByteArrayInputStream(Arrays.copyOfRange(readBuffer.array(), 0, readCount));
        } else {
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    protected static void registerClientSession(ClientSession session){
        synchronized(CONTEXT_HOLDER){
            CONTEXT_HOLDER.set(session);
        }
    }

    public AuthState getState() {
        return state;
    }

    @Override
    public void run() {
        registerClientSession(this);
        boolean active = true;
        while (active) {
            try {
                InputStream in = read(clientSocket.getInputStream());
                Header header = readHeader(in);
                Packet packet = readPacket(header, in);
                packetHandler.handle(packet);
                System.out.println(packet);
            } catch (Exception ex) {
                ex.printStackTrace();
                try{
                    active = false;
                    clientSocket.close();
                }catch(Throwable t){
                    t.printStackTrace();
                }
            }
        }
        unregisterClientSession(this);
    }

    private Packet readPacket(Header header, InputStream in) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(header.getSize());
        buffer.put(header.getData());
        int packetBodyLength = header.getSize() - header.getData().length;
        if (in.read(buffer.array(), header.getData().length, packetBodyLength) == packetBodyLength) {
            cabalEncryptor.decrypt(buffer.array(), key);
            buffer.position(8);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            short opcode = buffer.getShort();
            return new Packet(header, opcode, buffer);
        }
        return null;
    }

    private Header readHeader(InputStream in) throws Exception {
        int available = in.available();
        int readCount;
        if ((readCount = in.read(bufferHeader.array())) == 0x04) {
            final byte decryptedHeader[] = cabalEncryptor.decryptHeader(available);
            if (decryptedHeader == null) {
                throw new Exception("Descrpyt Header invalido");
            }

            final ByteBuffer decBuffer = ByteBuffer.wrap(decryptedHeader);
            decBuffer.order(ByteOrder.LITTLE_ENDIAN);

            short signatureHeader = decBuffer.getShort();
            if (signatureHeader == PACKET_SIGNATURE) {
                short sizeHeader = decBuffer.getShort();
                return new Header(signatureHeader, sizeHeader, Arrays.copyOf(bufferHeader.array(), bufferHeader.limit()));
            } else {
                throw new Exception("Assinatura invalida");
            }
        } else {
            throw new Exception("Header invalido");
        }
    }

    public static ClientSession getCurrentSession(){
        return CONTEXT_HOLDER.get();
    }

    public synchronized void changeClientKey(Key newClientKey){
        this.key = newClientKey;
    }

    public void setState(AuthState state) {
        synchronized(state) {
            System.out.println("State transition {" + this.state + "} -> {" + state + "}");
            this.state = state;
        }
    }

    public boolean sendPacket(ServerPacket packet){
        if (packet == null) {
            throw new IllegalArgumentException("Param packet cannot be null.");
        }
        return sendPacket(packet.generate(getPacketBuilderFactory()));
    }

    public boolean sendPacket(Packet packet){
        try{
            System.out.println("Sending packet to client: {" + packet.toByteString() + "}");

            cabalEncryptor.encrypt(packet.getData(), key);

            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(packet.getData());
            outputStream.flush();
            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
