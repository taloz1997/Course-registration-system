package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.Student;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class MYCOURSEScommand extends Command {
    private short opCode;

    public MYCOURSEScommand() {
        opCode = 11;
    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        if (protocol.getUser().isLoggedIn()) {
            String optional = database.getCoursesOfStudentByOrder((Student) protocol.getUser());
            if (optional != null)
                return new ACKcommand(opCode, optional);

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
