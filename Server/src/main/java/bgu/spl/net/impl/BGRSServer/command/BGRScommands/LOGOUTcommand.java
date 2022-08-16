package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class LOGOUTcommand extends Command {
    private short opCode;

    public LOGOUTcommand() {
        opCode = 4;
    }


    @Override
    public Command process(MessagingProtocolImpl protocol) {
        if (protocol.getUser() != null) {
                if (database.logoutUser(protocol.getUser(), protocol.getUser().getUserName()))
                    return new ACKcommand(opCode, null);
            }
        return new ERRcommand(opCode);  /// ERROR
    }

    @Override
    public String getOptionalByString() {
        return null;
    }

    @Override
    public short getOpcode() {
        return opCode;
    }
}
