public class PopupMessage
{
  private String to;
  private String from;
  private String message;
  private boolean visible;
  private final int box_width = 500;
  private boolean okToToggle;
  
  public PopupMessage(String to, String from, String message)
  {
    this.to = to;
    this.from = from;
    this.message = message;
    visible = true;
    okToToggle = true;
  }
  
  public void drawAt(float x, float y)
  {
    if(visible)
    {
      
      String full = "To: " + to + "\nFrom: " + from + "\n" + message; //<>//
      textFont(TEXT_FONT);
      
      float textHeight = wordWrap(full, box_width-50).size() * g.textLeading;
      stroke(0);
      fill(okcOffWhite);
      
      rect(x,y,box_width,textHeight+100,7);
      
      fill(0);
      textAlign(LEFT);
      text(full,x+5,y+5,box_width-50,textHeight+50);
      
      
      float button_w = 30;
      float button_h = 30;
      
      float rect_x = x+box_width-button_w;
      float rect_y = y;

      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(red_mouseover);
        if(mousePressed && mouseButton == LEFT & okToToggle)
        {
          okToToggle = false; //<>//
          setVisible(false);
        }
      }
      else
      {
          fill(red);
      }
      rect(rect_x,rect_y,button_w,button_h,2);
      
      textFont(BUTTON_FONT);
      fill(255);
      textAlign(CENTER);
      
      String buttonText = "X";
      text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    }
  }
  
  public boolean getVisible()
  {
    return visible;
  }
  
  public void setVisible(boolean visible)
  {
    this.visible = visible;
  }
}