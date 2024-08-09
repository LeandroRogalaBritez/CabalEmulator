package cabal.packet.handler;

import cabal.packet.Packet;
import cabal.packet.handler.chain.Chain;
import cabal.packet.handler.chain.ChainBuilder;
import cabal.packet.handler.chain.PacketHandler;

public final class AuthHandler implements PacketHandler {
    private final Chain<Boolean, Packet> handlers;
    public final static short CSC_CONNECT2SVR = (short)101;
    public final static short CSC_AUTHACCOUNT = (short)103;
    public final static short CSC_CHECKVERSION = (short)122;
    public final static short CSC_PUBLIC_KEY = (short) 2001;
    public final static short CSC_GET_CAPTCHA = (short) 2002;
    public final static short CSC_VERIFY_CAPTCHA = (short) 2003;
    public final static short S2C_DISCONECT_TIMER = (short) 2005;
    public final static short CSC_WAR_ENTRY_STATUS = (short) 5383;
    
    public AuthHandler() {
        this.handlers = buildHandlersChain();
    }
    
    private Chain<Boolean, Packet> buildHandlersChain(){
        ChainBuilder builder = ChainBuilder.create();
        
        builder
            .setLastElement(NullHandler.getInstance())
            .add(new Connect2SvrHandler())
            .add(new CheckVersionHandler())
            .add(new GetCaptchaHandler())
            .add(new VerifyCaptcha2SvrHandler())
            .add(new PublicKey2SvrHandler())
            .add(new WarEntryStatus2SvrHandler())
            .add(new AuthAccountHandler());
        
        return builder.build();
    }
        
    @Override
    public void handle(Packet packet) {
        System.out.println("Handling packet, opcode: {" + String.format("%04X", packet.getOpcode()) + "}, data: {" + packet.toByteString() + "}");
        handlers.handle(packet);
    }           
}
