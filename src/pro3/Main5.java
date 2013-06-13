package pro3;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;

import pro3.shape.*;
import pro3.target.Target;
import pro3.target.PanelTarget;
import static java.lang.Math.*;

/** 二つのボタンが付いたプログラムのメインクラス */
public class Main5 implements ActionListener{
  /** 図形の描画領域 */
  Target panel;
  /** ボタンの部品1 */
  JButton button1;
  /** ボタンの部品2 */
  JButton button2;
  /** 図形集合の配列 */
  ShapeManager[] sm = new ShapeManager[4];
  /** 図形集合 */
  ShapeManager smempty;
  /** 図形集合 */
  ShapeManager sms;

  public Main5(){
    for(int i=0;i<sm.length;i++){
      sm[i] = new ShapeManager();
      for(int j=0;j<10;j++){
        Attribute attr = new Attribute();
        attr.setColor((i/2)*255, (i%2)*256, ((3-i)/3)*255);
        attr.setFill(true);
        Shape s = new Circle(i*10+j,
                             100+(i*200)%400+(int)(cos((double)j*PI/5)*25),
                             100+(i/2)*200+(int)(sin((double)j*PI/5)*25),5);
        s.setAttribute(attr);
        sm[i].add(s);
      }
      sms = new ShapeManager();
      smempty = new ShapeManager();
    }
  }

  /** ウィンドウ内への部品の配置、アクションリスナの設定を行う */
  public void initWindow(){
    final JFrame frame = new JFrame("DisplayShapes");
    button1 = new JButton("clear");
    button2 = new JButton("set");
    panel = new PanelTarget(400,400);

    button1.addActionListener(this);
    button2.addActionListener(this);
    Container container = frame.getContentPane();
    container.setLayout(new BorderLayout());
    frame.add((PanelTarget)panel, BorderLayout.NORTH);
    frame.add(button1, BorderLayout.CENTER);
    frame.add(button2, BorderLayout.SOUTH);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){   
        frame.setVisible (true);
      }});
  }

  /** ボタンが押された時に呼ばれるメソッド */
  public void actionPerformed(ActionEvent e){
    if(e.getSource() == button1){
      //ボタン1が押された時の処理
      panel.draw(smempty);
      panel.flush(); 
    }else{
      //ボタン1以外のボタンが押された時の処理
      sms.clear();
      for(ShapeManager si:sm){
        sms.merge(si);
      }
      panel.draw(sms);
      panel.flush(); 
    }
  }

  /** メインメソッド */
  public static void main(String[] argv){
    Main5 m = new Main5();
    m.initWindow();
  }
}
