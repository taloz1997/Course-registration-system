package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.Student;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class COURSEREGcommand extends Command {

    private short opCode;
    private short courseNum;

    public COURSEREGcommand(short courseNum) {
        opCode = 5;
        this.courseNum = courseNum;
    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        if (protocol.getUser().isLoggedIn() && protocol.getUser() instanceof Student) {
            if (database.registerCourse(courseNum, (Student) protocol.getUser())) {  //
                return new ACKcommand(opCode, null);
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
