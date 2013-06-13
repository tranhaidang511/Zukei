package pro3.parser;
import java.util.Scanner;
import java.util.regex.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import org.apache.commons.codec.binary.Base64InputStream;

import pro3.shape.Attribute;
import pro3.shape.Image;

class ImageParser implements ShapeParser{
  private static final Pattern pattern = Pattern.compile("[\\w\\+/=&]+");
  private static final int codeand = "&".hashCode();
  private byte[] bisdata;
  private ByteArrayInputStream bis;
  ImageParser(){
    bisdata = new byte[Image.MAXWIDTH*Image.MAXHEIGHT*4*78*4/(76*3)+128];
    bis = new ByteArrayInputStream(bisdata);
  }
  @Override
  public Image parse(Scanner s, int id){
    int x = s.nextInt();
    int y = s.nextInt();
    BufferedImage img;
    try {
      img = scanImage(s);
    }catch(IOException e){
      img = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
    }
    Image ret = new Image(id,x,y,img);

    if(s.hasNext("Attribute")){
      Attribute attr = AttributeParser.parse(s);
      ret.setAttribute(attr);
    }
    return ret;
  }

  private BufferedImage scanImage(Scanner s) throws IOException{
    String str =s.next(pattern);
    int pos = 0;
    while(!(str.length()==1 && str.hashCode()== codeand)){
      str.getBytes(0,str.length(),bisdata,pos);
      pos += str.length();
      str = s.next(pattern);
    }
    bis.reset();
    return ImageIO.read(new Base64InputStream(bis));
  }
}
