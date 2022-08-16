package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class COURSESTATcommand extends Command {

    private short opCode;
    private short courseNum;


    public COURSESTATcommand(short courseNum) {
        opCode = 7;
        this.courseNum = courseNum;

    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        if (protocol.getUser().isLoggedIn()) {
            String optional = database.courseStatus(courseNum);
            if (optional != null) {
                return new ACKcommand(opCode,optional );
            }
        }
        return new ERRcommand(opCode);
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
