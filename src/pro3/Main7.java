package pro3;
import javax.swing.*;
import java.awt.Container;
import javax.swing.event.*;
import java.util.*;
import pro3.shape.*;
import pro3.target.Target;
import pro3.target.PanelTarget;
import pro3.parser.MainParser;

/** n個のスライダのついたプログラムのメインクラス */
public class Main7 implements ChangeListener{
  private int SLIDECOUNT = 3;
  Target panel;
  JSlider[] sliders;
  MainParser parser;
  ShapeManager sm;
  String[] shapes= {
      "shape 0 Circle 10 100 20 Attribute   0   0 255 true\n",
      "shape 1 Circle 20 130 20 Attribute   0 255 255 true\n",
      "shape 2 Circle 30 140 20 Attribute   0 255   0 true\n",
      "shape 3 Circle 40 130 20 Attribute 255 255   0 true\n",
      "shape 4 Circle 50 100 20 Attribute 255 165   0 true\n",
      "shape 5 Circle 60  70 20 Attribute 255   0   0 true\n",
      "shape 6 Circle 70  60 20 Attribute 255 125 200 true\n",
      "shape 7 Circle 80  70 20 Attribute 255   0 255 true\n",

      "shape 8 Circle 90 100 20 Attribute   0   0 255 true\n",
      "shape 9 Circle 100 130 20 Attribute   0 255 255 true\n",
      "shape 10 Circle 110 140 20 Attribute   0 255   0 true\n",
      "shape 11 Circle 120 130 20 Attribute 255 255   0 true\n",
      "shape 12 Circle 130 100 20 Attribute 255 165   0 true\n",
      "shape 13 Circle 140  70 20 Attribute 255   0   0 true\n",
      "shape 14 Circle 150  60 20 Attribute 255 125 200 true\n",
      "shape 15 Circle 160  70 20 Attribute 255   0 255 true\n",

      "shape 16 Circle 170 100 20 Attribute   0   0 255 true\n",
      "shape 17 Circle 180 130 20 Attribute   0 255 255 true\n",
      "shape 18 Circle 190 140 20 Attribute   0 255   0 true\n",
      "shape 19 Circle 200 130 20 Attribute 255 255   0 true\n",
      "shape 20 Circle 210 100 20 Attribute 255 165   0 true\n",
      "shape 21 Circle 220  70 20 Attribute 255   0   0 true\n",
      "shape 22 Circle 230  60 20 Attribute 255 125 200 true\n",
      "shape 23 Circle 240  70 20 Attribute 255   0 255 true\n",

      "shape 24 Circle 250 100 20 Attribute   0   0 255 true\n",
      "shape 25 Circle 260 130 20 Attribute   0 255 255 true\n",
      "shape 26 Circle 270 140 20 Attribute   0 255   0 true\n",
      "shape 27 Circle 280 130 20 Attribute 255 255   0 true\n",
      "shape 28 Circle 290 100 20 Attribute 255 165   0 true\n",
      "shape 29 Circle 300  70 20 Attribute 255   0   0 true\n",
      "shape 30 Circle 310  60 20 Attribute 255 125 200 true\n",
      "shape 31 Circle 320  70 20 Attribute 255   0 255 true\n",

      "shape 32 Circle 330 100 20 Attribute   0   0 255 true\n",
      "shape 33 Circle 340 130 20 Attribute   0 255 255 true\n",
      "shape 34 Circle 350 140 20 Attribute   0 255   0 true\n",
      "shape 35 Circle 360 130 20 Attribute 255 255   0 true\n",
      "shape 36 Circle 370 100 20 Attribute 255 165   0 true\n",
      "shape 37 Circle 380  70 20 Attribute 255   0   0 true\n",
      "shape 38 Circle 390  60 20 Attribute 255 125 200 true\n",
      "shape 39 Circle 400  70 20 Attribute 255   0 255 true\n",

      "shape 40 Circle 10 300 20 Attribute   0   0 255 true\n",
      "shape 41 Circle 20 330 20 Attribute   0 255 255 true\n",
      "shape 42 Circle 30 340 20 Attribute   0 255   0 true\n",
      "shape 43 Circle 40 330 20 Attribute 255 255   0 true\n",
      "shape 44 Circle 50 300 20 Attribute 255 165   0 true\n",
      "shape 45 Circle 60 270 20 Attribute 255   0   0 true\n",
      "shape 46 Circle 70 260 20 Attribute 255 125 200 true\n",
      "shape 47 Circle 80 270 20 Attribute 255   0 255 true\n",

      "shape 48 Circle 90 300 20 Attribute   0   0 255 true\n",
      "shape 49 Circle 100 330 20 Attribute   0 255 255 true\n",
      "shape 50 Circle 110 340 20 Attribute   0 255   0 true\n",
      "shape 51 Circle 120 330 20 Attribute 255 255   0 true\n",
      "shape 52 Circle 130 300 20 Attribute 255 165   0 true\n",
      "shape 53 Circle 140 270 20 Attribute 255   0   0 true\n",
      "shape 54 Circle 150 260 20 Attribute 255 125 200 true\n",
      "shape 55 Circle 160 270 20 Attribute 255   0 255 true\n",

      "shape 56 Circle 170 300 20 Attribute   0   0 255 true\n",
      "shape 57 Circle 180 330 20 Attribute   0 255 255 true\n",
      "shape 58 Circle 190 340 20 Attribute   0 255   0 true\n",
      "shape 59 Circle 200 330 20 Attribute 255 255   0 true\n",
      "shape 60 Circle 210 300 20 Attribute 255 165   0 true\n",
      "shape 61 Circle 220 270 20 Attribute 255   0   0 true\n",
      "shape 62 Circle 230 260 20 Attribute 255 125 200 true\n",
      "shape 63 Circle 240 270 20 Attribute 255   0 255 true\n",

      "shape 64 Circle 250 300 20 Attribute   0   0 255 true\n",
      "shape 65 Circle 260 330 20 Attribute   0 255 255 true\n",
      "shape 66 Circle 270 340 20 Attribute   0 255   0 true\n",
      "shape 67 Circle 280 330 20 Attribute 255 255   0 true\n",
      "shape 68 Circle 290 300 20 Attribute 255 165   0 true\n",
      "shape 69 Circle 300 270 20 Attribute 255   0   0 true\n",
      "shape 70 Circle 310 260 20 Attribute 255 125 200 true\n",
      "shape 71 Circle 320 270 20 Attribute 255   0 255 true\n",

      "shape 72 Circle 330 300 20 Attribute   0   0 255 true\n",
      "shape 73 Circle 340 330 20 Attribute   0 255 255 true\n",
      "shape 74 Circle 350 340 20 Attribute   0 255   0 true\n",
      "shape 75 Circle 360 330 20 Attribute 255 255   0 true\n",
      "shape 76 Circle 370 300 20 Attribute 255 165   0 true\n",
      "shape 77 Circle 380 270 20 Attribute 255   0   0 true\n",
      "shape 78 Circle 390 260 20 Attribute 255 125 200 true\n",
      "shape 79 Circle 400 270 20 Attribute 255   0 255 true\n",
};

  public Main7(){
    panel = new PanelTarget(400,400);
    sm = new ShapeManager();
    parser = new MainParser(panel,sm);
  }
  
  /** ウィンドウ内への部品の配置、チェンジリスナの設定を行う */
  public void initWindow(){
    final JFrame frame = new JFrame("DisplayShapes");
    Container container = frame.getContentPane();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    frame.add((PanelTarget)panel);

    sliders = new JSlider[SLIDECOUNT];
    for(JSlider s: sliders){
      s = new JSlider(0,shapes.length,0);
      s.addChangeListener(this);
      s.setMinorTickSpacing(1);
      s.setPaintTicks(true); 
      frame.add(s);
    }

    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){   
        frame.setVisible (true);
      }});
  }

  /** スライダが動かされたときに呼ばれるメソッド */
  public void stateChanged(ChangeEvent e){
    String data="";
    for(int i=0;i<((JSlider)(e.getSource())).getValue();i++){
      data = data+shapes[i];
    }
    Scanner s = new Scanner(data);
    sm.clear();
    parser.parse(s);
    
    panel.draw(sm);
    panel.flush(); 
  }

  /** メインメソッド */
  public static void main(String[] argv){
    Main7 m = new Main7();
    m.initWindow();
  }
}
