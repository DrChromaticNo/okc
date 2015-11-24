boolean overCircle(int x, int y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if (sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
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
  
  public Question(String text, String[] options)
  {
    this.text = text;
    this.options = options;
    yourChoice = null;
    theirChoice = null;
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
  
  public void drawAt(int x, int y, PFont TEXT_FONT, PFont OPTIONS_FONT)
  { 
    int q_width = MIN_WIDTH;
    int q_height = MIN_HEIGHT;
    
    rect(x, y, q_width, q_height, 7);
    
    textFont(TEXT_FONT);
    fill(0);
    textAlign(CENTER);
    int text_x = (q_width)/2 + x;
    int text_y = y+16;
    text(text,text_x,text_y);
    
    int opt_height = text_y+20;
    
    for(int i = 0; i < options.length; i++)
    {
      String option = options[i];
      
      //Make Button
      int circle_x = x+20;
      int circle_y = opt_height;
      int circle_d = 30;
      
      if(overCircle(circle_x, circle_y, circle_d))
      {
        if (mousePressed && mouseButton == LEFT)
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
      ellipse(circle_x, circle_y, circle_d/2, circle_d/2);
      
      //Write Text
      textFont(OPTIONS_FONT);
      fill(0);
      textAlign(LEFT);
      
      int option_x = x+40;
      int option_y = opt_height+5;
      text(option,option_x,option_y);
      
      //Increment
      opt_height = opt_height + 30;
    }
    
    stroke(0);
    line(x+20, opt_height, x+q_width-20, opt_height);
    
    opt_height = opt_height + 30;
    
    textFont(TEXT_FONT);
    fill(0);
    textAlign(CENTER);
    text_x = (q_width)/2 + x;
    text_y = opt_height;
    text("Answer(s) You Will Accept",text_x,text_y);
    
    opt_height = opt_height + 30;
    
    for(int i = 0; i < options.length; i++)
    {
      String option = options[i];
      
      //Make Button
      int circle_x = x+20;
      int circle_y = opt_height;
      int circle_d = 30;
      
      if(overCircle(circle_x, circle_y, circle_d))
      {
        if (mousePressed && mouseButton == LEFT)
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
      ellipse(circle_x, circle_y, circle_d/2, circle_d/2);
      
      //Write Text
      textFont(OPTIONS_FONT);
      fill(0);
      textAlign(LEFT);
      
      int option_x = x+40;
      int option_y = opt_height+5;
      text(option,option_x,option_y);
      
      //Increment
      opt_height = opt_height + 30;
    }
    
    fill(255);
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
    return options[yourChoice];
  }
  
  public String getTheirChoice()
  {
    return options[theirChoice];
  }
}