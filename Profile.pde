class Profile
{
  private String username;
  private String password;
  private Question[] questions;
  private int qIndex;
  private Demographics demo;
  private boolean okToToggle;
  private int currToggle;
  private int toggleInterval = 10;
  private boolean qChange;
  
  public Profile(String username, String password)
  {
    this.username = username;
    this.password = password;
    qIndex = 0;
    qChange = true;
  }
  
  public void setQuestions(Question[] questions)
  {
    this.questions = questions;
  }
  
  public Question[] getQuestions()
  {
    return questions;
  }
  
  public String getUsername()
  {
    return username;
  }
  
  public void setDemo(Demographics demo)
  {
    this.demo = demo;
  }
  
  public Demographics getDemo()
  {
    return demo;
  }
  
  public boolean questionsChanged()
  {
    if(qChange)
    {
      qChange = false;
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public void drawActiveQ(float x, float y)
  {
    Question question = questions[qIndex];
    question.drawAt(x,y);
    
    //Now draw navigation arrows over questions
    float button_w = 30;
    float button_h = 30;
    
    color okcButton = color(50,96,199);
    color okcButton_mouseover = color(69,110,203);
    noStroke();
      
    float rect_x = x;
    float rect_y = y;
    
    if(questions[qIndex].getEditing())
    {
      fill(100);
    }
    else
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
           questions[qIndex].setEditing(false);
           qIndex = qIndex-1;
           if(qIndex < 0)
           {
             qIndex = questions.length-1;
           }
           okToToggle = false;
           currToggle = 0;
        }
      }
      else
      {
          fill(okcButton);
      }
    }
    rect(rect_x,rect_y,button_w,button_h,2);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    String buttonText = "<-";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    
     rect_x = x + 300 - button_w;
     rect_y = y;
    
    if(questions[qIndex].getEditing())
    {
      fill(100);
    }
    else
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
           questions[qIndex].setEditing(false);
           qIndex = qIndex+1;
           if(qIndex > questions.length-1)
           {
             qIndex = 0;
           }
           okToToggle = false;
           currToggle = 0;
        }
      }
      else
      {
          fill(okcButton);
      }
    }
    rect(rect_x,rect_y,button_w,button_h,2);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    buttonText = "->";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    
    if(currToggle < toggleInterval)
    {
      currToggle = currToggle+1;
    }
    else
    {
      okToToggle = true;
    }
  }
  
  public void drawAt(float x, float y)
  {
    int username_x = 300;
    int username_y = 300;
    //First, the username block
    noStroke();
    fill(okcOffWhite);
    rect(x,y,username_x,username_y,7);
    
    textFont(USERNAME_FONT);
    fill(0);
    textAlign(CENTER);
    
    text(username,x+50,y+20,200,100);
    
    float textHeight = wordWrap(username, 200).size() * g.textLeading;
    
    Set<Gender> genders = demo.getGenders();
    int age = demo.getAge();
    
    textFont(USERNAME_SUBTITLE_FONT);
    String subheading = "";
    
    for(Gender g : genders)
    {
      subheading = subheading + " " + genderToString.get(g) + ",";
    }
    
    //Remove final comma
    if(subheading.charAt(subheading.length()-1) == ',')
    {
      subheading = subheading
        .substring(0,subheading.length()-1);
    }
    
    subheading = subheading + " | ";
    
    subheading = subheading + Integer.toString(age);
    
    text(subheading,x+50,y+20+textHeight,200,100);
    
    //Now draw the demographics panel
    
    fill(255);
    
    demo.drawAt(x+username_x+15,y,DEMO_FONT, BUTTON_FONT);
    
    //Now we figure out which question to display, and display it
    
    Question question = questions[qIndex];
    
    if(question.getChanged())
    {
      qChange = true;
    }
    
    float question_x = x+username_x+20+610;
    float question_y = y;
    question.drawAt(question_x,question_y);
    
    //Now draw navigation arrows over questions
    float button_w = 30;
    float button_h = 30;
    
    color okcButton = color(50,96,199);
    color okcButton_mouseover = color(69,110,203);
    noStroke();
      
    float rect_x = question_x;
    float rect_y = question_y;
    
    if(questions[qIndex].getEditing())
    {
      fill(100);
    }
    else
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
           questions[qIndex].setEditing(false);
           qIndex = qIndex-1;
           if(qIndex < 0)
           {
             qIndex = questions.length-1;
           }
           okToToggle = false;
           currToggle = 0;
        }
      }
      else
      {
          fill(okcButton);
      }
    }
    rect(rect_x,rect_y,button_w,button_h,2);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    String buttonText = "<-";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    
     rect_x = question_x + 300 - button_w;
     rect_y = question_y;
    
    if(questions[qIndex].getEditing())
    {
      fill(100);
    }
    else
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
           questions[qIndex].setEditing(false);
           qIndex = qIndex+1;
           if(qIndex > questions.length-1)
           {
             qIndex = 0;
           }
           okToToggle = false;
           currToggle = 0;
        }
      }
      else
      {
          fill(okcButton);
      }
    }
    rect(rect_x,rect_y,button_w,button_h,2);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    buttonText = "->";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    
    if(currToggle < toggleInterval)
    {
      currToggle = currToggle+1;
    }
    else
    {
      okToToggle = true;
    }
    
    fill(255);
  }
}