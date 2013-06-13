package pro3;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.event.*;
import pro3.shape.*;
import pro3.target.PanelTarget;
import static java.lang.Math.*;

/** 複数のShapeManagerを使い分けるプログラムのメインクラス */
public class Main6 implements ChangeListener{
  PanelTarget panel;
  JSlider slider;
  ShapeManager[] sm = new ShapeManager[5];

  public Main6(){
    sm[0] = new ShapeManager();
    for(int i=1;i<sm.length;i++){
      sm[i] = new ShapeManager();
      for(int j=0;j<10;j++){
        Attribute attr = new Attribute();
        attr.setColor((i/2)*255, (i%2)*256, ((3-i)/3)*255);
        attr.setFill(true);
        Shape s = new Circle(j,
                             100+((i-1)*200)%400+(int)(cos((double)j*PI/5)*25),
                             100+((i-1)/2)*200+(int)(sin((double)j*PI/5)*25),5);
        s.setAttribute(attr);
        sm[i].add(s);
      }
    }
  }
  
  /** ウィンドウ内への部品の配置、チェンジリスナの設定を行う */
  public void initWindow(){
    final JFrame frame = new JFrame("DisplayShapes");
    slider = new JSlider(0,4,0);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true); 
    panel = new PanelTarget(400,400);
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

  /** スライダが動かされた時に呼ばれるメソッド */
  public void stateChanged(ChangeEvent e){
    panel.draw(sm[slider.getValue()]);
    panel.flush(); 
  }

  /** メインメソッド */
  public static void main(String[] argv){
    Main6 m = new Main6();
    m.initWindow();
  }
}
