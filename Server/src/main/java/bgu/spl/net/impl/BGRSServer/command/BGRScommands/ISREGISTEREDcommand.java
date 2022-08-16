package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class ISREGISTEREDcommand extends Command {
    private short opCode;
    private short courseNum;


    public ISREGISTEREDcommand(short courseNum) {
        opCode = 9;
        this.courseNum = courseNum;
    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        if (protocol.getUser().isLoggedIn()) {
            Object isReg = database.isRegistered(courseNum, protocol.getUser().getUserName());
            if (isReg != null) {
                if (isReg.equals(true))
                    return new ACKcommand(opCode, "1");   //REGISTERED
                else
                    return new ACKcommand(opCode,"0" );  //NOT REGISTERED
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
