package pro3.target;

import java.io.*;
import java.net.*;
import java.util.*;
import pro3.shape.ShapeManager;
import pro3.shape.OrderedShapeManager;

public class TargetServerBidirection extends TargetServer implements Target{
  private ShapeManager dummysm;

  public TargetServerBidirection(Target target) throws IOException{
    this(target, new OrderedShapeManager());
  }

  public TargetServerBidirection(Target target, ShapeManager shapemanager)
                                                        throws IOException{
    super(target, shapemanager);
    dummysm = new ShapeManager(); 
  }

  public void welcome(){
    while(true){
      Socket s = null;
      try {
        s = ss.accept();
        SocketThreadBidirection st = new SocketThreadBidirection(s, this);
        add(st);
        st.start();
      }catch(IOException e){
        try {
          if(s!=null){
            s.close();
          }
        }catch(IOException e2){
        }
      } 
    }
  }

  @Override
  public synchronized void flush(){
    smimpl.clear();
    smimpl.merge(smlocal);
    Set<ShapeManager>keys = mapsmsm.keySet();
    for(ShapeManager key: keys){
      smimpl.merge(mapsmsm.get(key));
    }
    targetimpl.draw(smimpl);
    for(SocketThread st: setst){
      ((SocketThreadBidirection)st).outtarget.clear(dummysm);
      ((SocketThreadBidirection)st).outtarget.draw(smimpl);
    }
    targetimpl.flush();
    for(SocketThread st: setst){
      ((SocketThreadBidirection)st).outtarget.flush(); 
    }
  }
}
