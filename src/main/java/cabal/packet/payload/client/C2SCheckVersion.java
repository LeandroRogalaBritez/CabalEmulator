package cabal.packet.payload.client;


import cabal.packet.Packet;
import cabal.types.UInt32;

public class C2SCheckVersion {
    public final UInt32 clientVersion;
    public final UInt32 debugVersion;
    public final UInt32 reserved;

    public C2SCheckVersion(Packet packet) {
        this.clientVersion = packet.getUInt32();
        this.debugVersion = packet.getUInt32();
        this.reserved = packet.getUInt32();
    }
}