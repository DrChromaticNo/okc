Question Q1 = new Question("Have you ever been cruel to another person?", new String[]{"Yes","No"},true); //<>// //<>//
Question Q2 = new Question("Could you date someone who is androgynous?", new String[]{"Yes","No"}, true);
Question Q3 = new Question("Is supporting \"the troops\" the same thing as supporting a war?", new String[]{"Yes","No","Not Sure"}, true);
Question Q4 = new Question("Do you practice or believe in real magick, not to be confused with stage magic and parlor tricks?", new String[]{"Yes","No"}, true); //<>//
Question Q5 = new Question("Do you think the public should have access to any literature, regardless of its content?", new String[]{"Yes","No, some things should be censored"}, true);
Question Q6 = new Question("Which of the following do you find most liberating?", new String[]{"Travel","Financial Independence","Art","Sexuality"}, true);
Question Q7 = new Question("Would you launch nuclear weapons under any circumstances?", new String[]{"Yes","No"}, true);
Question Q8 = new Question("Would you strongly prefer to go out with someone of your own skin color/racial background?", new String[]{"Yes","No"}, true);
Question Q9 = new Question("How important to you is a potential match's sense of humor?", new String[]{"Very important","Somewhat important","Not important"}, true);
Question Q10 = new Question("Do you think homosexuality is a sin?", new String[]{"Yes","No"}, true);
 //<>//
Demographics demo = new Demographics("demo");
Demographics initial;
Demographics[] phases = new Demographics[3];
EnemyProfile[] enemies = new EnemyProfile[3];
int demoIndex = 0;

Profile player;
EnemyProfile enemy;
PFont USERNAME_FONT;
PFont USERNAME_SUBTITLE_FONT;
PFont TEXT_FONT;
PFont OPTIONS_FONT;
PFont DEMO_FONT;
PFont MATCH_FONT;
PFont BUTTON_FONT;
PFont QUESTION_FONT;
PFont ANSWER_FONT;
PFont TIMER_FONT;
PFont SCORE_FONT;
PFont TITLE_FONT;
ControlP5 cp5;
final color okcPink1 = color(249,59,102);
final color okcPink2 = color(252,236,242);
final color okcButton = color(50,96,199);
final color okcButton_mouseover = color(69,110,203);
final color okcBackground = color(16,77,161);
final color okcOffWhite = color(243,245,249);
final color okcPrivateQ = color(233,234,238);
final color okcPrivateQ2 = color(148,154,167);
final color green = color(0,192,0);
final color red = color(248,55,18);
final int TIMER_LENGTH = 2 * 60 * 1000;
final String title = "OKCUPID: THE VIDEOGAME";
final int screen_w = 1250;
final int screen_h = 700;
int timerStart;
int score;

int stage;
boolean newStage;
int tween = 0;
boolean unansweredQ = true;

String username;
String pass = "";
String pass_confirm = "";

CColor selected;
CColor oldColor;

boolean wasVisible;
int oldMatch;

int scoreFrames = 5;
int scoreTick = 0;

void setup()
{
  setupMaps();
  
  size(1250,700);
  MATCH_FONT = createFont("OpenSans.ttf",50,true);
  TIMER_FONT = createFont("OpenSans.ttf",50,true);
  SCORE_FONT = createFont("OpenSans.ttf",50,true);
  USERNAME_FONT = createFont("OpenSans.ttf",16,true);
  QUESTION_FONT = createFont("OpenSans.ttf",16,true);
  ANSWER_FONT = createFont("OpenSans.ttf",12,true);
  USERNAME_SUBTITLE_FONT = createFont("OpenSans.ttf",14,true);
  TEXT_FONT = createFont("OpenSans.ttf",14,true);
  BUTTON_FONT = createFont("OpenSans.ttf",14,true);
  OPTIONS_FONT = createFont("OpenSans.ttf",12,true);
  DEMO_FONT = createFont("OpenSans.ttf",11,true);
  TITLE_FONT = createFont("OpenSans.ttf",70,true);
  cp5 = new ControlP5(this);
  
  EnemyProfile jmc = make_jughead_muscle_carl(player);
  EnemyProfile BR = make_Book_Reader(player);
  
  enemies[0] = BR;
  enemies[1] = jmc;
  
  stage = 0;
  newStage = true;
  
  selected = new CColor();
  selected.setBackground(color(255,0,0));
  
  oldColor = new CColor();
  oldColor.setBackground(color(0,45,90));
}

void draw()
{
  background(okcBackground);
  
  if(stage == 0)
  {
    drawScreen1();
    if(newStage && stage == 0)
    {
      newStage = false;
    }
  }
  else if(stage == 1)
  {
    if(newStage)
    {
      tween = 0;
    }
    drawScreen1();
    fill(okcBackground);
    stroke(okcPink1);
    rect(0,-1,tween,screen_h+2);
    drawScreen2(tween-screen_w,0);
    tween = tween+10;
    
    if(tween >= screen_w)
    {
      stage = 2;
      newStage = true;
    }
    if(newStage && stage == 1)
    {
      newStage = false;
    }
  }
  else if(stage == 2)
  {
    drawScreen2(0,0);
    if(newStage && stage == 2)
    {
      newStage = false;
    }
  }
  else if(stage == 3)
  {
    if(newStage)
    {
      tween = 0;
    }
    drawScreen2(0,0);
    fill(okcBackground);
    stroke(okcPink1);
    rect(0,-1,tween,screen_h+2);
    
    drawScreen3(tween-screen_w,0);
    tween = tween+10;
    
    if(tween >= screen_w)
    {
      stage = 4;
      newStage = true;
    }
    
    if(newStage && stage == 3)
    {
      newStage = false;
    }
  }
  else if(stage == 4)
  {
    drawScreen3(0,0);
    if(newStage && stage == 4)
    {
      newStage = false;
    }
  }
  else if(stage == 5)
  {
    if(newStage)
    {
      tween = 0;
      enemy = enemies[demoIndex];
      enemy.setTarget(player);
    }
    drawScreen3(0,0);
    fill(okcBackground);
    stroke(okcPink1);
    rect(0,-1,tween,screen_h+2);
    player.drawAt(tween-screen_w-10,10);
    
    textFont(TEXT_FONT);
    textSize(20);
    textAlign(CENTER);
    fill(okcPink1);
    text("^YOU^",(tween-screen_w/2),330);
    
    stroke(okcPink1);
    line(0,345,tween,345);
    
    text("vTHEMv",tween-screen_w/2,370);
    
    enemy.drawAt(tween-screen_w-10,380);
    
    tween = tween+10;
    
    if(tween >= screen_w)
    {
      stage = 6;
      newStage = true;
    }
    
    if(newStage && stage == 5)
    {
      newStage = false;
    }
  }
  else if(stage == 6)
  {
    drawScreen6();
    
    if(newStage && stage == 6)
    {
      newStage = false;
    }
  }
  else if(stage == 7)
  {
    if(newStage)
    {
      tween = 0;
    }
    drawScreen6();
    fill(okcBackground);
    stroke(okcPink1);
    rect(0,-1,tween,screen_h+2);
    drawScreen7(tween-screen_w,0);
    
    tween = tween+10;
    
    if(tween >= screen_w)
    {
      stage = 8;
      newStage = true;
    }
    
    if(newStage && stage == 7)
    {
      newStage = false;
    }
  }
  else if(stage == 8)
  {
    if(!wasVisible || oldMatch == 0)
    {
      //Popup Messages here probably
      
      //Check to see if the game is over or if there are more enemies
      if(true)
      {
        stage = 9;
      }
      else
      {
        stage = 10;
      }
      newStage = true;
    }
    else if(scoreTick == scoreFrames)
    {
      oldMatch-=1;
      score+=1;
      scoreTick = 0;
    }
    else
    {
      scoreTick+=1;
    }
    
    drawScreen7(0,0);
    
    if(newStage && stage == 8)
    {
      newStage = false;
    }
  }
  else if(stage == 9)
  {
    if(newStage)
    {
      tween = screen_w+1;
      enemy = enemies[demoIndex];
      enemy.setTarget(player);
    }
    drawScreen6();
    fill(okcBackground);
    stroke(okcPink1);
    rect(0,-1,tween,screen_h+2);
    drawScreen7(tween-screen_w,0);
    
    tween = tween-10;
    
    if(tween <= 0)
    {
      stage = 6;
      newStage = true;
    }
    if(newStage && stage == 9)
    {
      newStage = false;
    }
  }
}

void drawScreen7(float x, float y)
{
  fill(okcOffWhite);
  noStroke();
  rect(x+300,y+150,screen_w-600,400,7);
  
  if(wasVisible)
  {
    fill(okcPink1);
  }
  else
  {
    fill(okcPrivateQ2);
  }
  float text_x = x+(screen_w/2);
  float text_y = y+200;
  textAlign(CENTER);
  textFont(MATCH_FONT);
  text(oldMatch + "% Match",text_x,text_y);
  
  text_y = text_y+100;
  fill(okcPink1);
  text("Score: " + score, text_x,text_y);
}

void drawScreen6()
{
     if(newStage)
    {
      timerStart = millis();
    }
    player.drawAt(10,10);
    
    textFont(TEXT_FONT);
    textSize(20);
    textAlign(CENTER);
    fill(okcPink1);
    text("^YOU^",screen_w/2,330);
    
    stroke(okcPink1);
    line(0,345,screen_w,345);
    
    text("vTHEMv",screen_w/2,370);
    
    enemy.drawAt(10,380);
    
    textFont(TIMER_FONT);
    textAlign(LEFT);
    fill(0);
    int currTime = millis();
    int timeLeft = (timerStart+TIMER_LENGTH)-currTime;
    int mins = timeLeft/60000;
    String minsDisp = Integer.toString(mins);
    if(mins < 10)
    {
      minsDisp = "0" + minsDisp;
    }
    int secs = (timeLeft/1000)%60;
    String secDisp = Integer.toString(secs);
    if(secs < 10)
    {
      secDisp = "0" + secDisp;
    }
    text(minsDisp + ":" + secDisp,70,200);
    
    textFont(SCORE_FONT);
    if(mins == 0 && secs == 0)
    {
      timerStart = millis();
      wasVisible = enemy.getVisible();
      oldMatch = enemy.getFinalMatch();
      player.getDemo().setEditing(false);
      phases[demoIndex] = player.getDemo().copy();
      demoIndex++;
      stage = 7;
      newStage = true;
    }
    text("Score: " + score,70,250);
}

void drawScreen1()
{
  fill(255);
  textAlign(CENTER);
  textFont(TITLE_FONT);
    float title_x = screen_w/2;
    float title_y = 100;
    text(title,title_x,title_y);
    
    String helpText = "Please enter a username and password to sign up.\nWarning!  These cannot be changed!";
    textFont(TEXT_FONT);
    textSize(20);
    float text_x = title_x;
    float text_y = title_y + 80;
    text(helpText, text_x, text_y);
    
    textSize(15);
    textAlign(LEFT);
    text_x = screen_w/4;
    text_y = text_y + 120;
    text("Username: ",text_x, text_y);
    
    float disp_x = text_x + textWidth("Confirm Password:") + 5;
    float disp_y = text_y-14;
    if(newStage && stage == 0)
    {
      cp5.addTextfield("Username")
           .setPosition(disp_x,disp_y)
           .setSize(500,20)
           .setFont(TEXT_FONT)
           .setLabel("")
           .setAutoClear(false);
    }
    else if(stage == 0)
    {
      cp5.get(Textfield.class, "Username").setPosition(disp_x, disp_y);
    }
    else
    {
      cp5.get(Textfield.class, "Username").setVisible(false);
    }
    
    username = cp5.get(Textfield.class, "Username").getText();
    
    text_x = text_x;
    text_y = text_y + 25;
    
    textSize(15);
    textAlign(LEFT);
    text_x = screen_w/4;
    text_y = text_y + 20;
    text("Password: ",text_x, text_y);
    
    disp_x = text_x + textWidth("Confirm Password:") + 5;
    disp_y = text_y-14;
    if(newStage && stage == 0)
    {
      cp5.addTextfield("Password")
           .setPosition(disp_x,disp_y)
           .setSize(500,20)
           .setFont(TEXT_FONT)
           .setLabel("")
           .setPasswordMode(true)
           .setAutoClear(false);
    }
    else if(stage == 0)
    {
      cp5.get(Textfield.class, "Password").setPosition(disp_x, disp_y);
    }
    else
    {
      cp5.get(Textfield.class, "Password").setVisible(false);
    }
    
    pass = cp5.get(Textfield.class, "Password").getText();
    
    text_x = text_x;
    text_y = text_y + 25;
    
    textSize(15);
    textAlign(LEFT);
    fill(255);
    text_x = screen_w/4;
    text_y = text_y + 20;
    text("Confirm Password: ",text_x, text_y);
    
    disp_x = text_x + textWidth("Confirm Password:") + 5;
    disp_y = text_y-14;
    if(newStage && stage == 0)
    {
      cp5.addTextfield("ConfirmPassword")
           .setPosition(disp_x,disp_y)
           .setSize(500,20)
           .setFont(TEXT_FONT)
           .setLabel("")
           .setPasswordMode(true)
           .setAutoClear(false);
    }
    else if(stage == 0)
    {
      cp5.get(Textfield.class, "ConfirmPassword").setPosition(disp_x, disp_y);
    }
    else
    {
      cp5.get(Textfield.class, "ConfirmPassword").setVisible(false);
    }
    
    pass_confirm = cp5.get(Textfield.class, "ConfirmPassword").getText();
    
    text_x = text_x;
    text_y = text_y + 25;
    
    float button_w = 100;
    float button_h = 50;
    
    color okcButton = color(50,96,199);
    color okcButton_mouseover = color(69,110,203);
    noStroke();
      
    float rect_x = (screen_w/2)-(button_w/2);
    float rect_y = screen_h-button_h-30;
    
    if(username == "" || pass == "" || pass_confirm == "" 
    || username.length() < 1 || pass.length() < 1 || pass_confirm.length() < 1 
    || !pass.equals(pass_confirm) || stage != 0)
    {
      fill(100);
    }
    else
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT)
        {
           if(stage == 0)
           {
             stage = 1;
             player = new Profile(username,pass);
             Question[] questions = new Question[]{Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10};
             player.setQuestions(questions);
             player.setDemo(demo);
             newStage = true;
           }
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
    
    String buttonText = "Sign Up";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
}

void drawScreen2(float x, float y)
{
  fill(255);
  textFont(TEXT_FONT);
  textSize(20);
  textAlign(CENTER);
  String helpText = player.getUsername() + ", please fill out demographic information to continue.\nOnly Gender and Age are required, the rest are optional.\nDon't worry, they can all be changed later.";
  text(helpText,x+(screen_w/2),y+50);
  
  fill(okcOffWhite);
  noStroke();
  float rect_x = x+30;
  float rect_y = y+200;
  float rect_w = screen_w-60;
  float rect_h = screen_h-250;
  rect(rect_x,rect_y,rect_w,rect_h,7);
  
  String prefix = "demo";
  float row1 = y+600;
  float col1 = x+(50);
  fill(0);
  textSize(14);
  textAlign(LEFT);
  
  drawEducationScreen2(col1,row1,prefix);
  row1 = row1-40;
  drawSignScreen2(col1,row1,prefix);
  row1 = row1-40;
  drawReligionScreen2(col1,row1,prefix);
  row1 = row1-40;
  drawBodyTypeScreen2(col1,row1,prefix);
  row1 = row1-40;
  drawRelationshipScreen2(col1,row1,prefix);
  row1 = row1-40;
  drawHeightScreen2(col1,row1,prefix);
  int human_height = 0;
  int feet = 0;
  int inches = 0;
  try
  {
    feet = Integer.parseInt(cp5.get(Textfield.class, prefix+"Feet").getText());
    feet = feet*12;
  }
  catch(Exception e)
  {}
  
  try
  {
    inches = Integer.parseInt(cp5.get(Textfield.class, prefix+"Inches").getText());
  }
  catch(Exception e)
  {}
  human_height = feet+inches;
  row1 = row1-40;
  drawEthnicityScreen2(col1,row1,prefix);
  row1 = row1-40;
  drawOrientationScreen2(col1,row1,prefix);
  row1 = row1-40;
  drawAgeScreen2(col1,row1,prefix);
  int age = -1;
  try
  {
    age = Integer.parseInt(cp5.get(Textfield.class, prefix+"Age").getText());
  }
  catch(Exception e)
  {}
  
  row1 = row1-40;
  drawGenderScreen2(col1,row1,prefix);
  
  float button_w = 100;
  float button_h = 50;
    
  noStroke();
      
  rect_x = (rect_x+rect_w-5)-(button_w);
  rect_y = (rect_y+rect_h)-button_h-5;
    
    if(player.getDemo().getGenders().isEmpty() || age <= 0 || stage > 2)
    {
      fill(100);
    }
    else
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT)
        {
           if(stage == 2)
           {
             stage = 3;
             player.getDemo().setAge(age);
             if(human_height > 0)
             {
               player.getDemo().setHeight(human_height);
             }
             newStage = true;
           }
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
    
    String buttonText = "Next";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
}

void drawScreen3(float x, float y)
{
  fill(255);
  textFont(TEXT_FONT);
  textSize(20);
  textAlign(CENTER);
  String helpText = player.getUsername() + ", please fill out all these questions to continue.\nDon't worry, they can all be changed later.";
  text(helpText,x+(screen_w/2),y+50);
  
  float question_x = x+(screen_w/2)-150;
  float question_y = y+200;
  player.drawActiveQ(question_x,question_y);
  
  float button_w = 100;
  float button_h = 50;
    
    noStroke();
      
    float rect_x = (x+screen_w/2)-(button_w/2);
    float rect_y = screen_h-button_h-30;
    
    if(player.questionsChanged() || unansweredQ)
    {
      unansweredQ = false;
      for(Question q:player.getQuestions())
      {
        if(q.getYourChoice() == null || q.getTheirChoice() == null)
        {
          unansweredQ = true;
        }
      }
    }
    
    if(unansweredQ || stage > 4)
    {
      fill(100);
    }
    else
    {
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT)
        {
           if(stage == 4)
           {
             initial = demo.copy();
             stage = 5;
             newStage = true;
           }
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
    
    String buttonText = "Next";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
}

void setupMaps()
{
    genderToString.put(Gender.WOMAN, "Woman");
    genderToString.put(Gender.MAN, "Man");
    genderToString.put(Gender.AGENDER, "Agender");
    genderToString.put(Gender.ANDROGYNOUS, "Androgynous");
    genderToString.put(Gender.BIGENDER, "Bigender");
    genderToString.put(Gender.CIS_MAN, "Cis Man");
    genderToString.put(Gender.CIS_WOMAN, "Cis Woman");
    genderToString.put(Gender.GENDERFLUID, "Genderfluid");
    genderToString.put(Gender.GENDERQUEER, "Genderqueer");
    genderToString.put(Gender.GENDER_NONCONFORMING, "Gender Nonconforming");
    genderToString.put(Gender.HIJRA, "Hijra");
    genderToString.put(Gender.INTERSEX, "Intersex");
    genderToString.put(Gender.NON_BINARY, "Non-Binary");
    genderToString.put(Gender.OTHER, "Other");
    genderToString.put(Gender.PANGENDER, "Pangender");
    genderToString.put(Gender.TRANSFEMININE, "Transfeminine");
    genderToString.put(Gender.TRANSGENDER, "Transgender");
    genderToString.put(Gender.TRANSMASCULINE, "Transmasculine");
    genderToString.put(Gender.TRANS_MAN, "Trans Man");
    genderToString.put(Gender.TRANS_WOMAN, "Trans Woman");
    genderToString.put(Gender.TWO_SPIRIT, "Two Spirit");
    
    stringToGender.put("Woman", Gender.WOMAN);
    stringToGender.put("Man", Gender.MAN);
    stringToGender.put("Agender", Gender.AGENDER);
    stringToGender.put("Androgynous", Gender.ANDROGYNOUS);
    stringToGender.put("Bigender", Gender.BIGENDER);
    stringToGender.put("Cis Man", Gender.CIS_MAN);
    stringToGender.put("Cis Woman", Gender.CIS_WOMAN);
    stringToGender.put("Genderfluid", Gender.GENDERFLUID);
    stringToGender.put("Genderqueer", Gender.GENDERQUEER);
    stringToGender.put("Gender Nonconforming", Gender.GENDER_NONCONFORMING);
    stringToGender.put("Hijra", Gender.HIJRA);
    stringToGender.put("Intersex", Gender.INTERSEX);
    stringToGender.put("Non-Binary", Gender.NON_BINARY);
    stringToGender.put("Other", Gender.OTHER);
    stringToGender.put("Pangender", Gender.PANGENDER);
    stringToGender.put("Transfeminine", Gender.TRANSFEMININE);
    stringToGender.put("Transgender", Gender.TRANSGENDER);
    stringToGender.put("Transmasculine", Gender.TRANSMASCULINE);
    stringToGender.put("Trans Man", Gender.TRANS_MAN);
    stringToGender.put("Trans Woman", Gender.TRANS_WOMAN);
    stringToGender.put("Two Spirit", Gender.TWO_SPIRIT);
    
    orientationToString.put(Orientation.STRAIGHT,"Straight");
    orientationToString.put(Orientation.GAY,"Gay");
    orientationToString.put(Orientation.BISEXUAL,"Bisexual");
    orientationToString.put(Orientation.ASEXUAL,"Asexual");
    orientationToString.put(Orientation.DEMISEXUAL,"Demisexual");
    orientationToString.put(Orientation.HETEROFLEXIBLE,"Heteroflexible");
    orientationToString.put(Orientation.LESBIAN,"Lesbian");
    orientationToString.put(Orientation.PANSEXUAL,"Pansexual");
    orientationToString.put(Orientation.QUEER,"Queer");
    orientationToString.put(Orientation.QUESTIONING,"Questioning");
    orientationToString.put(Orientation.SAPIOSEXUAL,"Sapiosexual");
    
    stringToOrientation.put("Straight",Orientation.STRAIGHT);
    stringToOrientation.put("Gay",Orientation.GAY);
    stringToOrientation.put("Bisexual",Orientation.BISEXUAL);
    stringToOrientation.put("Asexual",Orientation.ASEXUAL);
    stringToOrientation.put("Demisexual",Orientation.DEMISEXUAL);
    stringToOrientation.put("Heteroflexible",Orientation.HETEROFLEXIBLE);
    stringToOrientation.put("Lesbian",Orientation.LESBIAN);
    stringToOrientation.put("Pansexual",Orientation.PANSEXUAL);
    stringToOrientation.put("Queer",Orientation.QUEER);
    stringToOrientation.put("Questioning",Orientation.QUESTIONING);
    stringToOrientation.put("Sapiosexual",Orientation.SAPIOSEXUAL);
    
    ethnicityToString.put(Ethnicity.ASIAN,"Asian");
    ethnicityToString.put(Ethnicity.NATIVE_AMERICAN,"Native American");
    ethnicityToString.put(Ethnicity.INDIAN,"Indian");
    ethnicityToString.put(Ethnicity.MIDDLE_EASTERN,"Middle Eastern");
    ethnicityToString.put(Ethnicity.HISPANIC_LATIN,"Hispanic/Latin");
    ethnicityToString.put(Ethnicity.WHITE,"White");
    ethnicityToString.put(Ethnicity.BLACK,"Black");
    ethnicityToString.put(Ethnicity.PACIFIC_ISLANDER,"Pacific Islander");
    ethnicityToString.put(Ethnicity.OTHER,"Other");
    
    stringToEthnicity.put("Asian",Ethnicity.ASIAN);
    stringToEthnicity.put("Native American",Ethnicity.NATIVE_AMERICAN);
    stringToEthnicity.put("Indian",Ethnicity.INDIAN);
    stringToEthnicity.put("Middle Eastern",Ethnicity.MIDDLE_EASTERN);
    stringToEthnicity.put("Hispanic/Latin",Ethnicity.HISPANIC_LATIN);
    stringToEthnicity.put("White",Ethnicity.WHITE);
    stringToEthnicity.put("Black",Ethnicity.BLACK);
    stringToEthnicity.put("Pacific Islander",Ethnicity.PACIFIC_ISLANDER);
    stringToEthnicity.put("Other",Ethnicity.OTHER);
    
    rtToString.put(RelationshipType.STRICTLY_MONOGAMOUS,"Strictly Monogamous");
    rtToString.put(RelationshipType.MOSTLY_MONOGAMOUS,"Mostly Monogamous");
    rtToString.put(RelationshipType.STRICTLY_NONMONOGAMOUS,"Strictly Non-Monogamous");
    rtToString.put(RelationshipType.MOSTLY_NONMONOGAMOUS,"Mostly Non-Monogamous");
 
    stringToRT.put("Strictly Monogamous",RelationshipType.STRICTLY_MONOGAMOUS);
    stringToRT.put("Mostly Monogamous",RelationshipType.MOSTLY_MONOGAMOUS);
    stringToRT.put("Strictly Non-Monogamous",RelationshipType.STRICTLY_NONMONOGAMOUS);
    stringToRT.put("Mostly Non-Monogamous",RelationshipType.MOSTLY_NONMONOGAMOUS);
    
    btToString.put(BodyType.RATHER_NOT_SAY,"Rather Not Say");
    btToString.put(BodyType.THIN,"Thin");
    btToString.put(BodyType.OVERWEIGHT,"Overweight");
    btToString.put(BodyType.SKINNY,"Skinny");
    btToString.put(BodyType.AVERAGE_BUILD,"Average Build");
    btToString.put(BodyType.FIT,"Fit");
    btToString.put(BodyType.ATHLETIC,"Athletic");
    btToString.put(BodyType.JACKED,"Jacked");
    btToString.put(BodyType.A_LITTLE_EXTRA,"A Little Extra");
    btToString.put(BodyType.CURVY,"Curvy");
    btToString.put(BodyType.FULL_FIGURED,"Full Figured");
    btToString.put(BodyType.USED_UP,"Used Up");
    
    stringToBT.put("Rather Not Say",BodyType.RATHER_NOT_SAY);
    stringToBT.put("Thin",BodyType.THIN);
    stringToBT.put("Overweight",BodyType.OVERWEIGHT);
    stringToBT.put("Skinny",BodyType.SKINNY);
    stringToBT.put("Average Build",BodyType.AVERAGE_BUILD);
    stringToBT.put("Fit",BodyType.FIT);
    stringToBT.put("Athletic",BodyType.ATHLETIC);
    stringToBT.put("Jacked",BodyType.JACKED);
    stringToBT.put("A Little Extra",BodyType.A_LITTLE_EXTRA);
    stringToBT.put("Curvy",BodyType.CURVY);
    stringToBT.put("Full Figured",BodyType.FULL_FIGURED);
    stringToBT.put("Used Up",BodyType.USED_UP);
    
    religionToString.put(Religion.AGNOSTICISM,"Agnosticism");
    religionToString.put(Religion.ATHEISM,"Atheism");
    religionToString.put(Religion.CHRISTIANITY,"Christianity");
    religionToString.put(Religion.JUDAISM,"Judaism");
    religionToString.put(Religion.CATHOLICISM,"Catholicism");
    religionToString.put(Religion.ISLAM,"Islam");
    religionToString.put(Religion.HINDUISM,"Hinduism");
    religionToString.put(Religion.BUDDHISM,"Buddhism");
    religionToString.put(Religion.SIKH,"Sikh");
    religionToString.put(Religion.OTHER,"Other");
    
    stringToReligion.put("Agnosticism",Religion.AGNOSTICISM);
    stringToReligion.put("Atheism",Religion.ATHEISM);
    stringToReligion.put("Christianity",Religion.CHRISTIANITY);
    stringToReligion.put("Judaism",Religion.JUDAISM);
    stringToReligion.put("Catholicism",Religion.CATHOLICISM);
    stringToReligion.put("Islam",Religion.ISLAM);
    stringToReligion.put("Hinduism",Religion.HINDUISM);
    stringToReligion.put("Buddhism",Religion.BUDDHISM);
    stringToReligion.put("Sikh",Religion.SIKH);
    stringToReligion.put("Other",Religion.OTHER);
    
    signToString.put(Sign.AQUARIUS,"Aquarius");
    signToString.put(Sign.PISCES,"Pisces");
    signToString.put(Sign.ARIES,"Aries");
    signToString.put(Sign.TAURUS,"Taurus");
    signToString.put(Sign.GEMINI,"Gemini");
    signToString.put(Sign.CANCER,"Cancer");
    signToString.put(Sign.LEO,"Leo");
    signToString.put(Sign.VIRGO,"Virgo");
    signToString.put(Sign.LIBRA,"Libra");
    signToString.put(Sign.SCORPIO,"Scorpio");
    signToString.put(Sign.SAGITTARIUS,"Sagittarius");
    signToString.put(Sign.CAPRICORN,"Capricorn");
    
    stringToSign.put("Aquarius",Sign.AQUARIUS);
    stringToSign.put("Pisces",Sign.PISCES);
    stringToSign.put("Aries",Sign.ARIES);
    stringToSign.put("Taurus",Sign.TAURUS);
    stringToSign.put("Gemini",Sign.GEMINI);
    stringToSign.put("Cancer",Sign.CANCER);
    stringToSign.put("Leo",Sign.LEO);
    stringToSign.put("Virgo",Sign.VIRGO);
    stringToSign.put("Libra",Sign.LIBRA);
    stringToSign.put("Scorpio",Sign.SCORPIO);
    stringToSign.put("Sagittarius",Sign.SAGITTARIUS);
    stringToSign.put("Capricorn",Sign.CAPRICORN);
    
    educationToString.put(Education.HIGH_SCHOOL,"High School");
    educationToString.put(Education.TWO_YEAR_COLLEGE,"Two Year College");
    educationToString.put(Education.UNIVERSITY,"University");
    educationToString.put(Education.POST_GRAD,"Post Grad");
    
    stringToEducation.put("High School",Education.HIGH_SCHOOL);
    stringToEducation.put("Two Year College",Education.TWO_YEAR_COLLEGE);
    stringToEducation.put("University",Education.UNIVERSITY);
    stringToEducation.put("Post Grad",Education.POST_GRAD);
}

// Function to return an ArrayList of Strings
// (maybe redo to just make simple array?)
// Arguments: String to be wrapped, maximum width in pixels of line
ArrayList wordWrap(String s, int maxWidth) {
  // Make an empty ArrayList
  ArrayList a = new ArrayList();
  float w = 0;    // Accumulate width of chars
  int i = 0;      // Count through chars
  int rememberSpace = 0; // Remember where the last space was
  // As long as we are not at the end of the String
  while (i < s.length()) {
    // Current char
    char c = s.charAt(i);
    w += textWidth(c); // accumulate width
    if (c == ' ') rememberSpace = i; // Are we a blank space?
    if (w > maxWidth) {  // Have we reached the end of a line?
      String sub = s.substring(0,rememberSpace); // Make a substring
      // Chop off space at beginning
      if (sub.length() > 0 && sub.charAt(0) == ' ') {
        sub = sub.substring(1,sub.length());
      }
      // Add substring to the list
      a.add(sub);
      // Reset everything
      s = s.substring(rememberSpace,s.length());
      i = 0;
      w = 0;
    } 
    else {
      i++;  // Keep going!
    }
  }

  // Take care of the last remaining line
  if (s.length() > 0 && s.charAt(0) == ' ') {
    s = s.substring(1,s.length());
  }
  a.add(s);

  return a;
}

void drawGenderScreen2(float disp_x, float disp_y, String prefix)
{
  String genderDescription = "Gender (5 Max):";
  for(Gender g:player.getDemo().getGenders())
  {
      genderDescription = genderDescription + " " + genderToString.get(g) + ",";
  }
  //Remove final comma
  if(genderDescription.charAt(genderDescription.length()-1) == ',')
  {
    genderDescription = genderDescription
      .substring(0,genderDescription.length()-1);
  }
    
  text(genderDescription, disp_x, disp_y);
    
  disp_x = disp_x + (int) textWidth(genderDescription) + 5; 
  disp_y = disp_y - 10;
    
  if(cp5.get(ScrollableList.class, prefix+"Gender") == null)
  {
      cp5.addScrollableList(prefix + "Gender")
      .setPosition(disp_x, disp_y)
      .setBarHeight(20)
      .setItemHeight(20)
      .addItem("Woman",0)
      .addItem("Man",1)
      .addItem("Agender",2)
      .addItem("Androgynous",3)
      .addItem("Bigender",4)
      .addItem("Cis Man",5)
      .addItem("Cis Woman",6)
      .addItem("Genderfluid",7)
      .addItem("Genderqueer",8)
      .addItem("Gender Nonconforming",9)
      .addItem("Hijra",10)
      .addItem("Intersex",11)
      .addItem("Non-Binary",12)
      .addItem("Other",13)
      .addItem("Pangender",14)
      .addItem("Transfeminine",15)
      .addItem("Transgender",16)
      .addItem("Transmasculine",17)
      .addItem("Trans Man",18)
      .addItem("Trans Woman",19)
      .addItem("Two Spirit",20)
      .setType(ScrollableList.LIST)
      .setLabel("Gender")
      .setOpen(false);
      
  }
  else
  {
     if(stage <= 2)
     {
       cp5.get(ScrollableList.class, prefix+"Gender").setVisible(true);
     }
     else
     {
       cp5.get(ScrollableList.class, prefix+"Gender").setVisible(false);
     }
     cp5.get(ScrollableList.class, prefix+"Gender").setPosition(disp_x, disp_y);
  }
}

void drawAgeScreen2(float disp_x, float disp_y, String prefix)
{
    String ageDescription = "Age:";
    
    text(ageDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(ageDescription) + 5; 
    disp_y = disp_y - 10;
    
      if(cp5.get(Textfield.class, prefix+"Age") == null)
      {
        cp5.addTextfield(prefix + "Age")
           .setPosition(disp_x,disp_y)
           .setSize(20,20)
           .setFont(TEXT_FONT)
           .setLabel("");
      }
      else
      {
          if(stage <= 2)
          {
           cp5.get(Textfield.class, prefix+"Age").setVisible(true);
          }
          else
          {
           cp5.get(Textfield.class, prefix+"Age").setVisible(false);
          }
        cp5.get(Textfield.class, prefix+"Age").setPosition(disp_x, disp_y);
      }
      
}

void drawOrientationScreen2(float disp_x, float disp_y, String prefix)
{
    String orientationDescription = "Orientation (5 Max):";
    
    for(Orientation o:player.getDemo().getOrientations())
    {
      orientationDescription = orientationDescription + " " + orientationToString.get(o) + ",";
    }
    
    //Remove final comma
    if(orientationDescription.charAt(orientationDescription.length()-1) == ',')
    {
      orientationDescription = orientationDescription
        .substring(0,orientationDescription.length()-1);
    }
    
    text(orientationDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(orientationDescription) + 5; 
    disp_y = disp_y - 10;
    
      if(cp5.get(ScrollableList.class, prefix+"Orientation") == null)
      {
        cp5.addScrollableList(prefix + "Orientation")
        .setPosition(disp_x, disp_y)
        .setBarHeight(20)
        .setItemHeight(20)
        .addItems(orientationToString.values().toArray(new String[0]))
        .setType(ScrollableList.LIST)
        .setLabel("Orientation")
        .setOpen(false);
      }
      else
      {
         if(stage <= 2)
         {
           cp5.get(ScrollableList.class, prefix+"Orientation").setVisible(true);
         }
         else
         {
           cp5.get(ScrollableList.class, prefix+"Orientation").setVisible(false);
         }
        cp5.get(ScrollableList.class, prefix+"Orientation")
        .setPosition(disp_x, disp_y);
      }
}

void drawEthnicityScreen2(float disp_x, float disp_y, String prefix)
{
    String ethnicityDescription = "Ethnicity:";
    
    for(Ethnicity e:player.getDemo().getEthnicities())
    {
      ethnicityDescription = ethnicityDescription + " " + ethnicityToString.get(e) + ",";
    }
    
    //Remove final comma
    if(ethnicityDescription.charAt(ethnicityDescription.length()-1) == ',')
    {
      ethnicityDescription = ethnicityDescription
        .substring(0,ethnicityDescription.length()-1);
    }
    
    text(ethnicityDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(ethnicityDescription) + 5; 
    disp_y = disp_y - 10;
    
      if(cp5.get(ScrollableList.class, prefix+"Ethnicity") == null)
      {
        cp5.addScrollableList(prefix + "Ethnicity")
        .setPosition(disp_x, disp_y)
        .setBarHeight(20)
        .setItemHeight(20)
        .addItems(ethnicityToString.values().toArray(new String[0]))
        .setType(ScrollableList.LIST)
        .setLabel("Ethnicity")
        .setOpen(false);
      }
      else
      {
     if(stage <= 2)
     {
       cp5.get(ScrollableList.class, prefix+"Ethnicity").setVisible(true);
     }
     else
     {
       cp5.get(ScrollableList.class, prefix+"Ethnicity").setVisible(false);
     }
        cp5.get(ScrollableList.class, prefix+"Ethnicity")
        .setPosition(disp_x, disp_y);
     }
}

void drawHeightScreen2(float disp_x, float disp_y, String prefix)
{
    String heightDescription = "Height: ";
    text(heightDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(heightDescription) + 5; 
    disp_y = disp_y - 10;
    
    if(cp5.get(Textfield.class, prefix+"Feet") == null)
    {
        cp5.addTextfield(prefix + "Feet")
           .setPosition(disp_x,disp_y)
           .setSize(20,20)
           .setFont(TEXT_FONT)
           .setLabel("");
    }
    else
    {
           if(stage <= 2)
     {
       cp5.get(Textfield.class, prefix+"Feet").setVisible(true);
     }
     else
     {
       cp5.get(Textfield.class, prefix+"Feet").setVisible(false);
     }
      cp5.get(Textfield.class, prefix+"Feet").setPosition(disp_x, disp_y);
    }
      
      disp_x = disp_x + 25;
      disp_y = disp_y + 10;
      
      text("Ft",disp_x,disp_y);
      
      disp_x = disp_x + 15;
      disp_y = disp_y - 10;
      
      if(cp5.get(Textfield.class, prefix+"Inches") == null)
      {
        cp5.addTextfield(prefix + "Inches")
           .setPosition(disp_x,disp_y)
           .setSize(20,20)
           .setFont(TEXT_FONT)
           .setLabel("");
      }
      else
      {
             if(stage <= 2)
     {
       cp5.get(Textfield.class, prefix+"Inches").setVisible(true);
     }
     else
     {
       cp5.get(Textfield.class, prefix+"Inches").setVisible(false);
     }
        cp5.get(Textfield.class, prefix+"Inches").setPosition(disp_x, disp_y);
      }
      
      disp_x = disp_x + 25;
      disp_y = disp_y + 10;
      
      text("In",disp_x,disp_y);
}

void drawRelationshipScreen2(float disp_x, float disp_y, String prefix)
  {
    String relationshipDescription = "Relationship Type: ";
    RelationshipType relationship = player.getDemo().getRelationship();
    
    if(relationship != null)
    {
      relationshipDescription = relationshipDescription + rtToString.get(relationship);
    }
    
    text(relationshipDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(relationshipDescription) + 5;
    disp_y = disp_y - 10;
    
      if(cp5.get(ScrollableList.class, prefix+"Relationship") == null)
      {
        cp5.addScrollableList(prefix + "Relationship")
        .setPosition(disp_x, disp_y)
        .setSize(130,100)
        .setBarHeight(20)
        .setItemHeight(20)
        .addItem("Strictly Monogamous",0)
        .addItem("Mostly Monogamous",1)
        .addItem("Strictly Non-Monogamous",2)
        .addItem("Mostly Non-Monogamous",3)
        .setType(ScrollableList.LIST)
        .setLabel("Relationship Type")
        .setOpen(false);
      }
      else
      {
             if(stage <= 2)
     {
       cp5.get(ScrollableList.class, prefix+"Relationship").setVisible(true);
     }
     else
     {
       cp5.get(ScrollableList.class, prefix+"Relationship").setVisible(false);
     }
        cp5.get(ScrollableList.class, prefix+"Relationship").setPosition(disp_x, disp_y);
      }
  }
  
  void drawBodyTypeScreen2(float disp_x, float disp_y, String prefix)
  {
    String bodyDescription = "Body Type: ";
    BodyType bodytype = player.getDemo().getBodyType();
    
    
    if(bodytype != null)
    {
      bodyDescription = bodyDescription + btToString.get(bodytype);
    }
    
    text(bodyDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(bodyDescription) + 5;
    disp_y = disp_y - 10;
    
      if(cp5.get(ScrollableList.class, prefix+"Body") == null)
      {
        cp5.addScrollableList(prefix + "Body")
        .setPosition(disp_x, disp_y)
        .setSize(80,100)
        .setBarHeight(20)
        .setItemHeight(20)
        .addItem("Rather Not Say",0)
        .addItem("Thin",1)
        .addItem("Overweight",2)
        .addItem("Skinny",3)
        .addItem("Average Build",4)
        .addItem("Fit",5)
        .addItem("Athletic",6)
        .addItem("Jacked",7)
        .addItem("A Little Extra",8)
        .addItem("Curvy",9)
        .addItem("Full Figured",10)
        .addItem("Used Up",11)
        .setType(ScrollableList.LIST)
        .setLabel("Body Type")
        .setOpen(false);
      }
      else
      {
             if(stage <= 2)
     {
       cp5.get(ScrollableList.class, prefix+"Body").setVisible(true);
     }
     else
     {
       cp5.get(ScrollableList.class, prefix+"Body").setVisible(false);
     }
        cp5.get(ScrollableList.class, prefix+"Body").setPosition(disp_x, disp_y);
      }
  }
  
void drawReligionScreen2(float disp_x, float disp_y, String prefix)
  {
    String religionDescription = "Religion: ";
    Religion religion = player.getDemo().getReligion();
    
    if(religion != null)
    {
      religionDescription = religionDescription + religionToString.get(religion);
    }
    
    text(religionDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(religionDescription) + 5;
    disp_y = disp_y - 10;
    
      if(cp5.get(ScrollableList.class, prefix+"Religion") == null)
      {
        cp5.addScrollableList(prefix + "Religion")
        .setPosition(disp_x, disp_y)
        .setSize(80,100)
        .setBarHeight(20)
        .setItemHeight(20)
        .addItem("Agnosticism",0)
        .addItem("Atheism",1)
        .addItem("Christianity",2)
        .addItem("Judaism",3)
        .addItem("Catholicism",4)
        .addItem("Islam",5)
        .addItem("Hinduism",6)
        .addItem("Buddhism",7)
        .addItem("Sikh",8)
        .addItem("Other",9)
        .setType(ScrollableList.LIST)
        .setLabel("Religion")
        .setOpen(false);
        
      }
      else
      {
                     if(stage <= 2)
     {
       cp5.get(ScrollableList.class, prefix+"Religion").setVisible(true);
     }
     else
     {
       cp5.get(ScrollableList.class, prefix+"Religion").setVisible(false);
     }
        cp5.get(ScrollableList.class, prefix+"Religion").setPosition(disp_x, disp_y);
      }
  }
  
void drawSignScreen2(float disp_x, float disp_y, String prefix)
  {
    String signDescription = "Sign: ";
    Sign sign = player.getDemo().getSign();
    
    if(sign != null)
    {
      signDescription = signDescription + signToString.get(sign);
    }
    
    text(signDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(signDescription) + 5;
    disp_y = disp_y - 10;
    
      if(cp5.get(ScrollableList.class, prefix+"Sign") == null)
      {
        cp5.addScrollableList(prefix + "Sign")
        .setPosition(disp_x, disp_y)
        .setSize(80,100)
        .setBarHeight(20)
        .setItemHeight(20)
        .addItem("Aquarius",0)
        .addItem("Pisces",1)
        .addItem("Aries",2)
        .addItem("Taurus",3)
        .addItem("Gemini",4)
        .addItem("Cancer",5)
        .addItem("Leo",6)
        .addItem("Virgo",7)
        .addItem("Libra",8)
        .addItem("Scorpio",9)
        .addItem("Sagittarius",10)
        .addItem("Capricorn",11)
        .setType(ScrollableList.LIST)
        .setLabel("Sign")
        .setOpen(false);
        
      }
      else
      {
                     if(stage <= 2)
     {
       cp5.get(ScrollableList.class, prefix+"Sign").setVisible(true);
     }
     else
     {
       cp5.get(ScrollableList.class, prefix+"Sign").setVisible(false);
     }
        cp5.get(ScrollableList.class, prefix+"Sign").setPosition(disp_x, disp_y);
      }
  }
  
void drawEducationScreen2(float disp_x, float disp_y, String prefix)
  {
    String educationDescription = "Education: ";
    Education education = player.getDemo().getEducation();
    
    if(education != null)
    {
      educationDescription = educationDescription + educationToString.get(education);
    }
    
    text(educationDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(educationDescription) + 5;
    disp_y = disp_y - 10;
    
      if(cp5.get(ScrollableList.class, prefix+"Education") == null)
      {
        cp5.addScrollableList(prefix + "Education")
        .setPosition(disp_x, disp_y)
        .setSize(85,100)
        .setBarHeight(20)
        .setItemHeight(20)
        .addItem("High School",0)
        .addItem("Two Year College",1)
        .addItem("University",2)
        .addItem("Post Grad",3)
        .setType(ScrollableList.LIST)
        .setLabel("Education")
        .setOpen(false);
        
      }
      else
      {
                     if(stage <= 2)
     {
       cp5.get(ScrollableList.class, prefix+"Education").setVisible(true);
     }
     else
     {
       cp5.get(ScrollableList.class, prefix+"Education").setVisible(false);
     }
        cp5.get(ScrollableList.class, prefix+"Education").setPosition(disp_x, disp_y);
      }
  }
  
EnemyProfile make_jughead_muscle_carl(Profile target)
{
  EnemyProfile jmc = new EnemyProfile("jughead_muscle_carl", target);
  
  //First, demos
  Demographics jmcDemo = jmc.getDemo();
  jmcDemo.addGender(Gender.MAN);
  jmcDemo.addGender(Gender.CIS_MAN);
  jmcDemo.setAge(28);
  jmcDemo.addOrientation(Orientation.STRAIGHT);
  jmcDemo.addEthnicity(Ethnicity.WHITE);
  jmcDemo.setHeight(6*12);
  jmcDemo.setRelationship(RelationshipType.STRICTLY_MONOGAMOUS);
  jmcDemo.setBodyType(BodyType.ATHLETIC);
  jmcDemo.setSign(Sign.TAURUS);
  jmcDemo.setReligion(Religion.CHRISTIANITY);
  jmcDemo.setEducation(Education.UNIVERSITY);
  
  //Now, Questions
  
  Question Q1 = new Question("Have you ever been cruel to another person?", new String[]{"Yes","No"},false); //<>//
  
  Q1.selectYourChoice(1);
  Q1.selectTheirChoice(1);
  
  Question Q2 = new Question("Could you date someone who is androgynous?", new String[]{"Yes","No"}, false);
  
  Q2.selectYourChoice(1);
  Q2.selectTheirChoice(1);
  
  Question Q3 = new Question("Is supporting \"the troops\" the same thing as supporting a war?", new String[]{"Yes","No","Not Sure"}, false);
  
  Q3.selectYourChoice(1);
  Q3.selectTheirChoice(1);
  
  Question Q4 = new Question("Do you practice or believe in real magick, not to be confused with stage magic and parlor tricks?", new String[]{"Yes","No"}, false);
  
  Q4.selectYourChoice(1);
  Q4.selectTheirChoice(1);
  
  Question Q5 = new Question("Do you think the public should have access to any literature, regardless of its content?", new String[]{"Yes","No, some things should be censored"}, false);
  
  Q5.selectYourChoice(1);
  Q5.selectTheirChoice(1);
  
  Question Q6 = new Question("Which of the following do you find most liberating?", new String[]{"Travel","Financial Independence","Art","Sexuality"}, false);
  
  Q6.selectYourChoice(1);
  Q6.selectTheirChoice(3);
  
  Question Q7 = new Question("Would you launch nuclear weapons under any circumstances?", new String[]{"Yes","No"}, false);
  
  Q7.selectYourChoice(0);
  Q7.selectYourChoice(0);
  
  Question Q8 = new Question("Would you strongly prefer to go out with someone of your own skin color/racial background?", new String[]{"Yes","No"}, false);
  
  Q8.selectYourChoice(0);
  Q8.selectTheirChoice(0);
  
  Question Q9 = new Question("How important to you is a potential match's sense of humor?", new String[]{"Very important","Somewhat important","Not important"}, false);
  
  Q9.selectYourChoice(0);
  Q9.selectTheirChoice(0);
  
  Question Q10 = new Question("Do you think homosexuality is a sin?", new String[]{"Yes","No"}, false);
  
  Q10.selectYourChoice(1);
  Q10.selectTheirChoice(1);
  
  Question[] questions = new Question[]{Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10};
  jmc.setQuestions(questions);
  
  //Preferences
  
  Preference jmcPref = jmc.getPreference();
  HashSet<Gender> genderPref = new HashSet<Gender>();
  genderPref.add(Gender.WOMAN);
  genderPref.add(Gender.CIS_WOMAN);
  
  jmcPref.setGenders(genderPref);
  jmcPref.setAgeRange(21,28);
  
  HashSet<Orientation> orientationPref = new HashSet<Orientation>();
  orientationPref.add(Orientation.BISEXUAL);
  orientationPref.add(Orientation.STRAIGHT);
  orientationPref.add(Orientation.QUESTIONING);
  orientationPref.add(Orientation.SAPIOSEXUAL);
  orientationPref.add(Orientation.HETEROFLEXIBLE);
  jmcPref.setOrientations(orientationPref);
  
  HashSet<Ethnicity> ethnicityPref = new HashSet<Ethnicity>();
  ethnicityPref.add(Ethnicity.WHITE);
  jmcPref.setEthnicities(ethnicityPref);
  
  jmcPref.setHeightRange(0,6*12);
  
  HashSet<Religion> religionPref = new HashSet<Religion>();
  religionPref.add(Religion.CHRISTIANITY);
  
  
  
  //Essays
  
  Essay about = new Essay("My self-summary", "LAX!  My god is my light and true fire.\nEqual Opportunity Sex God.  It isn't hot to be tall, unless you're me.  Support our troops.");
  
  Essay[] essays = new Essay[]{about};
  
  jmc.setEssays(essays);
  
  return jmc;
}

EnemyProfile make_Book_Reader(Profile target)
{
  EnemyProfile BR = new EnemyProfile("Book_Reader", target);
  
  //First, demos
  Demographics BRDemo = BR.getDemo();
  BRDemo.addGender(Gender.TRANSFEMININE);
  BRDemo.setAge(32);
  BRDemo.addOrientation(Orientation.QUEER);
  BRDemo.addOrientation(Orientation.SAPIOSEXUAL);
  BRDemo.setHeight(5*12 + 7);
  BRDemo.setRelationship(RelationshipType.MOSTLY_NONMONOGAMOUS);
  BRDemo.setBodyType(BodyType.SKINNY);
  BRDemo.setSign(Sign.PISCES);
  BRDemo.setReligion(Religion.ATHEISM);
  BRDemo.setEducation(Education.POST_GRAD);
  
  //Now, Questions
  
  Question Q1 = new Question("Have you ever been cruel to another person?", new String[]{"Yes","No"},false);
  
  Q1.selectYourChoice(0);
  Q1.selectTheirChoice(0);
  
  Question Q2 = new Question("Could you date someone who is androgynous?", new String[]{"Yes","No"}, false);
  
  Q2.selectYourChoice(0);
  Q2.selectTheirChoice(0);
  
  Question Q3 = new Question("Is supporting \"the troops\" the same thing as supporting a war?", new String[]{"Yes","No","Not Sure"}, false, true);
  
  Q3.selectYourChoice(2);
  Q3.selectTheirChoice(2);
  
  Question Q4 = new Question("Do you practice or believe in real magick, not to be confused with stage magic and parlor tricks?", new String[]{"Yes","No"}, false);
  
  Q4.selectYourChoice(1);
  Q4.selectTheirChoice(1);
  
  Question Q5 = new Question("Do you think the public should have access to any literature, regardless of its content?", new String[]{"Yes","No, some things should be censored"}, false);
  
  Q5.selectYourChoice(0);
  Q5.selectTheirChoice(0);
  
  Question Q6 = new Question("Which of the following do you find most liberating?", new String[]{"Travel","Financial Independence","Art","Sexuality"}, false, true);
  
  Q6.selectYourChoice(2);
  Q6.selectTheirChoice(2);
  
  Question Q7 = new Question("Would you launch nuclear weapons under any circumstances?", new String[]{"Yes","No"}, false);
  
  Q7.selectYourChoice(1);
  Q7.selectYourChoice(1);
  
  Question Q8 = new Question("Would you strongly prefer to go out with someone of your own skin color/racial background?", new String[]{"Yes","No"}, false);
  
  Q8.selectYourChoice(1);
  Q8.selectTheirChoice(1);
  
  Question Q9 = new Question("How important to you is a potential match's sense of humor?", new String[]{"Very important","Somewhat important","Not important"}, false, true);
  
  Q9.selectYourChoice(1);
  Q9.selectTheirChoice(1);
  
  Question Q10 = new Question("Do you think homosexuality is a sin?", new String[]{"Yes","No"}, false);
  
  Q10.selectYourChoice(1);
  Q10.selectTheirChoice(1);
  
  Question[] questions = new Question[]{Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10};
  BR.setQuestions(questions);
  
  //Preferences
  
  Preference BRPref = BR.getPreference();
  BRPref.setAgeRange(25,40);
  
  HashSet<Religion> religionPref = new HashSet<Religion>();
  religionPref.add(Religion.AGNOSTICISM);
  religionPref.add(Religion.ATHEISM);
  BRPref.setReligions(religionPref);
  
  HashSet<Education> educationPref = new HashSet<Education>();
  educationPref.add(Education.UNIVERSITY);
  educationPref.add(Education.POST_GRAD);
  BRPref.setEducations(educationPref);
  
  //Essays
  
  Essay about = new Essay("My self-summary", "Voracious reader.  PhD Candidate in Medieval Literature.  Magic and god aren't real.");
  
  Essay[] essays = new Essay[]{about};
  
  BR.setEssays(essays);
  
  return BR;
}