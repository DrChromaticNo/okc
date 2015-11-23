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
                    
class Demographics
{
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

  public HashMap<Gender,String> genderToString = new HashMap<Gender,String>();
  public HashMap<String,Gender> stringToGender = new HashMap<String,Gender>();
  
  public HashMap<Orientation,String> orientationToString = new HashMap<Orientation,String>();
  public HashMap<String,Orientation> stringToOrientation = new HashMap<String,Orientation>();
  
  public HashMap<Ethnicity,String> ethnicityToString = new HashMap<Ethnicity,String>();
  public HashMap<String,Ethnicity> stringToEthnicity = new HashMap<String,Ethnicity>();  
  
  public Demographics(String prefix)
  {
    gender = new HashSet(5);
    orientation = new HashSet(5);
    ethnicity = new HashSet(Ethnicity.values().length);
    this.prefix = prefix;
    
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

  }
  
  public void drawAt(int x, int y, PFont TEXT_FONT)
  {
    int d_width = MIN_WIDTH;
    int d_height = MIN_HEIGHT;
    
    rect(x, y, d_width, d_height);
    
    
    //Gender
    
    
    int disp_x = x+5;
    int disp_y = y+15;
    
    textFont(TEXT_FONT);
    fill(0);
    textAlign(LEFT);
    
    String genderDescription = "I am:";
    
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
      cp5.get(ScrollableList.class, prefix+"Gender").setPosition(disp_x, disp_y);
    }
    
    
    //Age
    
    
    disp_x = x+5; 
    disp_y = disp_y+35;
    
    String ageDescription = "Age:";
    
    if(age > 0)
    {
      ageDescription = ageDescription + " " + age;
    }
    
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
      cp5.get(Textfield.class, prefix+"Age").setPosition(disp_x, disp_y);
    }
    
    
    //Orientation
    
    
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
    
    disp_x = x+5; 
    disp_y = disp_y+35;
    
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
      cp5.get(ScrollableList.class, prefix+"Orientation").setPosition(disp_x, disp_y);
    }
    
    
    
    //Ethnicity
    
    
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
    
    disp_x = x+5; 
    disp_y = disp_y+35;
    
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
      cp5.get(ScrollableList.class, prefix+"Ethnicity").setPosition(disp_x, disp_y);
    }
    
    fill(255);
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
{ //<>//
  Gender selectedGender = demo.stringToGender.get(
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
   //<>//
}

void demoOrientation(int n)
{
  Orientation selectedOrientation = demo.stringToOrientation.get(
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
  Ethnicity selectedEthnicity = demo.stringToEthnicity.get(
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