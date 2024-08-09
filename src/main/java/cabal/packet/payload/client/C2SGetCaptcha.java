package cabal.packet.payload.client;


import cabal.packet.Packet;

public class C2SGetCaptcha {
    public final String userName;

    public C2SGetCaptcha(Packet packet) {
        this.userName = packet.getString(129);
    }
}
