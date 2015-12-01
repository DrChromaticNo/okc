import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import controlP5.*;

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
    
    color okcButton = color(50,96,199);
    color okcButton_mouseover = color(69,110,203);
    
    noStroke();
    
    float rect_x = x+d_width-button_w-15;
    float rect_y = y+d_height-button_h-15;
    
    if(overRect(rect_x,rect_y,button_w,button_h))
    {
      fill(okcButton_mouseover);
      if(mousePressed && mouseButton == LEFT && okToSwitch)
      {
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
    
    if(age > 0)
    {
      ageDescription = ageDescription + " " + age;
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

void demoGender(int n)
{
  Gender selectedGender = stringToGender.get(
                          cp5.get(ScrollableList.class, "demoGender")
                             .getItem(n).get("name"));
  
  Set<Gender> genders = demo.getGenders();
  
  if(genders.contains(selectedGender))
  {
    demo.removeGender(selectedGender);
  }
  else
  {
    demo.addGender(selectedGender);
  }
  
}

void demoOrientation(int n)
{
  Orientation selectedOrientation = stringToOrientation.get(
                          cp5.get(ScrollableList.class, "demoOrientation")
                             .getItem(n).get("name"));
  
  Set<Orientation> orientations = demo.getOrientations();
  
  if(orientations.contains(selectedOrientation))
  {
    demo.removeOrientation(selectedOrientation);
  }
  else
  {
    demo.addOrientation(selectedOrientation);
  }
  
}

void demoEthnicity(int n)
{
  Ethnicity selectedEthnicity = stringToEthnicity.get(
                          cp5.get(ScrollableList.class, "demoEthnicity")
                             .getItem(n).get("name"));
  
  Set<Ethnicity> ethnicities = demo.getEthnicities();
  
  if(ethnicities.contains(selectedEthnicity))
  {
    demo.removeEthnicity(selectedEthnicity);
  }
  else
  {
    demo.addEthnicity(selectedEthnicity);
  }
  
}

void demoRelationship(int n)
{
  RelationshipType selectedRelationship = stringToRT.get(
                          cp5.get(ScrollableList.class, "demoRelationship")
                             .getItem(n).get("name"));
  
  demo.setRelationship(selectedRelationship); 
}

void demoBody(int n)
{
  BodyType selectedBody = stringToBT.get(
                          cp5.get(ScrollableList.class, "demoBody")
                             .getItem(n).get("name"));
  
  demo.setBodyType(selectedBody); 
}

void demoReligion(int n)
{
  Religion selectedReligion = stringToReligion.get(
                          cp5.get(ScrollableList.class, "demoReligion")
                             .getItem(n).get("name"));
  
  demo.setReligion(selectedReligion); 
}

void demoSign(int n)
{
  Sign selectedSign = stringToSign.get(
                          cp5.get(ScrollableList.class, "demoSign")
                             .getItem(n).get("name"));
  
  demo.setSign(selectedSign); 
}

void demoEducation(int n)
{
  Education selectedEducation = stringToEducation.get(
                          cp5.get(ScrollableList.class, "demoEducation")
                             .getItem(n).get("name"));
  
  demo.setEducation(selectedEducation); 
}

void demoAge(String theText)
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

void demoFeet(String theText)
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

void demoInches(String theText)
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