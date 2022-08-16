package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;
import bgu.spl.net.impl.BGRSServer.Admin;

public class ADMINREGcommand extends Command {

    private short opCode;
    private String userName;
    private String password;


    public ADMINREGcommand(String userName, String password) {
        opCode = 1;
        this.userName = userName;
        this.password = password;
    }

    public Command process(MessagingProtocolImpl protocol) {
            if(protocol.getUser()==null && database.addUser('a', userName, password))
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
