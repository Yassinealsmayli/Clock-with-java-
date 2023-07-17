package Clock;

import java.awt.*;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Locale;   
import javax.swing.*;  
public class Clock extends JPanel implements Runnable  
{  
 Thread thread = null;  
 SimpleDateFormat formatter = new SimpleDateFormat("s", Locale.getDefault());  
 Date currentDate;  
 int xcenter = 175, ycenter = 175, lastxs = 0, lastys = 0, lastxm = 0, lastym = 0, lastxh = 0, lastyh = 0;  
 private void drawStructure(Graphics g) {  
  g.setFont(new Font("TimesRoman", Font.BOLD, 20)); 
  //make the clock background
  g.setColor(new Color(117, 194, 246));  
  g.fillOval(xcenter - 150, ycenter - 150, 300, 300);  
  g.setColor(new Color(29, 93, 155));  
  //draw 3,6,9,12 hours indicators
  g.drawString("9", xcenter - 145, ycenter + 0);  
  g.drawString("3", xcenter + 135, ycenter + 0);  
  g.drawString("12", xcenter - 10, ycenter - 130);  
  g.drawString("6", xcenter - 10, ycenter + 145);
  //draw the other hours indicators
  for(int i = 5;i<60;i+=5){
      if(i!=15&&i!=30&&i!=45){
          g.fillOval((int)(Math.cos(i * 3.14f/30 - 3.14f/2) * 140 + xcenter), (int)(Math.sin(i * 3.14f / 30 - 3.14f / 2) * 140 + ycenter), 5, 5);
      }
      
  }
 }  

    /**
     *
     * @param g
     */
    @Override
 public void paint(Graphics g)   
 {  
  int xhour, yhour, xminute, yminute, xsecond, ysecond, second, minute, hour;
  //define Color palette
  Color color1 = new Color(29, 93, 155);
  Color color2 = new Color(244, 209, 96);
  Color color3 = new Color(251, 238, 172);
  
  drawStructure(g); 
  //get sec, min , hour logic
  currentDate = new Date();  
  formatter.applyPattern("s");  
  second = Integer.parseInt(formatter.format(currentDate));  
  formatter.applyPattern("m");  
  minute = Integer.parseInt(formatter.format(currentDate));  
  formatter.applyPattern("h");  
  hour = Integer.parseInt(formatter.format(currentDate)); 
  formatter.applyPattern("EEEE dd/MM/yy");
  String date = formatter.format(currentDate);
  
  xsecond = (int)(Math.cos(second * 3.14f/30 - 3.14f/2) * 120 + xcenter);  
  ysecond = (int)(Math.sin(second * 3.14f / 30 - 3.14f / 2) * 120 + ycenter);  
  xminute = (int)(Math.cos(minute * 3.14f / 30 - 3.14f / 2) * 100 + xcenter);  
  yminute = (int)(Math.sin(minute * 3.14f / 30 - 3.14f / 2) * 100 + ycenter);  
  xhour = (int)(Math.cos((hour * 30 + minute / 2) * 3.14f / 180 - 3.14f / 2) * 80 + xcenter);  
  yhour = (int)(Math.sin((hour * 30 + minute / 2) * 3.14f / 180 - 3.14f / 2) * 80 + ycenter);
  //display date
  g.setColor(color1);
  g.drawString(date, xcenter - 75, ycenter + 75);  
  //display clock analogs
  if (xsecond != lastxs || ysecond != lastys)   
  {  
   g.drawLine(xcenter, ycenter, lastxs, lastys);  
  } 
  //g.setColor(color2);
  if (xminute != lastxm || yminute != lastym) 
  {  
   g.drawLine(xcenter, ycenter - 1, lastxm, lastym);  
   g.drawLine(xcenter - 1, ycenter, lastxm, lastym);  
  } 
  if (xhour != lastxh || yhour != lastyh)   
  {  
   g.drawLine(xcenter, ycenter - 1, lastxh, lastyh);  
   g.drawLine(xcenter - 1, ycenter, lastxh, lastyh);  
  }  
  g.setColor(color1);  
  g.drawLine(xcenter, ycenter, xsecond, ysecond);  
  g.setColor(color2);  
  g.drawLine(xcenter, ycenter - 1, xminute, yminute);  
  g.drawLine(xcenter - 1, ycenter, xminute, yminute);  
  g.setColor(color3);  
  g.drawLine(xcenter, ycenter - 1, xhour, yhour);  
  g.drawLine(xcenter - 1, ycenter, xhour, yhour);  
  //save current analogs location
  lastxs = xsecond;  
  lastys = ysecond;  
  lastxm = xminute;  
  lastym = yminute;  
  lastxh = xhour;  
  lastyh = yhour;  
  
 }  
 public void start()   
 {  
  if (thread == null)   
  {  
   thread = new Thread(this);  
   thread.start();  
  }  
 }  
 public void stop()   
 {  
  thread = null;  
 }  
 @Override
 public void run()   
 {  
  while (thread != null)   
  {  
   try   
   {  
    Thread.sleep(100);  
   }   
   catch (InterruptedException e) {}  
   repaint();  
  }  
  thread = null;  
 }  
 @Override
 public void update(Graphics g)   
 {  
  paint(g);  
 }  
 public static void main(String args[])   
 {  
  JFrame window = new JFrame();  
  window.setBackground(new Color(29, 93, 155));  
  window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
  window.setBounds(0, 0, 400, 400);  
  Clock clock = new Clock();  
  window.getContentPane().add(clock);  
  window.setVisible(true);  
  clock.start();  
 }  
}   
