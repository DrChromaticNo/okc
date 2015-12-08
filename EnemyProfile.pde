class Essay
{
  String question;
  String answer;
  
  public Essay(String q, String a)
  {
    question = q;
    answer = a;
  }
  
  public void drawAt(float x, float y)
  {
    int e_width = 400;
    int e_height = 300;
    fill(okcOffWhite);
    noStroke();
    
    rect(x, y, e_width, e_height,7);
    
    textFont(QUESTION_FONT);
    fill(0);
    text(question, x+50, y+20, 300, 100);
    
    float textHeight = wordWrap(question, (int)400).size() * g.textLeading;
    
    textFont(ANSWER_FONT);
    fill(0);
    text(answer, x+25, y+textHeight+20, 350, e_height-textHeight-20);
  }
}
  
class Preference
{
  private Set<Gender> genders;
  private Set<Orientation> orientations;
  private Set<Ethnicity> ethnicities;
  private Set<Religion> religions;
  private Set<Education> educations;
  private Set<Sign> signs;
  private Set<RelationshipType> rtypes;
  private Set<BodyType> btypes;
  private int minAge;
  private int maxAge;
  private int minHeight;
  private int maxHeight;
  
  public Preference()
  {
    minAge = -1;
    maxAge = -1;
    minHeight = -1;
    maxHeight = -1;
  }
  
  public void setGenders(Set<Gender> g)
  {
    genders = g;
  }
  
  public Set<Gender> getGenders()
  {
    return genders;
  }
  
  public void setOrientations(Set<Orientation> o)
  {
    orientations = o;
  }
  
  public void setEthnicities(Set<Ethnicity> e)
  {
    ethnicities = e;
  }
  
  public void setReligions(Set<Religion> r)
  {
    religions = r;
  }
  
  public void setEducations(Set<Education> e)
  {
    educations = e;
  }
  
  public void setSign(Set<Sign> s)
  {
    signs = s;
  }
  
  public void setRTypes(Set<RelationshipType> r)
  {
    rtypes = r;
  }
  
  public void setBTypes(Set<BodyType> b)
  {
    btypes = b;
  }
  
  public void setAgeRange(int min, int max)
  {
    minAge = min;
    maxAge = max;
  }
  
  public int getAgeMin()
  {
    return minAge;
  }
  
  public int getAgeMax()
  {
    return maxAge;
  }
  
  public void setHeightRange(int min, int max)
  {
    minHeight = min;
    maxHeight = max;
  }
  
  public boolean check(Demographics d)
  {
    boolean exit = true;
    if(genders != null)
    {
      for(Gender g:d.getGenders())
      {
        if(genders.contains(g))
        {
          exit = false;
        }
      }
      if(exit)
      {
        return false;
      }
      exit = true;
    }
    
    if(orientations != null)
    {
      for(Orientation o:d.getOrientations())
      {
        if(orientations.contains(o))
        {
          exit = false;
        }
      }
      if(exit)
      {
        return false;
      }
      exit = true;
    }
    
    if(ethnicities != null)
    {
      for(Ethnicity e:d.getEthnicities())
      {
        if(ethnicities.contains(e))
        {
          exit = false;
        }
      }
      if(exit)
      {
        return false;
      }
      exit = true;
    }
    
    if(religions != null && !religions.contains(d.getReligion()))
    {
      return false;
    }
    
    if(rtypes != null && !rtypes.contains(d.getRelationship()))
    {
      return false;
    }
    
    if(btypes != null && !btypes.contains(d.getBodyType()))
    {
      return false;
    }
    
    if(signs != null && !signs.contains(d.getSign()))
    {
      return false;
    }
    
    if(educations != null && !educations.contains(d.getEducation()))
    {
      return false;
    }
    
    int age = d.getAge();
    
    if(minAge > 0 && age < minAge)
    {
      return false;
    }
    if(maxAge > 0 && age > maxAge)
    {
      return false;
    }
    
    int human_height = d.getHeight();
    if(minHeight > 0 && human_height < minHeight)
    {
      return false;
    }
    if(maxHeight > 0 && human_height > maxHeight)
    {
      return false;
    }
    
    return true;
  }
  
}

class EnemyProfile
{
  private String username;
  private PImage profileImage;
  private Essay[] essays;
  private Demographics enemyDemo;
  private Question[] questions;
  private Preference pref;
  private Profile player;
  private int currMatch;
  private int targetMatch;
  private boolean visible;
  private boolean firstDraw;
  private float pulsePoint;
  private boolean pulseAscend;
  private float pulseAmt = .02;
  private int qIndex;
  private int eIndex;
  private boolean okToToggle;
  private int currToggle;
  private int toggleInterval = 10;
  private int demoPage;
  private int tickFrames = 2;
  private int currTick;
  
  public EnemyProfile(String username, Profile player)
  {
    this.username = username;
    pref = new Preference();
    enemyDemo = new Demographics(username);
    this.player = player;
    currMatch = 0;
    targetMatch = 0;
    firstDraw = true;
    pulsePoint = 0;
    pulseAscend = true;
    eIndex = 0;
    qIndex = 0;
    okToToggle = true;
    currToggle = 0;
    demoPage = 1;
    currTick = 0;
  }
  
  public void setTarget(Profile p)
  {
    player = p;
  }
  
  public int getFinalMatch()
  {
    return targetMatch;
  }
  
  public boolean getVisible()
  {
    return visible;
  }
  
  public void setImage(PImage img)
  {
    profileImage = img;
  }
  
  public void setEssays(Essay[] essays)
  {
    this.essays = essays;
  }
  
  public void setDemo(Demographics d)
  {
    enemyDemo = d;
  }
  
  public Demographics getDemo()
  {
    return enemyDemo;
  }
  
  public void setQuestions(Question[] questions)
  {
    this.questions = questions;
  }
  
  public Preference getPreference()
  {
    return pref;
  }
  
  public void setPreference(Preference p)
  {
    pref = p;
  }
  
  public int getMatch()
  {
    int max = 0; //<>//
    int total = 0;
    Question[] pQuestions = player.getQuestions();
    for(Question q:questions)
    {
      for(Question p:pQuestions)
      {
        if(q.getText().equals(p.getText()))
        {
          max = max + 50;
          
          if(p.getTheirChoice() != null && q.getYourChoice().equals(p.getTheirChoice()))
          {
            total = total + 25;
          }
          if(p.getYourChoice() != null && p.getYourChoice().equals(q.getTheirChoice()))
          {
            total = total + 25;
          }
        }
      }
    }
    
    float result = (float) total/max;
    result = result*100;
    if(result == 100)
    {
      result = 99;
    }
    
    return (int) result;
  }
  
  public void drawAt(float x, float y)
  {
    int prof_w = 300;
    int prof_h = 300;
    
    noStroke();
    fill(okcOffWhite);
    rect(x,y,prof_w,prof_h,7);
    
    float username_x = x+50;
    float username_y = y;
    
    textFont(USERNAME_FONT);
    fill(0);
    textAlign(CENTER);
    
    text(username,username_x,username_y,200,100);
    float textHeight = wordWrap(username, 200).size() * g.textLeading;
    
    float match_x = x+(prof_w/2);
    float match_y = username_y + textHeight + 50;
    
    if(profileImage != null)
    {
      image(profileImage,x+50,y+textHeight+10);
      
      match_y = y + textHeight + profileImage.height + 60;
    }
    
    //Match %
    
    Demographics playerDemo = player.getDemo();
    if(firstDraw || playerDemo.getChanged())
    {
      visible = pref.check(playerDemo);
    }
    
    textFont(MATCH_FONT);
    
    if(visible)
    {
      color pulseColor = lerpColor(okcPink2, okcPink1, pulsePoint);
      fill(pulseColor);
      if(pulseAscend)
      {
        pulsePoint = pulsePoint + pulseAmt;
        if(pulsePoint >= 1)
        {
          pulseAscend = false;
        }
      }
      else
      {
        pulsePoint = pulsePoint - pulseAmt;
        if(pulsePoint <= 0)
        {
          pulseAscend = true;
        }
      }
    }
    else
    {
      fill(okcPrivateQ2);
    }
    
    if(player.questionsChanged() || firstDraw)
    {
      //This will be more complicated ideally
      targetMatch = getMatch();
    }
    
    if(currMatch != targetMatch)
    {
      if(tickFrames == currTick)
      {
        if(currMatch > targetMatch)
        {
          currMatch-=1;
          currTick = 0;
        }
        else
        {
          currMatch+=1;
          currTick=0;
        }
      }
      else
      {
        currTick+=1;
      }
    }
    else
    {
      currTick = 0;
    }
    
    String matchText = "--% Match";
    if(visible)
    {
      matchText = currMatch + "% Match";
    }
    
    text(matchText, match_x, match_y);
    textAlign(CENTER);
    
    textFont(USERNAME_SUBTITLE_FONT);
    textSize(16);
    if(visible)
    {
      text("Visible",match_x,match_y+15);
    }
    else
    {
      text("Invisible",match_x,match_y+15);
    }
    
    
    String lookingForDescription = "Looking for:";
    
    if(pref.getGenders() == null)
    {
      lookingForDescription = lookingForDescription + " All";
    }
    else
    {
      for(Gender g:pref.getGenders())
      {
        lookingForDescription = lookingForDescription + " " + genderToString.get(g) + ",";
      }
      //Remove final comma
      if(lookingForDescription.charAt(lookingForDescription.length()-1) == ',')
      {
        lookingForDescription = lookingForDescription
          .substring(0,lookingForDescription.length()-1);
      }
    }
    
    int min = pref.getAgeMin();
    int max = pref.getAgeMax();
    
    if(min != -1 && max != -1)
    {
      lookingForDescription = lookingForDescription + " | ";
      lookingForDescription = lookingForDescription + min + "-" + max;
    }
    
    textFont(USERNAME_SUBTITLE_FONT);
    fill(0);
    textAlign(CENTER);
    
    float looking_x = x+50;
    float looking_y = match_y + 40;
    
    text(lookingForDescription,looking_x,looking_y,200,200);
    
    //Now, essays
    float essay_x = x+310;
    float essay_y = y;
    
    Essay essay = essays[eIndex];
    
    essay.drawAt(essay_x,essay_y);
    
    //Now draw navigation arrows over essays
    float button_w = 30;
    float button_h = 30;
    
    noStroke();
      
    float rect_x = essay_x;
    float rect_y = essay_y;
    
    if(overRect(rect_x,rect_y,button_w,button_h))
    {
      fill(okcButton_mouseover);
      if(mousePressed && mouseButton == LEFT && okToToggle)
      {
         eIndex = eIndex-1;
         if(eIndex < 0)
         {
           eIndex = essays.length-1;
         }
         okToToggle = false;
         currToggle = 0;
      }
    }
    else
    {
      fill(okcButton);
    }
    
    rect(rect_x,rect_y,button_w,button_h,2);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    String buttonText = "<-";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    
    textFont(TEXT_FONT);
    fill(0);
    textAlign(CENTER);
    textSize(10);
    
    text((eIndex+1) + "/" + (essays.length),essay_x+200,essay_y+10);
    
    rect_x = essay_x + 400 - button_w;
    rect_y = essay_y;
    
    if(overRect(rect_x,rect_y,button_w,button_h))
    {
      fill(okcButton_mouseover);
      if(mousePressed && mouseButton == LEFT && okToToggle)
      {
         eIndex = eIndex+1;
         if(eIndex > essays.length-1)
         {
           eIndex = 0;
         }
         okToToggle = false;
         currToggle = 0;
      }
    }
    else
    {
      fill(okcButton);
    }
    
    rect(rect_x,rect_y,button_w,button_h,2);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    buttonText = "->";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    
    //Now, enemy demographics
    
    float demo_x = essay_x + 400+10;
    float demo_y = y;
    
    drawDemo(demo_x,demo_y,enemyDemo);
    
    //Finally, the questions
    
    Question question = questions[qIndex];
    
    float question_x = x+demo_x+200;
    float question_y = y;
    question.drawAt(question_x,question_y);
    
    //Now draw navigation arrows over questions
    button_w = 30;
    button_h = 30;

    noStroke();
      
    rect_x = question_x;
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
    
    buttonText = "<-";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    
    textFont(TEXT_FONT);
    fill(0);
    textAlign(CENTER);
    textSize(10);
    
    text((qIndex+1) + "/" + (questions.length),question_x+150,question_y+10);
    
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
    firstDraw = false;
  }
  
  private void drawDemo(float x, float y, Demographics demo)
  {
    fill(okcOffWhite);
    noStroke();
    float demo_w = 200;
    float demo_h = 300;
    rect(x,y,demo_w,demo_h,7);
    
    float text_x = x+(demo_w/2);
    float text_y = y+13;
    
    textFont(DEMO_FONT);
    fill(0);
    textAlign(CENTER);
    stroke(0);
    if(demoPage == 1)
    {
      text("Gender",text_x,text_y);
      
      String genderDescription = "";
      for(Gender g:demo.getGenders())
      {
        genderDescription = genderDescription + " " + genderToString.get(g) + ",";
      }
      if(genderDescription != "" && genderDescription.charAt(genderDescription.length()-1) == ',')
      {
        genderDescription = genderDescription
          .substring(0,genderDescription.length()-1);
      }
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(genderDescription,text_x,text_y,demo_w,50);
      
      float textHeight = wordWrap(genderDescription, (int)demo_w).size() * g.textLeading;
      
      float line_x = x+5;
      float line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
      
      text_x = x+(demo_w/2);
      text_y = line_y+13;
      
      textAlign(CENTER);
      text("Age",text_x,text_y);
      
      String ageDesc = " " + Integer.toString(demo.getAge());
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(ageDesc,text_x,text_y,demo_w,50);
      
      textHeight = wordWrap(ageDesc, (int)demo_w).size() * g.textLeading;
      
      line_x = x+5;
      line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
      
      text_x = x+(demo_w/2);
      text_y = line_y+13;
      
      textAlign(CENTER);
      text("Orientation",text_x,text_y);
      
      String orientationDescription = "";
      
      for(Orientation o:demo.getOrientations())
      {
        orientationDescription = orientationDescription + " " + orientationToString.get(o) + ",";
      }
      
      //Remove final comma
      if(orientationDescription != "" && orientationDescription.charAt(orientationDescription.length()-1) == ',')
      {
        orientationDescription = orientationDescription
          .substring(0,orientationDescription.length()-1);
      }
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(orientationDescription,text_x,text_y,demo_w,50);
      
      textHeight = wordWrap(orientationDescription, (int)demo_w).size() * g.textLeading;
      
      line_x = x+5;
      line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
      
      text_x = x+(demo_w/2);
      text_y = line_y+13;
      
      textAlign(CENTER);
      text("Ethnicity",text_x,text_y);
      
      String ethnicityDescription = "";
      
      for(Ethnicity e:demo.getEthnicities())
      {
        ethnicityDescription = ethnicityDescription + " " + ethnicityToString.get(e) + ",";
      }
      
      //Remove final comma
      if(ethnicityDescription != "" && ethnicityDescription.charAt(ethnicityDescription.length()-1) == ',')
      {
        ethnicityDescription = ethnicityDescription
          .substring(0,ethnicityDescription.length()-1);
      }
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(ethnicityDescription,text_x,text_y,demo_w,50);
      
      textHeight = wordWrap(ethnicityDescription, (int)demo_w).size() * g.textLeading;
      
      line_x = x+5;
      line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
      
      text_x = x+(demo_w/2);
      text_y = line_y+13;
      
      textAlign(CENTER);
      text("Height",text_x,text_y);
      
      String heightDescription = " ";
      
      int feet = demo.getHeight()/12;
      int inches = demo.getHeight()%12;
      
      if(feet > 0)
      {
        heightDescription = heightDescription + feet + " feet";
        if(inches > 0)
        {
          heightDescription = heightDescription + ", ";
        }
      }
      
      if(inches > 0)
      {
        heightDescription = heightDescription + inches + " inches";
      }
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(heightDescription,text_x,text_y,demo_w,50);
      
      textHeight = wordWrap(heightDescription, (int)demo_w).size() * g.textLeading;
      
      line_x = x+5;
      line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
    }
    else
    {
      textAlign(CENTER);
      text("Relationship Type",text_x,text_y);
      
      String rType = " " + rtToString.get(demo.getRelationship());
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(rType,text_x,text_y,demo_w,50);
      
      float textHeight = wordWrap(rType, (int)demo_w).size() * g.textLeading;
      
      float line_x = x+5;
      float line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
      
      text_x = x+(demo_w/2);
      text_y = line_y+13;
      
      textAlign(CENTER);
      text("Body Type",text_x,text_y);
      
      String bType = " " + btToString.get(demo.getBodyType());
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(bType,text_x,text_y,demo_w,50);
      
      textHeight = wordWrap(bType, (int)demo_w).size() * g.textLeading;
      
      line_x = x+5;
      line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
      
      text_x = x+(demo_w/2);
      text_y = line_y+13;
      
      textAlign(CENTER);
      text("Religion",text_x,text_y);
      
      String religionDesc = " " + religionToString.get(demo.getReligion());
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(religionDesc,text_x,text_y,demo_w,50);
      
      textHeight = wordWrap(religionDesc, (int)demo_w).size() * g.textLeading;
      
      line_x = x+5;
      line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
      
      text_x = x+(demo_w/2);
      text_y = line_y+13;
      
      textAlign(CENTER);
      text("Sign",text_x,text_y);
      
      String signDesc = " " + signToString.get(demo.getSign());
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(signDesc,text_x,text_y,demo_w,50);
      
      textHeight = wordWrap(signDesc, (int)demo_w).size() * g.textLeading;
      
      line_x = x+5;
      line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
      
      text_x = x+(demo_w/2);
      text_y = line_y+13;
      
      textAlign(CENTER);
      text("Education",text_x,text_y);
      
      String educationDesc = " " + educationToString.get(demo.getEducation());
      
      text_x = x;
      text_y = text_y+5;
      textAlign(LEFT);
      text(educationDesc,text_x,text_y,demo_w,50);
      
      textHeight = wordWrap(educationDesc, (int)demo_w).size() * g.textLeading;
      
      line_x = x+5;
      line_y = text_y+textHeight;
      
      line(line_x,line_y,x+demo_w-5,line_y);
    }
    //Now draw navigation tabs over bottom of demos
    float button_w = 50;
    float button_h = 30;
    
    noStroke();
      
    float rect_x = x;
    float rect_y = y+demo_h-button_h;
    
    if(demoPage != 1)
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
           demoPage = 1;
           okToToggle = false;
           currToggle = 0;
        }
      }
      else
      {
        fill(okcButton);
      }
    }
    else
    {
      fill(100);
    }
    
    rect(rect_x,rect_y,button_w,button_h);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    String buttonText = "Page 1";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
    
    rect_x = x + button_w + 5;
    rect_y = y+demo_h-button_h;
    
    if(demoPage != 2)
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
           demoPage = 2;
           okToToggle = false;
           currToggle = 0;
        }
      }
      else
      {
        fill(okcButton);
      }
    }
    else
    {
      fill(100);
    }
    
    rect(rect_x,rect_y,button_w,button_h);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    buttonText = "Page 2";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
  }
}