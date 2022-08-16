package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.srv.Server;

public class ReactorMain {
    public static void main(String[] args) {
        int port;
        int threadNum;
        if(args.length<2){
            throw new IllegalArgumentException();
    }
        try{
            port = Integer.decode(args[0]);
            threadNum = Integer.decode(args[1]);

        }
        catch(NumberFormatException e){
            throw new IllegalArgumentException();
        }
        Database database = Database.getInstance();
        database.initialize("Courses.txt");
        Server.reactor(threadNum, port, MessagingProtocolImpl::new, MessageEncoderDecoderImpl::new).serve();

    }
}
