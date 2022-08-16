package bgu.spl.net.api;

import bgu.spl.net.impl.BGRSServer.command.BGRScommands.ACKcommand;
import bgu.spl.net.impl.BGRSServer.command.BGRScommands.ERRcommand;
import bgu.spl.net.impl.BGRSServer.command.Command;
import bgu.spl.net.impl.BGRSServer.Admin;
import bgu.spl.net.impl.BGRSServer.Student;
import bgu.spl.net.impl.BGRSServer.User;

import java.util.concurrent.atomic.AtomicBoolean;

public class MessagingProtocolImpl implements MessagingProtocol<Command> {

    private final AtomicBoolean shouldTerminate = new AtomicBoolean();
    private User user = null;


    @Override
    public Command process(Command msg) {
        switch (msg.getOpcode()) {
            case 1:
            case 2:
            case 3:
                return msg.process(this);
            case 4:
                Command logoutCommand = msg.process(this);
                if (logoutCommand instanceof ACKcommand)
                    shouldTerminate.set(true);
                return logoutCommand;
            case 5:
            case 6:
            case 9:
            case 10:
            case 11: {
                if (user instanceof Student)
                    return msg.process(this);
                else
                    return new ERRcommand(msg.getOpcode());
            }
            case 7:
            case 8: {
                if (user instanceof Admin)
                    return msg.process(this);
                else
                    return new ERRcommand(msg.getOpcode());
            }
        }
        return null;
    }


    @Override
    public boolean shouldTerminate() {
        return shouldTerminate.get();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
