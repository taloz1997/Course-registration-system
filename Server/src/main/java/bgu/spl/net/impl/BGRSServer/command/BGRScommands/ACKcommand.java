package bgu.spl.net.impl.BGRSServer.command.BGRScommands;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.command.Command;


public class ACKcommand extends Command {
    private short opCode;
    private short ackOpcode;
    private String optional;

    public ACKcommand(short ackOpcode, String optional) {
        this.ackOpcode = ackOpcode;
        this.optional = optional;
        opCode = 13;
    }


    @Override
    public short getOpcode() {
        return ackOpcode;
    }

    @Override
    public Command process(MessagingProtocolImpl protocol) {
        return null;
    }

    @Override
    public String getOptionalByString() {
        if (optional != null)
            return optional;
        return null;
    }


}
