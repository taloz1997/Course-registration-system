package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class LOGINcommand extends Command {
    private short opCode;
    private String userName;
    private String password;

    public LOGINcommand(String userName, String password) {
        this.userName = userName;
        this.password = password;
        opCode = 3;
    }

    public Command process(MessagingProtocolImpl protocol) {
        User user = database.getUsersMap().get(userName);
        if (protocol.getUser() == null) {
            protocol.setUser(user);
                if (database.loginUser(userName, password)) {
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
