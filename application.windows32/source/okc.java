import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Random; 
import java.util.Set; 
import java.util.HashSet; 
import java.util.Date; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class okc extends PApplet {



Question Q1 = new Question("Have you ever been cruel to another person?", new String[]{"Yes","No"},true);
Question Q2 = new Question("Could you date someone who is androgynous?", new String[]{"Yes","No"}, true);
Question Q3 = new Question("Is supporting \"the troops\" the same thing as supporting a war?", new String[]{"Yes","No","Not Sure"}, true);
Question Q4 = new Question("Do you practice or believe in real magick, not to be confused with stage magic and parlor tricks?", new String[]{"Yes","No"}, true);
Question Q5 = new Question("Do you think the public should have access to any literature, regardless of its content?", new String[]{"Yes","No, some things should be censored"}, true);
Question Q6 = new Question("Which of the following do you find most liberating?", new String[]{"Travel","Financial Independence","Art","Sexuality"}, true);
Question Q7 = new Question("Would you launch nuclear weapons under any circumstances?", new String[]{"Yes","No"}, true);
Question Q8 = new Question("Would you strongly prefer to go out with someone of your own skin color/racial background?", new String[]{"Yes","No"}, true);
Question Q9 = new Question("How important to you is a potential match's sense of humor?", new String[]{"Very important","Somewhat important","Not important"}, true);
Question Q10 = new Question("Do you think homosexuality is a sin?", new String[]{"Yes","No"}, true);

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
final int okcPink1 = color(249,59,102);
final int okcPink2 = color(252,236,242);
final int okcButton = color(50,96,199);
final int okcButton_mouseover = color(69,110,203);
final int okcBackground = color(16,77,161);
final int okcOffWhite = color(243,245,249);
final int okcPrivateQ = color(233,234,238);
final int okcPrivateQ2 = color(148,154,167);
final int green = color(0,192,0);
final int red = color(248,55,18);
final int red_mouseover = color(251,142,123);
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

ArrayList<PopupMessage> popups;
ArrayList<float[]> popupLocs;
boolean firstPops;

boolean draw35;

int feedbackAlpha = 0;
String feedbackMessage = "";

public void setup()
{
  setupMaps();
  
  
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
  EnemyProfile wis = make_Wicca_is_Sicca(player);
  EnemyProfile hmcltm = make_hold_me_closer_tiny_marxist(player);
  
  ArrayList<EnemyProfile> options = new ArrayList<EnemyProfile>();
  options.add(jmc);
  options.add(BR);
  options.add(wis);
  options.add(hmcltm);
  
  Random random = new Random();
  
  for(int i = 0; i < 3; i++)
  {
    int choice = random.nextInt(options.size());
    EnemyProfile enemy = options.remove(choice);
    enemies[i] = enemy;
  }
  
  stage = 0;
  newStage = true;
  
  selected = new CColor();
  selected.setBackground(color(255,0,0));
  
  oldColor = new CColor();
  oldColor.setBackground(color(0,45,90));
  
  draw35 = false;
}

public void draw()
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
    if(draw35)
    {
      drawScreen35(0,0);
    }
    else
    {
      drawScreen3(0,0);
    }
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
    drawScreen35(0,0);
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
    if(newStage && stage == 8)
    {
      firstPops = true;
      if(demoIndex == 1)
      {
        generatePopups(initial,phases[demoIndex-1]);
      }
      else
      {
        generatePopups(phases[demoIndex-2],phases[demoIndex-1]);
      }
    }
    drawScreen7(0,0);
    
    boolean allPopupsClosed = true;
    for(PopupMessage p:popups)
    {
      if(p.getVisible() == true)
      {
        allPopupsClosed = false;
      }
    }
    
    if(!wasVisible || oldMatch == 0 )
    {
      for(int i = 0; i < popups.size(); i++)
      {
        popups.get(i).drawAt(popupLocs.get(i)[0],popupLocs.get(i)[1]);
      }
      firstPops = false;
      
      if(demoIndex < 3 && allPopupsClosed)
      {
        stage = 9;
        newStage = true;
      }
      else if(allPopupsClosed)
      {
        stage = 10;
        newStage = true;
      }
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
  else if (stage == 10)
  {
    if(newStage)
    {
      tween = 0;
    }
    drawScreen7(0,0);
    fill(okcBackground);
    stroke(okcPink1);
    rect(-1,0,screen_w+2,tween);
    drawScreen10(0,tween-screen_h);
    tween=tween+10;
    
    if(tween >= screen_h)
    {
      stage = 11;
      newStage = true;
    }
    
    if(newStage && stage == 10)
    {
      newStage = false;
    }
  }
  else if(stage == 11)
  {
    drawScreen10(0,0);
  }
}

public void drawScreen10(float x, float y)
{
  fill(255);
  textFont(TEXT_FONT);
  textAlign(CENTER);
  textSize(100);
  text("The End\nScore: " + score,x+(screen_w/2),y+(screen_h/2));
}

public void drawScreen7(float x, float y)
{
  fill(okcOffWhite);
  noStroke();
  rect(x+300,y+150,screen_w-600,200,7);
  
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

public void drawScreen6()
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
    
    println("drawing enemy");
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

public void drawScreen1()
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
    
    int okcButton = color(50,96,199);
    int okcButton_mouseover = color(69,110,203);
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

public void drawScreen2(float x, float y)
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

public void drawScreen35(float x, float y)
{
  fill(255);
  textFont(TEXT_FONT);
  textSize(70);
  textAlign(CENTER);
  text("RULES",x+(screen_w/2),y+100);
  
  
  textSize(20);
  textAlign(CENTER);
  String helpText = player.getUsername() + ", you will be presented with a series of 3 profiles.\nYou will have 2 minutes to interact with each.\nYour demographic information determines if your profile is visible to that profile.\nYour score is determined by the Match %, which is governed by how your profile's questions and the other profile's questions match up.\nIf your profile is not visible to the other, your score for that round will be 0.";
  text(helpText,x,y+300,screen_w,400);
  
    float button_w = 100;
    float button_h = 50;
    
    noStroke();
      
    float rect_x = (x+screen_w/2)-(button_w/2);
    float rect_y = screen_h-button_h-100;
  
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
    rect(rect_x,rect_y,button_w,button_h,2);
    
    textFont(BUTTON_FONT);
    fill(255);
    textAlign(CENTER);
    
    String buttonText = "Next";
    text(buttonText, rect_x+(button_w)/2, rect_y+(button_h)/2 + 5);
}

public void drawScreen3(float x, float y)
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
             draw35 = true;
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

public void setupMaps()
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
public ArrayList wordWrap(String s, int maxWidth) {
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

public void drawGenderScreen2(float disp_x, float disp_y, String prefix)
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

public void drawAgeScreen2(float disp_x, float disp_y, String prefix)
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

public void drawOrientationScreen2(float disp_x, float disp_y, String prefix)
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
        .addItem("Straight",0)
        .addItem("Gay",1)
        .addItem("Bisexual",2)
        .addItem("Asexual",3)
        .addItem("Demisexual",4)
        .addItem("Heteroflexible",5)
        .addItem("Lesbian",6)
        .addItem("Pansexual",7)
        .addItem("Queer",8)
        .addItem("Questioning",9)
        .addItem("Sapiosexual",10)
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

public void drawEthnicityScreen2(float disp_x, float disp_y, String prefix)
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
        .addItem("Asian",0)
        .addItem("Native American",1)
        .addItem("Indian",2)
        .addItem("Middle Eastern",3)
        .addItem("Hispanic/Latin",4)
        .addItem("White",5)
        .addItem("Black",6)
        .addItem("Pacific Islander",7)
        .addItem("Other",8)
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

public void drawHeightScreen2(float disp_x, float disp_y, String prefix)
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

public void drawRelationshipScreen2(float disp_x, float disp_y, String prefix)
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
  
  public void drawBodyTypeScreen2(float disp_x, float disp_y, String prefix)
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
        .setSize(80,200)
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
  
public void drawReligionScreen2(float disp_x, float disp_y, String prefix)
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
        .setSize(80,150)
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
  
public void drawSignScreen2(float disp_x, float disp_y, String prefix)
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
        .setSize(80,150)
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
  
public void drawEducationScreen2(float disp_x, float disp_y, String prefix)
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
  
public EnemyProfile make_jughead_muscle_carl(Profile target)
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
  
  Question Q1 = new Question("Have you ever been cruel to another person?", new String[]{"Yes","No"},false);
  
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
  Q7.selectTheirChoice(0);
  
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
  
  Essay about = new Essay("My self-summary", "LAX!  My god is my light and true fire.\nEqual Opportunity Sex God (within reason).  It isn't hot to be tall, unless you're me.  Support our troops.");
  
  Essay[] essays = new Essay[]{about};
  
  jmc.setEssays(essays);
  
  return jmc;
}

public EnemyProfile make_Book_Reader(Profile target)
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
  Q7.selectTheirChoice(1);
  
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

public EnemyProfile make_hold_me_closer_tiny_marxist(Profile target)
{
  EnemyProfile hmcltm = new EnemyProfile("HoldMeCloserTinyMarxist", target);
  
  //First, demos
  Demographics hmcltmDemo = hmcltm.getDemo();
  hmcltmDemo.addGender(Gender.CIS_MAN);
  hmcltmDemo.setAge(45);
  hmcltmDemo.addOrientation(Orientation.STRAIGHT);
  hmcltmDemo.addEthnicity(Ethnicity.INDIAN);
  hmcltmDemo.setHeight(5*12 + 8);
  hmcltmDemo.setRelationship(RelationshipType.MOSTLY_NONMONOGAMOUS);
  hmcltmDemo.setBodyType(BodyType.USED_UP);
  hmcltmDemo.setSign(Sign.CAPRICORN);
  hmcltmDemo.setReligion(Religion.ATHEISM);
  hmcltmDemo.setEducation(Education.UNIVERSITY);
  
  //Now, Questions
  
  Question Q1 = new Question("Have you ever been cruel to another person?", new String[]{"Yes","No"},false);
  
  Q1.selectYourChoice(0);
  Q1.selectTheirChoice(0);
  
  Question Q2 = new Question("Could you date someone who is androgynous?", new String[]{"Yes","No"}, false);
  
  Q2.selectYourChoice(0);
  Q2.selectTheirChoice(0);
  
  Question Q3 = new Question("Is supporting \"the troops\" the same thing as supporting a war?", new String[]{"Yes","No","Not Sure"}, false);
  
  Q3.selectYourChoice(0);
  Q3.selectTheirChoice(0);
  
  Question Q4 = new Question("Do you practice or believe in real magick, not to be confused with stage magic and parlor tricks?", new String[]{"Yes","No"}, false);
  
  Q4.selectYourChoice(1);
  Q4.selectTheirChoice(1);
  
  Question Q5 = new Question("Do you think the public should have access to any literature, regardless of its content?", new String[]{"Yes","No, some things should be censored"}, false);
  
  Q5.selectYourChoice(0);
  Q5.selectTheirChoice(0);
  
  Question Q6 = new Question("Which of the following do you find most liberating?", new String[]{"Travel","Financial Independence","Art","Sexuality"}, false, true);
  
  Q6.selectYourChoice(1);
  Q6.selectTheirChoice(1);
  
  Question Q7 = new Question("Would you launch nuclear weapons under any circumstances?", new String[]{"Yes","No"}, false);
  
  Q7.selectYourChoice(1);
  Q7.selectTheirChoice(1);
  
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
  hmcltm.setQuestions(questions);
  
  //Preferences
  
  Preference hmcltmPref = hmcltm.getPreference();
  HashSet<Gender> genderPref = new HashSet<Gender>();
  genderPref.add(Gender.CIS_WOMAN);
  genderPref.add(Gender.WOMAN);
  genderPref.add(Gender.TRANSFEMININE);
  genderPref.add(Gender.TRANS_WOMAN);
  genderPref.add(Gender.TWO_SPIRIT);
  genderPref.add(Gender.INTERSEX);
  genderPref.add(Gender.BIGENDER);
  genderPref.add(Gender.GENDERQUEER);
  genderPref.add(Gender.GENDERFLUID);
  genderPref.add(Gender.PANGENDER);
  
  hmcltmPref.setGenders(genderPref);
  hmcltmPref.setAgeRange(45,55);
  
  HashSet<Ethnicity> ethnicityPref = new HashSet<Ethnicity>();
  ethnicityPref.add(Ethnicity.OTHER);
  ethnicityPref.add(Ethnicity.BLACK);
  ethnicityPref.add(Ethnicity.INDIAN);
  ethnicityPref.add(Ethnicity.NATIVE_AMERICAN);
  ethnicityPref.add(Ethnicity.MIDDLE_EASTERN);
  ethnicityPref.add(Ethnicity.ASIAN);
  ethnicityPref.add(Ethnicity.PACIFIC_ISLANDER);
  ethnicityPref.add(Ethnicity.HISPANIC_LATIN);
  hmcltmPref.setEthnicities(ethnicityPref);
  
  HashSet<RelationshipType> rlPref = new HashSet<RelationshipType>();
  rlPref.add(RelationshipType.STRICTLY_NONMONOGAMOUS);
  rlPref.add(RelationshipType.MOSTLY_NONMONOGAMOUS);
  
  hmcltmPref.setRTypes(rlPref);
  
  
  //Essays
  
  Essay about = new Essay("My self-summary","My two favorite things are elton john and communism.  No offense, but no white people, please.\nMy heart & the state can only support poly relationships.");
  
  Essay[] essays = new Essay[]{about};
  
  hmcltm.setEssays(essays);
  
  return hmcltm;
}

public EnemyProfile make_Wicca_is_Sicca(Profile target)
{
  EnemyProfile wis = new EnemyProfile("Wicca_is_Sicca", target);
  
  //First, demos
  Demographics wisDemo = wis.getDemo();
  wisDemo.addGender(Gender.ANDROGYNOUS);
  wisDemo.setAge(40);
  wisDemo.addOrientation(Orientation.QUEER);
  wisDemo.addEthnicity(Ethnicity.BLACK);
  wisDemo.addEthnicity(Ethnicity.OTHER);
  wisDemo.setHeight(5*12 + 5);
  wisDemo.setRelationship(RelationshipType.MOSTLY_MONOGAMOUS);
  wisDemo.setBodyType(BodyType.A_LITTLE_EXTRA);
  wisDemo.setSign(Sign.VIRGO);
  wisDemo.setReligion(Religion.OTHER);
  wisDemo.setEducation(Education.UNIVERSITY);
  
  //Now, Questions
  
  Question Q1 = new Question("Have you ever been cruel to another person?", new String[]{"Yes","No"},false);
  
  Q1.selectYourChoice(0);
  Q1.selectTheirChoice(0);
  
  Question Q2 = new Question("Could you date someone who is androgynous?", new String[]{"Yes","No"}, false);
  
  Q2.selectYourChoice(0);
  Q2.selectTheirChoice(0);
  
  Question Q3 = new Question("Is supporting \"the troops\" the same thing as supporting a war?", new String[]{"Yes","No","Not Sure"}, false);
  
  Q3.selectYourChoice(2);
  Q3.selectTheirChoice(2);
  
  Question Q4 = new Question("Do you practice or believe in real magick, not to be confused with stage magic and parlor tricks?", new String[]{"Yes","No"}, false);
  
  Q4.selectYourChoice(0);
  Q4.selectTheirChoice(0);
  
  Question Q5 = new Question("Do you think the public should have access to any literature, regardless of its content?", new String[]{"Yes","No, some things should be censored"}, false);
  
  Q5.selectYourChoice(0);
  Q5.selectTheirChoice(0);
  
  Question Q6 = new Question("Which of the following do you find most liberating?", new String[]{"Travel","Financial Independence","Art","Sexuality"}, false);
  
  Q6.selectYourChoice(3);
  Q6.selectTheirChoice(3);
  
  Question Q7 = new Question("Would you launch nuclear weapons under any circumstances?", new String[]{"Yes","No"}, false);
  
  Q7.selectYourChoice(1);
  Q7.selectTheirChoice(1);
  
  Question Q8 = new Question("Would you strongly prefer to go out with someone of your own skin color/racial background?", new String[]{"Yes","No"}, false);
  
  Q8.selectYourChoice(1);
  Q8.selectTheirChoice(1);
  
  Question Q9 = new Question("How important to you is a potential match's sense of humor?", new String[]{"Very important","Somewhat important","Not important"}, false);
  
  Q9.selectYourChoice(2);
  Q9.selectTheirChoice(2);
  
  Question Q10 = new Question("Do you think homosexuality is a sin?", new String[]{"Yes","No"}, false);
  
  Q10.selectYourChoice(1);
  Q10.selectTheirChoice(1);
  
  Question[] questions = new Question[]{Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10};
  wis.setQuestions(questions);
  
  //Preferences
  
  Preference wisPref = wis.getPreference();
  HashSet<Gender> genderPref = new HashSet<Gender>();
  genderPref.add(Gender.AGENDER);
  genderPref.add(Gender.ANDROGYNOUS);
  genderPref.add(Gender.BIGENDER);
  genderPref.add(Gender.TWO_SPIRIT);
  genderPref.add(Gender.GENDER_NONCONFORMING);
  genderPref.add(Gender.INTERSEX);
  genderPref.add(Gender.NON_BINARY);
  genderPref.add(Gender.GENDERQUEER);
  genderPref.add(Gender.GENDERFLUID);
  genderPref.add(Gender.PANGENDER);
  
  wisPref.setGenders(genderPref);
  wisPref.setAgeRange(35,45);
  
  HashSet<Religion> religionPref = new HashSet<Religion>();
  religionPref.add(Religion.OTHER);
  wisPref.setReligions(religionPref);
  
  HashSet<Sign> signPref = new HashSet<Sign>();
  signPref.add(Sign.TAURUS);
  signPref.add(Sign.CANCER);
  signPref.add(Sign.SCORPIO);
  signPref.add(Sign.CAPRICORN);
  wisPref.setSign(signPref);
  
  HashSet<RelationshipType> rlPref = new HashSet<RelationshipType>();
  rlPref.add(RelationshipType.STRICTLY_MONOGAMOUS);
  rlPref.add(RelationshipType.MOSTLY_MONOGAMOUS);
  
  wisPref.setRTypes(rlPref);
  
  
  //Essays
  
  Essay about = new Essay("My self-summary","Not interested if you find your gender on a line. Our signs have GOT to be compatible.\nThe spirits might be free, but I'm not- not interested in polyamory.");
  Essay qs = new Essay("Questions, Complaints, Improvements?", "There's no Wicca option in the religions menu!");
  
  Essay[] essays = new Essay[]{about,qs};
  
  wis.setEssays(essays);
  
  return wis;
}

public void generatePopups(Demographics before, Demographics after)
{
  Random random = new Random();
  popups = new ArrayList<PopupMessage>();
  popupLocs = new ArrayList<float[]>();
  
  if(before.getHeight() < after.getHeight())
  {
    popups.add(new PopupMessage(player.getUsername(), "height_queen", "I love how tall you're becoming! So delciously tall!!!  When they're tall... I want to bawl!"));
  }
  else if(before.getHeight() > after.getHeight())
  {
    popups.add(new PopupMessage(player.getUsername(), "short_stack", "Shorther!  Shooorter!!! Sortter!!  I love 'em shhort"));
  }
  
  if(before.getAge() < after.getAge())
  {
    popups.add(new PopupMessage(player.getUsername(), "death", "You're older than you were before, you creep closer to death......."));
  }
  else if(before.getAge() > after.getAge())
  {
    popups.add(new PopupMessage(player.getUsername(), "spiffy_jim", "Um... people only age in the opposite direction than the way you're aging.  What gives?"));
  }
  
  Set<Gender> beforeG = before.getGenders();
  Set<Gender> afterG = after.getGenders();
  
  if(!beforeG.contains(Gender.WOMAN) && !beforeG.contains(Gender.TRANS_WOMAN) && !beforeG.contains(Gender.TRANSFEMININE) 
  && !beforeG.contains(Gender.BIGENDER) && !beforeG.contains(Gender.TWO_SPIRIT) && !beforeG.contains(Gender.HIJRA))
  {
    if(afterG.contains(Gender.WOMAN) || afterG.contains(Gender.TRANS_WOMAN) || afterG.contains(Gender.TRANSFEMININE))
    {
      popups.add(new PopupMessage(player.getUsername(), "femmme_fatale", "i see you're getting in touch with your femininity.  hmu if you want someone to REALLY get in touch with your femininity ;)"));
    }
  }
  
  if(!beforeG.contains(Gender.MAN) && !beforeG.contains(Gender.TRANS_MAN) && !beforeG.contains(Gender.TRANSMASCULINE) 
  && !beforeG.contains(Gender.BIGENDER) && !beforeG.contains(Gender.TWO_SPIRIT) && !beforeG.contains(Gender.HIJRA))
  {
    if(afterG.contains(Gender.MAN) || afterG.contains(Gender.TRANS_MAN) || afterG.contains(Gender.TRANSMASCULINE))
    {
      popups.add(new PopupMessage(player.getUsername(), "chad_the_dude_hugger", "HULK SMASH! haha no just kidding, but welcome to a more masculine world.  let's get ripped from our pecs to our toes"));
    }
  }
  
  if(before.getReligion() != after.getReligion())
  {
    if(before.getReligion() == null)
    {
      popups.add(new PopupMessage(player.getUsername(), "Guru_Child", "I see you've begun a path along " + religionToString.get(after.getReligion()) + ". I wish you luck on your spiritual journey."));
    }
    else
    {
      popups.add(new PopupMessage(player.getUsername(), "Guru_Child", "Abandoning " + religionToString.get(before.getReligion()) + " must not have been an easy choice.  I hope " +
        religionToString.get(after.getReligion()) + " will bring you fulfillment."));
    }
  }
  
  if(before.getEthnicities().size() <=3 && after.getEthnicities().size() > 3)
  {
    String message = "I see you're ";
    
    for(Ethnicity e: after.getEthnicities())
    {
      message = message + ethnicityToString.get(e) + ", ";
    }
    
    message = message + "but where are you FROM, really?";
    
    popups.add(new PopupMessage(player.getUsername(), "carhorn_leghorn", message));
  }
  
  if(after.getRelationship() == RelationshipType.STRICTLY_NONMONOGAMOUS || after.getRelationship() == RelationshipType.MOSTLY_NONMONOGAMOUS)
  {
    popups.add(new PopupMessage(player.getUsername(), "poly_molly", "oooooo, increasing number of people with whom you feel capable of sharing your affection, huh? hmm?"));
  }
  
  if(after.getRelationship() == RelationshipType.STRICTLY_MONOGAMOUS || after.getRelationship() == RelationshipType.MOSTLY_MONOGAMOUS)
  {
    popups.add(new PopupMessage(player.getUsername(), "youre_the_only_one", "Now that you only feel like there's only one person romantically at a time for you, I hope that person is me!"));
  }
  
  for(int i = 0; i < popups.size(); i++)
  {
    popupLocs.add(new float[2]);
    popupLocs.get(i)[0] = random.nextInt(screen_w-500);
    popupLocs.get(i)[1] = random.nextInt(screen_h-400);
  }
}

public void displayFeedback()
{
  
  textFont(TEXT_FONT);
  textSize(400);
  textAlign(CENTER);
  fill(0,feedbackAlpha);
  text(feedbackMessage,screen_w/2,screen_h/2 + 100);
  
  if(feedbackAlpha > 0)
  {
    feedbackAlpha = feedbackAlpha-1;
  }
}

public void setFeedback(String msg)
{
  feedbackAlpha = 100;
  feedbackMessage = msg;
}





public enum Gender {WOMAN, MAN, AGENDER, ANDROGYNOUS, 
                    BIGENDER, CIS_MAN, CIS_WOMAN, GENDERFLUID,
                    GENDERQUEER, GENDER_NONCONFORMING, HIJRA, INTERSEX,
                    NON_BINARY, OTHER, PANGENDER, TRANSFEMININE,
                    TRANSGENDER, TRANSMASCULINE, TRANS_MAN, TRANS_WOMAN,
                    TWO_SPIRIT};
                    
public enum Orientation {STRAIGHT, GAY, BISEXUAL, ASEXUAL, 
                        DEMISEXUAL, HETEROFLEXIBLE, LESBIAN, PANSEXUAL,
                        QUEER, QUESTIONING, SAPIOSEXUAL};
                        
public enum Ethnicity {ASIAN, NATIVE_AMERICAN, INDIAN,
                       MIDDLE_EASTERN, HISPANIC_LATIN, WHITE,
                       BLACK, PACIFIC_ISLANDER, OTHER};
                       
public enum BodyType {RATHER_NOT_SAY, THIN, OVERWEIGHT, SKINNY,
                      AVERAGE_BUILD, FIT, ATHLETIC, JACKED,
                      A_LITTLE_EXTRA, CURVY, FULL_FIGURED, USED_UP};
                      
public enum Smoking {NO, SOMETIMES, YES};

public enum Drugs {NEVER, SOMETIMES, OFTEN};

public enum Religion {AGNOSTICISM, ATHEISM, CHRISTIANITY, JUDAISM,
                      CATHOLICISM, ISLAM, HINDUISM, BUDDHISM,
                      SIKH, OTHER};
                      
public enum Sign {AQUARIUS, PISCES, ARIES, TAURUS,
                  GEMINI, CANCER, LEO, VIRGO,
                  LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN};

public enum Education {HIGH_SCHOOL, TWO_YEAR_COLLEGE, UNIVERSITY,
                       POST_GRAD};   
                       
public enum RelationshipType {STRICTLY_MONOGAMOUS, MOSTLY_MONOGAMOUS, STRICTLY_NONMONOGAMOUS, MOSTLY_NONMONOGAMOUS};           
                    
public HashMap<Gender,String> genderToString = new HashMap<Gender,String>();
public HashMap<String,Gender> stringToGender = new HashMap<String,Gender>();
  
public HashMap<Orientation,String> orientationToString = new HashMap<Orientation,String>();
public HashMap<String,Orientation> stringToOrientation = new HashMap<String,Orientation>();
  
public HashMap<Ethnicity,String> ethnicityToString = new HashMap<Ethnicity,String>();
public HashMap<String,Ethnicity> stringToEthnicity = new HashMap<String,Ethnicity>();
  
public HashMap<RelationshipType,String> rtToString = new HashMap<RelationshipType,String>();
public HashMap<String,RelationshipType> stringToRT = new HashMap<String,RelationshipType>();
  
public HashMap<BodyType,String> btToString = new HashMap<BodyType,String>();
public HashMap<String,BodyType> stringToBT = new HashMap<String,BodyType>();
  
public HashMap<Religion,String> religionToString = new HashMap<Religion,String>();
public HashMap<String,Religion> stringToReligion = new HashMap<String,Religion>();
  
public HashMap<Sign,String> signToString = new HashMap<Sign,String>();
public HashMap<String,Sign> stringToSign = new HashMap<String,Sign>();
  
public HashMap<Education,String> educationToString = new HashMap<Education,String>();
public HashMap<String,Education> stringToEducation = new HashMap<String,Education>();                    

class Demographics
{
  private boolean editing;
  private boolean okToSwitch;
  private int currentSwitchInterval;
  private int switchInterval = 10;
  private boolean changed;
  
  private HashSet<Gender> gender;
  
  private int age;
  
  private HashSet<Orientation> orientation;
  private HashSet<Ethnicity> ethnicity;
  
  //Measured to the nearest inch
  private int human_height;
  
  private RelationshipType relationship;
  
  private BodyType bodytype;
  
  private Smoking smoking;
  private Drugs drugs;
  
  private Religion religion;
  
  private Sign sign;
  
  private Education education;
  
  private String prefix;
  
  private final int MIN_WIDTH = 600;
  private final int MIN_HEIGHT = 300;
  
  public Demographics(String prefix)
  {
    gender = new HashSet(5);
    orientation = new HashSet(5);
    ethnicity = new HashSet(Ethnicity.values().length);
    this.prefix = prefix;
    editing = false;
    okToSwitch = true;
    currentSwitchInterval = 0;
    changed = true;
  }
  
  public Demographics copy()
  {
    Demographics copy = new Demographics(prefix + "_copy");
    for(Gender g:gender)
    {
      copy.addGender(g);
    }
    for(Orientation o:orientation)
    {
      copy.addOrientation(o);
    }
    for(Ethnicity e:ethnicity)
    {
      copy.addEthnicity(e);
    }
    copy.setAge(age);
    copy.setHeight(human_height);
    
    copy.setRelationship(relationship);
    copy.setBodyType(bodytype);
    copy.setReligion(religion);
    copy.setSign(sign);
    copy.setEducation(education);
    
    return copy;
  }
  
  public void setEditing(boolean editing)
  {
    this.editing = editing;
  }
  
  public boolean getEditing()
  {
    return editing;
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
  
  public void drawAt(float x, float y, PFont TEXT_FONT, PFont BUTTON_FONT)
  {
    int dispAge = -1;
    if(editing)
    {
      try
      {
        dispAge = Integer.parseInt(cp5.get(Textfield.class, "demoAge").getText());
      }
      catch(Exception e)
      {}
    }
    int disp_height = -1;
    int feet = 0;
    int inches = 0;
    if(editing)
    {
      try
      {
        feet = Integer.parseInt(cp5.get(Textfield.class, "demoFeet").getText());
        feet = feet*12;
      }
      catch(Exception e)
      {}
        
      try
      {
        inches = Integer.parseInt(cp5.get(Textfield.class, "demoInches").getText());
      }
      catch(Exception e)
      {}
      disp_height = feet+inches;
    }
    
    
    int d_width = MIN_WIDTH;
    int d_height = MIN_HEIGHT;
    float interval = 25;
    float startBuffer = 15;
    
    float end = interval*9 + startBuffer;
    
    fill(okcOffWhite);
    noStroke();
    
    rect(x, y, d_width, d_height,7);
    
    //Editing button
    float button_w = 100;
    float button_h = 50;
    
    int okcButton = color(50,96,199);
    int okcButton_mouseover = color(69,110,203);
    
    noStroke();
    
    float rect_x = x+d_width-button_w-15;
    float rect_y = y+d_height-button_h-15;
    
    if(overRect(rect_x,rect_y,button_w,button_h))
    {
      fill(okcButton_mouseover);
      if(mousePressed && mouseButton == LEFT && okToSwitch)
      {
        if(editing)
        {
          if(dispAge > 0)
          {
            age = dispAge;
          }
          
          if(disp_height > 0)
          {
            human_height = disp_height;
          }
        }
        editing = !editing;
        okToSwitch = false;
        currentSwitchInterval = 0;
        if(editing == false)
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
    
    textFont(BUTTON_FONT);
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
    
    textFont(TEXT_FONT);
    fill(0);
    textAlign(LEFT);
    
    float disp_x = x+5;
    float disp_y = y+end;
    
    disp_y = y+end;
    
    drawEducation(disp_x, disp_y);
    
    disp_y = disp_y-interval;
    
    drawSign(disp_x, disp_y);
    
    disp_y = disp_y-interval;
    
    drawReligion(disp_x, disp_y);
    
    disp_y = disp_y-interval;
    
    drawBodyType(disp_x, disp_y);
    
    disp_y = disp_y-interval;
    
    drawRelationship(disp_x, disp_y);
    
    disp_y = disp_y-interval;
    
    drawHeight(disp_x, disp_y);
   
    disp_y = disp_y-interval;
    
    drawEthnicity(disp_x,disp_y);
    
    disp_y = disp_y-interval;
    
    drawOrientation(disp_x,disp_y);
   
    disp_y = disp_y-interval;
    
    drawAge(disp_x,disp_y);
   
    disp_y = disp_y-interval;
    
    drawGender(disp_x,disp_y); 
    
    if(currentSwitchInterval < switchInterval)
    {
      currentSwitchInterval = currentSwitchInterval+1;
    }
    else
    {
      okToSwitch = true;
    }
    
    fill(255);
  }
  
  private void drawGender(float disp_x, float disp_y)
  {
    String genderDescription = "Gender:";
    
    for(Gender g:gender)
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
    
    if(editing)
    {
      if(cp5.get(ScrollableList.class, prefix+"Gender") == null)
      {
        cp5.addScrollableList(prefix + "Gender")
        .setPosition(disp_x, disp_y)
        .setBarHeight(20)
        .setItemHeight(20)
        .addItems(genderToString.values().toArray(new String[0]))
        .setType(ScrollableList.LIST)
        .setLabel("Gender")
        .setOpen(false);
      }
      else
      {
        cp5.get(ScrollableList.class, prefix+"Gender").setVisible(true);
        cp5.get(ScrollableList.class, prefix+"Gender").setPosition(disp_x, disp_y);
      }
    }
    else
    {
      if(cp5.get(ScrollableList.class, prefix+"Gender") != null)
      {
        cp5.get(ScrollableList.class, prefix+"Gender").setOpen(false);
        cp5.get(ScrollableList.class, prefix+"Gender").setVisible(false);
      }
    }
  }
  
  private void drawAge(float disp_x, float disp_y)
  {
    String ageDescription = "Age:";
    
    if(!editing)
    {
      if(age > 0)
      {
        ageDescription = ageDescription + " " + age;
      }
    }
    
    text(ageDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(ageDescription) + 5;
    disp_y = disp_y - 10;
    
    if(editing)
    {
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
        cp5.get(Textfield.class, prefix+"Age").setVisible(true);
        cp5.get(Textfield.class, prefix+"Age").setPosition(disp_x, disp_y);
      }
    }
    else
    {
      if(cp5.get(Textfield.class, prefix+"Age") != null)
      {
        cp5.get(Textfield.class, prefix+"Age").setText("");
        cp5.get(Textfield.class, prefix+"Age").setVisible(false);
      }
    }
  }
  
  private void drawOrientation(float disp_x, float disp_y)
  {
    String orientationDescription = "Orientation:";
    
    for(Orientation o:orientation)
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
    
    if(editing)
    {
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
        cp5.get(ScrollableList.class, prefix+"Orientation")
        .setPosition(disp_x, disp_y)
        .setVisible(true);
      }
    }
    else
    {
      if(cp5.get(ScrollableList.class, prefix+"Orientation") != null)
      {
        cp5.get(ScrollableList.class, prefix+"Orientation")
        .setVisible(false)
        .setOpen(false);
      }
    }
  }
  
  private void drawEthnicity(float disp_x, float disp_y)
  {
    String ethnicityDescription = "Ethnicity:";
    
    for(Ethnicity e:ethnicity)
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
    
    if(editing)
    {
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
        cp5.get(ScrollableList.class, prefix+"Ethnicity")
        .setVisible(true)
        .setPosition(disp_x, disp_y);
      }
    }
    else
    {
      if(cp5.get(ScrollableList.class, prefix+"Ethnicity") != null)
      {
        cp5.get(ScrollableList.class, prefix+"Ethnicity")
        .setVisible(false)
        .setOpen(false);
      }
    }
  }
  
  private void drawHeight(float disp_x, float disp_y)
  {
    String heightDescription = "Height: ";
    
    if(!editing)
    {
      int feet = human_height/12;
      int inches = human_height%12;
      
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
    }
    
    text(heightDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(heightDescription) + 5; 
    disp_y = disp_y - 10;
    
    if(editing)
    {
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
        cp5.get(Textfield.class, prefix+"Feet").setPosition(disp_x, disp_y).setVisible(true);
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
        cp5.get(Textfield.class, prefix+"Inches").setPosition(disp_x, disp_y).setVisible(true);
      }
      
      disp_x = disp_x + 25;
      disp_y = disp_y + 10;
      
      text("In",disp_x,disp_y);
    }
    else
    {
      if(cp5.get(Textfield.class, prefix+"Inches") != null)
      {
        cp5.get(Textfield.class, prefix+"Inches").setText("").setVisible(false);
        cp5.get(Textfield.class, prefix+"Feet").setText("").setVisible(false);
      }
    }
  }
  
  private void drawRelationship(float disp_x, float disp_y)
  {
    String relationshipDescription = "Relationship Type: ";
    
    if(relationship != null)
    {
      relationshipDescription = relationshipDescription + rtToString.get(relationship);
    }
    
    text(relationshipDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(relationshipDescription) + 5;
    disp_y = disp_y - 10;
    
    if(editing)
    {
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
        cp5.get(ScrollableList.class, prefix+"Relationship").setPosition(disp_x, disp_y).setVisible(true);
      }
    }
    else
    {
      if(cp5.get(ScrollableList.class, prefix+"Relationship") != null)
      {
        cp5.get(ScrollableList.class, prefix+"Relationship").setOpen(false).setVisible(false);
      } 
    }
  }
  
  private void drawBodyType(float disp_x, float disp_y)
  {
    String bodyDescription = "Body Type: ";
    
    if(bodytype != null)
    {
      bodyDescription = bodyDescription + btToString.get(bodytype);
    }
    
    text(bodyDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(bodyDescription) + 5;
    disp_y = disp_y - 10;
    
    if(editing)
    {
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
        cp5.get(ScrollableList.class, prefix+"Body").setPosition(disp_x, disp_y).setVisible(true);
      }
    }
    else
    {
      if(cp5.get(ScrollableList.class, prefix+"Body") != null)
      {
        cp5.get(ScrollableList.class, prefix+"Body").setOpen(false).setVisible(false);
      }
    }
  }
  
  private void drawReligion(float disp_x, float disp_y)
  {
    String religionDescription = "Religion: ";
    
    if(religion != null)
    {
      religionDescription = religionDescription + religionToString.get(religion);
    }
    
    text(religionDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(religionDescription) + 5;
    disp_y = disp_y - 10;
    
    if(editing)
    {
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
        cp5.get(ScrollableList.class, prefix+"Religion").setPosition(disp_x, disp_y).setVisible(true);
      }
    }
    else
    {
      if(cp5.get(ScrollableList.class, prefix+"Religion") != null)
      {
        cp5.get(ScrollableList.class, prefix+"Religion").setOpen(false).setVisible(false);
      }
    }
  }
  
  private void drawSign(float disp_x, float disp_y)
  {
    String signDescription = "Sign: ";
    
    if(sign != null)
    {
      signDescription = signDescription + signToString.get(sign);
    }
    
    text(signDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(signDescription) + 5;
    disp_y = disp_y - 10;
    
    if(editing)
    {
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
        cp5.get(ScrollableList.class, prefix+"Sign").setPosition(disp_x, disp_y).setVisible(true);
      }
    }
    else
    {
      if(cp5.get(ScrollableList.class, prefix+"Sign") != null)
      {
        cp5.get(ScrollableList.class, prefix+"Sign").setOpen(false).setVisible(false);
      }
    }
  }
  
  private void drawEducation(float disp_x, float disp_y)
  {
    String educationDescription = "Education: ";
    
    if(education != null)
    {
      educationDescription = educationDescription + educationToString.get(education);
    }
    
    text(educationDescription, disp_x, disp_y);
    
    disp_x = disp_x + (int) textWidth(educationDescription) + 5;
    disp_y = disp_y - 10;
    
    if(editing)
    {
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
        cp5.get(ScrollableList.class, prefix+"Education").setPosition(disp_x, disp_y).setVisible(true);
      }
    }
    else
    {
      if(cp5.get(ScrollableList.class, prefix+"Education") != null)
      {
        cp5.get(ScrollableList.class, prefix+"Education").setOpen(false).setVisible(false);
      }
    }
  }
  
  public Set<Gender> getGenders()
  {
    return gender;
  }
  
  public boolean addGender (Gender newGender)
  {
    if (!gender.contains(newGender) && gender.size() <= 4)
    {
      gender.add(newGender);
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public boolean removeGender (Gender oldGender)
  {
    if (gender.contains(oldGender))
    {
      gender.remove(oldGender);
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public int getAge()
  {
    return age;
  }
  
  public void setAge(int newAge)
  {
    age = newAge;
  }
  
  public Set<Orientation> getOrientations()
  {
    return orientation;
  }
  
  public boolean addOrientation (Orientation newOrientation)
  {
    if (!orientation.contains(newOrientation) && orientation.size() <= 4)
    {
      orientation.add(newOrientation);
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public boolean removeOrientation (Orientation oldOrientation)
  {
    if (orientation.contains(oldOrientation))
    {
      orientation.remove(oldOrientation);
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public Set<Ethnicity> getEthnicities()
  {
    return ethnicity;
  }
  
  public boolean addEthnicity (Ethnicity newEthnicity)
  {
    if (!ethnicity.contains(newEthnicity))
    {
      ethnicity.add(newEthnicity);
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public boolean removeEthnicity (Ethnicity oldEthnicity)
  {
    if (ethnicity.contains(oldEthnicity))
    {
      ethnicity.remove(oldEthnicity);
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public int getHeight()
  {
    return human_height;
  }
  
  public void setHeight(int inches)
  {
    human_height = inches;
  }
  
  public RelationshipType getRelationship()
  {
    return relationship;
  }
  
  public void setRelationship (RelationshipType relations)
  {
    relationship = relations;
  }
  
  public BodyType getBodyType()
  {
    return bodytype;
  }
  
  public void setBodyType(BodyType type)
  {
    bodytype = type;
  }
  
  public Smoking getSmoking()
  {
    return smoking;
  }
  
  public void setSmoking (Smoking smoke)
  {
    smoking = smoke;
  }
  
  public Drugs getDrugs()
  {
    return drugs;
  }
  
  public void setDrugs (Drugs drug)
  {
    drugs = drug;
  }
  
  public Religion getReligion()
  {
    return religion;
  }
  
  public void setReligion (Religion pray)
  {
    religion = pray;
  }
  
  public Sign getSign()
  {
    return sign;
  }
  
  public void setSign (Sign astro)
  {
    sign = astro;
  }
  
  public Education getEducation()
  {
    return education;
  }
  
  public void setEducation(Education educate)
  {
    education = educate;
  }
}

public void demoGender(int n)
{ //<>//
  Gender selectedGender = stringToGender.get(
                          cp5.get(ScrollableList.class, "demoGender")
                             .getItem(n).get("name"));
  
  Set<Gender> genders = demo.getGenders();
  
  if(genders.contains(selectedGender))
  {
    if(demo.removeGender(selectedGender))
    {
      cp5.get(ScrollableList.class, "demoGender").getItem(n).put("color",oldColor);
      cp5.get(ScrollableList.class, "demoGender").close();
    }
    
  }
  else
  {
    if(demo.addGender(selectedGender))
    {
      cp5.get(ScrollableList.class, "demoGender").getItem(n).put("color",selected);
      cp5.get(ScrollableList.class, "demoGender").close();
    }
  }
  
  
  
}

public void demoOrientation(int n)
{
  Orientation selectedOrientation = stringToOrientation.get(
                          cp5.get(ScrollableList.class, "demoOrientation")
                             .getItem(n).get("name"));
  
  Set<Orientation> orientations = demo.getOrientations();
  
  if(orientations.contains(selectedOrientation))
  {
    if(demo.removeOrientation(selectedOrientation))
    {
      cp5.get(ScrollableList.class, "demoOrientation").getItem(n).put("color",oldColor);
      cp5.get(ScrollableList.class, "demoOrientation").close();
    }
  }
  else
  {
    if(demo.addOrientation(selectedOrientation))
    {
      cp5.get(ScrollableList.class, "demoOrientation").getItem(n).put("color",selected);
      cp5.get(ScrollableList.class, "demoOrientation").close();
    }
  }
  
}

public void demoEthnicity(int n)
{
  Ethnicity selectedEthnicity = stringToEthnicity.get(
                          cp5.get(ScrollableList.class, "demoEthnicity")
                             .getItem(n).get("name"));
  
  Set<Ethnicity> ethnicities = demo.getEthnicities();
  
  if(ethnicities.contains(selectedEthnicity))
  {
    if(demo.removeEthnicity(selectedEthnicity))
    {
      cp5.get(ScrollableList.class, "demoEthnicity").getItem(n).put("color",oldColor);
      cp5.get(ScrollableList.class, "demoEthnicity").close();
    }
  }
  else
  {
    if(demo.addEthnicity(selectedEthnicity))
    {
      cp5.get(ScrollableList.class, "demoEthnicity").getItem(n).put("color",selected);
      cp5.get(ScrollableList.class, "demoEthnicity").close();
    }
  }
  
}

public void demoRelationship(int n)
{
  RelationshipType selectedRelationship = stringToRT.get(
                          cp5.get(ScrollableList.class, "demoRelationship")
                             .getItem(n).get("name"));
  
  demo.setRelationship(selectedRelationship); 
  cp5.get(ScrollableList.class, "demoRelationship").close();
}

public void demoBody(int n)
{
  BodyType selectedBody = stringToBT.get(
                          cp5.get(ScrollableList.class, "demoBody")
                             .getItem(n).get("name"));
  
  demo.setBodyType(selectedBody); 
  cp5.get(ScrollableList.class, "demoBody").close();
}

public void demoReligion(int n)
{
  Religion selectedReligion = stringToReligion.get(
                          cp5.get(ScrollableList.class, "demoReligion")
                             .getItem(n).get("name"));
  
  demo.setReligion(selectedReligion); 
  cp5.get(ScrollableList.class, "demoReligion").close();
}

public void demoSign(int n)
{
  Sign selectedSign = stringToSign.get(
                          cp5.get(ScrollableList.class, "demoSign")
                             .getItem(n).get("name"));
  
  demo.setSign(selectedSign);
  cp5.get(ScrollableList.class, "demoSign").close();
}

public void demoEducation(int n)
{
  Education selectedEducation = stringToEducation.get(
                          cp5.get(ScrollableList.class, "demoEducation")
                             .getItem(n).get("name"));
  
  demo.setEducation(selectedEducation);
  cp5.get(ScrollableList.class, "demoEducation").close();
}

public void demoAge(String theText)
{
  int age = -1;
  try{
    age = Integer.parseInt(theText);
  }
  catch(Exception e)
  {
    //Do nothing
  }
  
  demo.setAge(age);
}

public void demoFeet(String theText)
{
  int temp_height = demo.getHeight();
  int feet = 0;
  try{
    feet = Integer.parseInt(theText);
    int temp_inches = temp_height%12;
    temp_height = feet*12+temp_inches;
  }
  catch(Exception e)
  {
    //Do nothing
  }
  
  demo.setHeight(temp_height);
}

public void demoInches(String theText)
{
  int temp_height = demo.getHeight();
  int inches = 0;
  try{
    inches = Integer.parseInt(theText);
    int old_inches = temp_height%12;
    int temp_feet = temp_height/12;
    if(inches >= 0 && inches < 12)
    {
      temp_height = temp_feet*12+inches;
    }
    else
    {
      temp_height = temp_feet*12+old_inches;
    }
  }
  catch(Exception e)
  {
    //Do nothing
  }
  
  demo.setHeight(temp_height);
}
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
  private float pulseAmt = .02f;
  private int qIndex;
  private int eIndex;
  private boolean okToToggle;
  private int currToggle;
  private int toggleInterval = 10;
  private int demoPage;
  private int tickFrames = 2;
  private int currTick;
  private String[] feedbacks = {"WOW!","GOOD","GREAT","BEST!","LOVE"};
  
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
  
  public String getUsername()
  {
    return username;
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
    int max = 0;
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
      int pulseColor = lerpColor(okcPink2, okcPink1, pulsePoint);
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
      int oldMatch = targetMatch;
      //This will be more complicated ideally
      targetMatch = getMatch();
      if(visible && targetMatch > oldMatch)
      {
        Random random = new Random();
        String feedback = feedbacks[random.nextInt(feedbacks.length)];
        setFeedback(feedback);
      }
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
      
      String full = "To: " + to + "\nFrom: " + from + "\n" + message;
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
          okToToggle = false;
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
    
    int okcButton = color(50,96,199);
    int okcButton_mouseover = color(69,110,203);
    noStroke();
      
    float rect_x = x;
    float rect_y = y;
    
    boolean editing = questions[qIndex].getEditing();
    for(Question q: questions)
    {
     q.setEditing(editing);
    }

      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
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
    
    text((qIndex+1) + "/" + (questions.length),x+150,y+10);
    
     rect_x = x + 300 - button_w;
     rect_y = y;
    
      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
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
    if(subheading.length() != 0 && subheading.charAt(subheading.length()-1) == ',')
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
    
    int okcButton = color(50,96,199);
    int okcButton_mouseover = color(69,110,203);
    noStroke();
      
    float rect_x = question_x;
    float rect_y = question_y;
    
   boolean editing = questions[qIndex].getEditing();
    for(Question q: questions)
    {
     q.setEditing(editing);
    }

      if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
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
    
    text((qIndex+1) + "/" + (questions.length),question_x+150,question_y+10);
    
     rect_x = question_x + 300 - button_w;
     rect_y = question_y;
    
    if(overRect(rect_x,rect_y,button_w,button_h))
      {
        fill(okcButton_mouseover);
        if(mousePressed && mouseButton == LEFT && okToToggle)
        {
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
public boolean overCircle(float x, float y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if (sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
    return true;
  } else {
    return false;
  }
}

public boolean overRect(float x, float y, float width, float height)  {
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
      
      int okcButton = color(50,96,199);
      int okcButton_mouseover = color(69,110,203);
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
  public void settings() {  size(1250,700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "okc" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
