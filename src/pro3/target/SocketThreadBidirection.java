package pro3.target;

import java.io.*;
import java.net.*;

class SocketThreadBidirection extends SocketThread{
  final PrintStream ps;
  final TextTarget outtarget;
  SocketThreadBidirection(Socket socket, TargetServerBidirection tsb) 
                                                  throws IOException{
    super(socket,tsb);
    ps = new PrintStream(socket.getOutputStream(),true);
    outtarget = new TextTarget(ps);
  }
}
