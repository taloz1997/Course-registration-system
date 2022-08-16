package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.Student;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class STUDENTREGcommand extends Command {
    private short opCode;
    private String userName;
    private String password;

    public STUDENTREGcommand(String userName, String password) {
        opCode = 2;
        this.userName = userName;
        this.password = password;
    }

    public Command process(MessagingProtocolImpl protocol) {
            if(protocol.getUser()==null &&database.addUser('s', userName, password))
            return new ACKcommand(opCode, null);
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

