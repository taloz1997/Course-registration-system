package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;

public class ERRcommand extends Command {

    private short opCode;
    private short errorOpcode;

    public ERRcommand(short errorOpcode){
        this.errorOpcode= errorOpcode;
        opCode= 13;
    }

    @Override
    public short getOpcode() {
        return errorOpcode;
    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        return null;
    }

    @Override
    public String getOptionalByString() {
        return null;
    }
}
