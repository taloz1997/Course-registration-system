package bgu.spl.net.impl.BGRSServer.command.BGRScommands;
import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class STUDENTSTATcommand extends Command {
    private short opCode;
    private String userName;


    public STUDENTSTATcommand(String userName) {
        opCode = 8;
        this.userName = userName;
    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        if(protocol.getUser()!= null) {
            if (protocol.getUser().isLoggedIn()) {
                String optional = database.studentStatus(userName);
                if (optional != null)
                    return new ACKcommand(opCode, optional);

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
