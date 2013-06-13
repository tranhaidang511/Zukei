package pro3.target;
import java.io.*;
import java.net.*;
import java.util.*;
import pro3.shape.ShapeManager;
import pro3.parser.MainParser;

/** 
 * 一つの通信路を受け持つクラス
 */ 
class SocketThread extends Thread{
  final Socket socket;
  final PrintWriter pw;
  final BufferedReader br;
  final ShapeManager sm;
  final MainParser mp;
  final TargetServer targetserver;
  SocketThread(Socket socket, TargetServer ts) throws IOException{
    this.socket = socket;
    pw = new PrintWriter(socket.getOutputStream(),true);
    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    sm = new ShapeManager();
    mp = new MainParser(ts, sm);
    targetserver = ts;
  }
  public void run(){
    try {
      mp.parse(new Scanner(br));
    }catch(NoSuchElementException e){
    }finally{
      targetserver.remove(this);
    } 
  }
}
