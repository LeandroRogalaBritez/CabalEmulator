package cabal.packet.handler.chain;

import cabal.packet.Packet;

public interface PacketHandler {
    void handle(Packet packet);
}
