Question testQ = new Question("This is a test?", new String[]{"Option 1", "Option 2"}); //<>// //<>// //<>// //<>// //<>//
Question test2Q = new Question("This is another test?", new String[]{"Option 3", "Option 4"});
PFont TEXT_FONT; 
PFont OPTIONS_FONT;


void setup()
{
  size(500,500);
  TEXT_FONT = createFont("Arial",16,true);
  OPTIONS_FONT = createFont("Arial",14,true);
}

void draw()
{
  testQ.drawAt(10, 10, TEXT_FONT, OPTIONS_FONT);
  test2Q.drawAt(100, 100, TEXT_FONT, OPTIONS_FONT); //<>//
}