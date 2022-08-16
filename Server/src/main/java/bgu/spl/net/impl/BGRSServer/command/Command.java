package bgu.spl.net.impl.BGRSServer.command;

import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.impl.BGRSServer.Database;

public abstract class Command {
    protected Database database= Database.getInstance();
    public abstract short getOpcode();
    public abstract Command process(MessagingProtocolImpl protocol);
    public abstract String getOptionalByString();
}
