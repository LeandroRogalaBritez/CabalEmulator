package cabal.packet.handler;

import cabal.AuthState;
import cabal.ClientSession;
import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.handler.chain.AbstractAuthPacketHandler;
import cabal.types.UInt16;
import cabal.types.UInt32;

final class Connect2SvrHandler extends AbstractAuthPacketHandler {
    
    public Connect2SvrHandler() {
        super(AuthHandler.CSC_CONNECT2SVR, AuthState.CONNECTED);
    }
              
    @Override
    protected Boolean handleValue(Packet packet) {
        UInt32 secondXorKey = new UInt32(0x135EFE81l); // FIXME        
        ClientSession session = getCurrentSession();
                
        session.setState(AuthState.HANDSHAKED);
        session.sendPacket(make_S2C_ACK4CONNECT(secondXorKey, session));
        session.changeClientKey(session.getKEY_FACTORY().create(secondXorKey));
        return true;
    }
         
    private Packet make_S2C_ACK4CONNECT(UInt32 secondXorKey, ClientSession session){
        PacketBuilder packet = getCurrentSession().getPacketBuilderFactory().create(opcodeHandled);
                
        UInt16 recvXorKeyIdx = new UInt16(0x3942);
        
        packet.put(secondXorKey);
        packet.put(session.getAuthKey());
        packet.put(session.getUserIdx());
        packet.put(recvXorKeyIdx);               
               
        return packet.build();
    }
    
}
