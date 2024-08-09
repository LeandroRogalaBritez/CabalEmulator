package cabal.packet.handler;

import cabal.AuthState;
import cabal.ClientSession;
import cabal.packet.Packet;
import cabal.packet.handler.chain.AbstractAuthPacketHandler;
import cabal.packet.payload.server.S2CPublicKey;
import cabal.packet.payload.server.S2SWarEntryStatus;
import cabal.types.UInt16;
import cabal.types.UInt32;
import cabal.types.UInt8;

final class WarEntryStatus2SvrHandler extends AbstractAuthPacketHandler {

    public WarEntryStatus2SvrHandler() {
        super(AuthHandler.CSC_WAR_ENTRY_STATUS, AuthState.PUBLICKEY_INITIALIZED);
    }
              
    @Override
    protected Boolean handleValue(Packet packet) {
        S2SWarEntryStatus response = new S2SWarEntryStatus();
        response.unknown1 = new UInt8((short) 0x00);
        response.unknown2 = new UInt8((short) 0x00);
        response.unknown3 = new UInt8((short) 0x00);
        response.unknown4 = new UInt8((short) 0x00);
        response.receivedWarReward = new UInt32(0x00);
        getCurrentSession().sendPacket(response);
        return true;
    }
    
}
