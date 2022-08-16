package bgu.spl.net.impl.BGRSServer.command.BGRScommands;
import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;

public class UNREGISTERcommand extends Command {
    private short opCode;
    private short courseNum;


    public UNREGISTERcommand(short courseNum) {
        opCode = 10;
        this.courseNum = courseNum;

    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        if (protocol.getUser().isLoggedIn()) {
            if (database.unRegistered(courseNum, protocol.getUser().getUserName()))
                return new ACKcommand(opCode, null);
        }
        return new ERRcommand(opCode);  //error
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
