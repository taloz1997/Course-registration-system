package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;
import bgu.spl.net.impl.BGRSServer.Student;

public class KDAMCHECKcommand extends Command {
    private short opCode;
    private short courseNum;


    public KDAMCHECKcommand(short courseNum) {
        opCode = 6;
        this.courseNum = courseNum;

    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        if (protocol.getUser().isLoggedIn() && protocol.getUser() instanceof Student) {
            return new ACKcommand(opCode,database.kdamCheckList(courseNum) ) ;
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
