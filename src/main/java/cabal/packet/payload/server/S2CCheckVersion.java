package cabal.packet.payload.server;

import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.builder.PacketBuilderFactory;
import cabal.packet.builder.ServerPacket;
import cabal.packet.handler.AuthHandler;
import cabal.types.UInt32;

public class S2CCheckVersion implements ServerPacket {
    public UInt32 clientVersion;
    public UInt32 serverMagicKey;
    public UInt32 reserved;

    public S2CCheckVersion() {
    }

    public S2CCheckVersion(UInt32 clientVersion, UInt32 serverMagicKey, UInt32 reserved) {
        this.clientVersion = clientVersion;
        this.serverMagicKey = serverMagicKey;
        this.reserved = reserved;
    }
    
    @Override
    public Packet generate(PacketBuilderFactory packetBuilderFactory) {
        PacketBuilder packet = packetBuilderFactory.create(AuthHandler.CSC_CHECKVERSION);
        
        packet.putUInt32LE(clientVersion);
        packet.putUInt32LE(serverMagicKey);
        packet.putUInt32LE(reserved);

        return packet.build();
    }    
}