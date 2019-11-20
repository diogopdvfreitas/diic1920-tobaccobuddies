const int INCR_BUTTON = 2;
const int DECR_BUTTON = 3;
const int R_PIN = 9;
const int G_PIN = 10;
const int B_PIN = 11;

const int LONG_PRESS = 3000;

int currIncrButtState = 0;                // Button's current state
int lastIncrButtState = 1;                // Button's state in the last loop
int incrTimeStartPressed = 0;             // Time the button was pressed
int incrTimeEndPressed = 0;               // Time the button was released
int incrTimeHold = 0;                     // How long the button was held
int incrTimeReleased = 0;                 // How long the button released

int currDecrButtState = 0;                // Button's current state
int lastDecrButtState = 1;                // Button's state in the last loop
int decrTimeStartPressed = 0;             // Time the button was pressed
int decrTimeEndPressed = 0;               // Time the button was released
int decrTimeHold = 0;                     // How long the button was held
int decrTimeReleased = 0;                 // How long the button released

int cigarettesSmoked = 0;

void setup() {
  Serial.begin(9600);

  pinMode(INCR_BUTTON, INPUT);
  pinMode(DECR_BUTTON, INPUT);
  pinMode(R_PIN, OUTPUT); 
  pinMode(G_PIN, OUTPUT);
  pinMode(B_PIN, OUTPUT);
}    

void setColor (unsigned char red, unsigned char green, unsigned char blue) {     
  analogWrite(R_PIN, red);   
  analogWrite(G_PIN, green); 
  analogWrite(B_PIN, blue); 
}

void loop() {    
  currIncrButtState = digitalRead(INCR_BUTTON);
  if (currIncrButtState != lastIncrButtState) {
    updateIncrButtonState();
    if (currIncrButtState == HIGH) {
      incrementCigsSmoked();
    }
  }
  else {
    updateIncrButtonCounter();
  }

  currDecrButtState = digitalRead(DECR_BUTTON);
  if (currDecrButtState != lastDecrButtState) {
    updateDecrButtonState();
    if (currDecrButtState == HIGH) {
      decrementCigsSmoked();
    }
  }
  else {
    updateDecrButtonCounter();
  }

  lastIncrButtState = currIncrButtState;
  lastDecrButtState = currDecrButtState;
}

void updateIncrButtonState(){
  /*  HIGH == Button Pressed 
      LOW == Button Released */
  if(currIncrButtState == HIGH) {
    incrTimeStartPressed = millis();                                // Registers the time the button was pressed
    incrTimeReleased = incrTimeStartPressed - incrTimeEndPressed;   // Registers how long the button was released
  }
  else {
    incrTimeEndPressed = millis();                                  // Registers the time the button was released
    incrTimeHold = incrTimeEndPressed - incrTimeStartPressed;       // Registers how long the button was held down
  }
}

void updateIncrButtonCounter(){
  if(currIncrButtState == HIGH)
    incrTimeHold = millis() - incrTimeStartPressed;                 // Registers for how long the button has been pressed currently
  else
    incrTimeReleased = millis() - incrTimeEndPressed;               // Registers for how long the button has been released currently
}

void updateDecrButtonState(){
  /*  HIGH == Button Pressed 
      LOW == Button Released */
  if(currDecrButtState == HIGH) {
    decrTimeStartPressed = millis();                                // Registers the time the button was pressed
    decrTimeReleased = decrTimeStartPressed - decrTimeEndPressed;   // Registers how long the button was released
  }
  else {
    decrTimeEndPressed = millis();                                  // Registers the time the button was released
    decrTimeHold = decrTimeEndPressed - decrTimeStartPressed;       // Registers how long the button was held down
  }
}

void updateDecrButtonCounter(){
  if(currDecrButtState == HIGH)
    decrTimeHold = millis() - decrTimeStartPressed;                 // Registers for how long the button has been pressed currently
  else
    decrTimeReleased = millis() - decrTimeEndPressed;               // Registers for how long the button has been released currently
}

void incrementCigsSmoked() {
  cigarettesSmoked++;
  Serial.print("Number of Cigarettes Smoked: "); Serial.println(cigarettesSmoked);
  setColor(0, 255, 0);
  delay(1000);
  setColor(0, 0, 0);
}

void decrementCigsSmoked() {
  cigarettesSmoked--;
  Serial.print("Number of Cigarettes Smoked: "); Serial.println(cigarettesSmoked);
  setColor(255, 0, 0);
  delay(1000);
  setColor(0, 0, 0);
}