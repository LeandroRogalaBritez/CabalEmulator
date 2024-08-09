package cabal.packet.payload.server;

import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.builder.PacketBuilderFactory;
import cabal.packet.builder.ServerPacket;
import cabal.packet.handler.AuthHandler;
import cabal.types.UInt16;
import cabal.types.UInt32;
import cabal.types.UInt8;

public class S2CPublicKey implements ServerPacket {
    public UInt8 unknown1;
    public UInt16 publicKeyLength;
    public byte[] payload;

    public S2CPublicKey() {
    }

    public S2CPublicKey(UInt8 unknown1, UInt16 publicKeyLength, byte[] payload) {
        this.unknown1 = unknown1;
        this.publicKeyLength = publicKeyLength;
        this.payload = payload;
    }
    
    @Override
    public Packet generate(PacketBuilderFactory packetBuilderFactory) {
        PacketBuilder packet = packetBuilderFactory.create(AuthHandler.CSC_PUBLIC_KEY);
        
        packet.putUInt8LE(unknown1);
        packet.putUInt16LE(publicKeyLength);
        packet.putByteArray(payload);

        return packet.build();
    }    
}