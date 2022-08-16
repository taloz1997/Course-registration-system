package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main(String[] args) {
            int portNum;
        if(args.length<1){
            throw new IllegalArgumentException();
        }
        try{
            portNum = Integer.decode(args[0]).intValue();
        }
        catch(NumberFormatException e){
            throw new IllegalArgumentException();
        }
        Database database = Database.getInstance();
        database.initialize("Courses.txt");
        Server tpcServer= Server.threadPerClient(portNum,MessagingProtocolImpl::new, MessageEncoderDecoderImpl::new );
        tpcServer.serve();
    }
}
