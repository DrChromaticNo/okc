boolean overCircle(float x, float y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if (sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
    return true;
  } else {
    return false;
  }
}

boolean overRect(float x, float y, float width, float height)  {
  if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
}

class Question
{
  private String text;
  private String[] options;
  private Integer yourChoice;
  private Integer theirChoice;
  private final int MIN_WIDTH = 300;
  private final int MIN_HEIGHT = 300;
  private boolean editable;
  private boolean editing;
  private boolean okToSwitch;
  private int switchInterval = 10;
  private int currSwitch;
  private boolean changed;
  private boolean hidden;
  
  public Question(String text, String[] options, boolean editable, boolean hidden)
  {
    this.text = text;
    this.options = options;
    yourChoice = null;
    theirChoice = null;
    this.editable = editable;
    editing = false;
    currSwitch = 0;
    okToSwitch = true;
    changed = true;
    this.hidden = hidden;
  }
  
  public Question(String text, String[] options, boolean editable)
  {
    this(text, options, editable, false);
  }
  
  public boolean getChanged()
  {
    if(changed)
    {
      changed = false;
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public void selectYourChoice(int choice)
  {
    if (choice < options.length)
    {
      yourChoice = choice;
    }
  }
  
  public void selectTheirChoice(int choice)
  {
    if (choice < options.length)
    {
      theirChoice = choice;
    }
  }
  
  public void drawAt(float x, float y)
  { 
    int q_width = MIN_WIDTH;
    int q_height = MIN_HEIGHT;
    
    noStroke();
    if(hidden)
    {
      fill(okcPrivateQ);
    }
    else
    {
      fill(okcOffWhite);
    }
    rect(x, y, q_width, q_height, 7);
    
    if(editable && !hidden)
    {
      //Make Edit Button
      float button_w = 80;
      float button_h = 25;
      
      color okcButton = color(50,96,199);
      color okcButton_mouseover = color(69,110,203);
      noStroke();
      
      float rect_x = x+q_width-button_w-5;
      float rect_y = y+q_height-button_h-5;
      
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToSwitch)
        {
          editing = !editing;
          okToSwitch = false;
          currSwitch = 0;
          if(!editing)
          {
            changed = true;
          }
        }
      }
      else
      {
        fill(okcButton);
      }
      
      rect(rect_x,rect_y,button_w,button_h);
      
      textFont(OPTIONS_FONT);
      fill(255);
      textAlign(CENTER);
      
      String buttonText;
      
      if(editing)
      {
        buttonText = "Done";
      }
      else
      {
        buttonText = "Edit";
      }
      
      text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
      
    }
    textFont(TEXT_FONT);
    textSize(13);
    fill(0);
    textAlign(CENTER);
    float text_w = 200;
    float text_h = 70;
    text(text,x+50,y+16,text_w,text_h);
    if(hidden)
    {
      textFont(USERNAME_FONT);
      fill(okcPrivateQ2);
      textSize(30);
      textAlign(CENTER);
      
      text("PRIVATE",x+(q_width/2),y+(q_height)/2);
    }
    else
    {
      float textHeight = wordWrap(text, (int)text_w).size() * g.textLeading;
      
      float opt_height = y+16+textHeight+5;
      
      for(int i = 0; i < options.length; i++)
      {
        String option = options[i];
        
        //Make Button
        float circle_x = x+20;
        float circle_y = opt_height;
        int circle_d = 30;
        
        if(overCircle(circle_x, circle_y, circle_d))
        {
          if (mousePressed && mouseButton == LEFT && editing)
          {
            fill(0);
            selectYourChoice(i);
          }
          else
          {
            if(yourChoice != null && yourChoice == i)
            {
              fill(0);
            }  
            else
            {
              fill(204);
            }
          }
        }
        else
        {
          if(yourChoice != null && yourChoice == i)
          {
            fill(0);
          }
          else
          {
            fill(255);
          }
        }
        stroke(0);
        if(!editing && yourChoice != null && yourChoice == i)
        {
          ellipse(circle_x, circle_y, circle_d/2, circle_d/2);
        }
        else if(editing)
        {
          ellipse(circle_x, circle_y, circle_d/2, circle_d/2);
        }
        
        //Write Text
        textFont(OPTIONS_FONT);
        fill(0);
        textAlign(LEFT);
        
        float option_x = x+40;
        float option_y = opt_height+4;
        text(option,option_x,option_y);
        
        //Increment
        opt_height = opt_height + 25;
      }
      
      opt_height = opt_height-4;
      if(editable)
      {
        stroke(0);
        line(x+20, opt_height, x+q_width-20, opt_height);
        
        opt_height = opt_height + 20;
        
        textFont(TEXT_FONT);
        textSize(13);
        fill(0);
        textAlign(CENTER);
        float text_x = (q_width)/2 + x;
        float text_y = opt_height;
        text("Answer You Will Accept",text_x,text_y);
        
        opt_height = opt_height + 10;
        for(int i = 0; i < options.length; i++)
        {
          String option = options[i];
          
          //Make Button
          float circle_x = x+20;
          float circle_y = opt_height;
          int circle_d = 30;
          
          if(overCircle(circle_x, circle_y, circle_d))
          {
            if (mousePressed && mouseButton == LEFT && editing)
            {
              fill(0);
              selectTheirChoice(i);
            }
            else
            {
              if(theirChoice != null && theirChoice == i)
              {
                fill(0);
              }  
              else
              {
                fill(204);
              }
            }
          }
          else
          {
            if(theirChoice != null && theirChoice == i)
            {
              fill(0);
            }
            else
            {
              fill(255);
            }
          }
          stroke(0);
          if(!editing && theirChoice != null && theirChoice == i)
          {
            ellipse(circle_x, circle_y, circle_d/2, circle_d/2);
          }
          else if(editing)
          {
            ellipse(circle_x, circle_y, circle_d/2, circle_d/2);
          }
          
          //Write Text
          textFont(OPTIONS_FONT);
          fill(0);
          textAlign(LEFT);
          
          float option_x = x+40;
          float option_y = opt_height+4;
          text(option,option_x,option_y);
          
          //Increment
          opt_height = opt_height + 25;
        }
      }
      if(currSwitch < switchInterval)
      {
        currSwitch = currSwitch+1;
      }
      else
      {
        okToSwitch = true;
      }
      
      fill(255);
    }
  }
  
  public String getText()
  {
    return text;
  }
  
  public String[] getOptions()
  {
    return options;
  }
  
  public String getYourChoice()
  {
    if(yourChoice == null)
      return null;
    return options[yourChoice];
  }
  
  public String getTheirChoice()
  {
    if(theirChoice == null)
      return null;
    return options[theirChoice];
  }
  
  public void setEditing(boolean edit)
  {
    editing = edit;
  }
  
  public boolean getEditing()
  {
    return editing;
  }
}