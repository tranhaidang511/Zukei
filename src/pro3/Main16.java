package pro3;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.event.*;
import pro3.shape.*;
import pro3.target.PanelTarget;
import pro3.opencl.TargetImageFilter;
import static java.lang.Math.*;

/** スダイダの値を変数に渡して画像処理するメインクラス */
public class Main16 implements ChangeListener{
  TargetImageFilter target;
  ShapeManager sm;
  final int INITVALUE=128;
  int oldvalue=INITVALUE;
  volatile int newvalue=INITVALUE;

  public Main16(){
    sm = new OrderedShapeManager();
    Shape s = new Camera(10,0,0);
    sm.add(s);
    s = new Rectangle(5,0,0,Camera.WIDTH,Camera.HEIGHT);
    Attribute attr = new Attribute();
    attr.setColor(255,255,0); 
    attr.setFill(true);
    s.setAttribute(attr);
    sm.add(s);
  }
  
  /** ウィンドウ内への部品の配置、チェンジリスナの設定を行う*/
  public void initWindow(String filename, String kernelname ){
    final JFrame frame = new JFrame("ImageFilter");
    JSlider slider = new JSlider(0,255,INITVALUE);
    slider.setMinorTickSpacing(10);
    slider.setPaintTicks(true); 
    PanelTarget panel = new PanelTarget(Camera.WIDTH,Camera.HEIGHT);
    target = new TargetImageFilter(panel, this, filename, kernelname);
    target.setParameter(oldvalue);

    slider.addChangeListener(this);
    Container container = frame.getContentPane();
    container.setLayout(new BorderLayout());
    frame.add(panel, BorderLayout.NORTH);
    frame.add(slider, BorderLayout.CENTER);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        frame.setVisible (true);
      }});
  }

  public void loop(){
    while(true){
      if(oldvalue != newvalue){
        target.setParameter(newvalue);
        oldvalue = newvalue; 
      }
      target.draw(sm);
      target.flush();
      try{
	Thread.sleep(100);
      }catch(InterruptedException e){
      }
    }
  }

  /** スライダが動かされた時に呼ばれるメソッド */
  public void stateChanged(ChangeEvent e){
    newvalue = ((JSlider)(e.getSource())).getValue();
  }

  /** メインメソッド */
  public static void main(String[] argv){
    Main16 m = new Main16();
    m.initWindow("imagefilter.cl", "Filter1");
    m.loop();
  }
}
