#include <math.h>
#include <NewPing.h>
#include <BluetoothSerial.h>
#include <Adafruit_NeoPixel.h>

/* --- GLOBAL VARIABLES --- */

const uint8_t MYCIGS_BUTTONPIN = 23;
const uint8_t BDCIGS_BUTTONPIN = 22;

const uint8_t SENSE_ECHOPIN = 27;
const uint8_t SENSE_TRIGPIN = 26;

const uint8_t MYNEOPIXEL_PIN = 5;
const uint8_t BDNEOPIXEL_PIN = 14;

const uint8_t VIBRMOD_PIN = 12;

const uint8_t R_PIN = 18;
const uint8_t G_PIN = 19;
const uint8_t B_PIN = 21;
const uint8_t R_LED = 1;
const uint8_t G_LED = 2;
const uint8_t B_LED = 3;

const char MESSAGE_START_CHAR = '<';
const char MESSAGE_ENDED_CHAR = '>';

BluetoothSerial esp32BT;

char receivedMessageBuffer[100];
String receivedMessage;

/* --- COMPONENTS VARIABLES --- */

const int LONG_PRESS = 1000;
const int MAX_DISTANCE = 50;
const int N_PIXELS = 8;

int currMyButtState = 0;                  // Button's current state
int lastMyButtState = 0;                  // Button's state in the last loop
int myButtTimeStartPressed = 0;           // Time the button was pressed
int myButtTimeEndPressed = 0;             // Time the button was released
int myButtTimeHold = 0;                   // How long the button was held
int myButtTimeReleased = 0;               // How long the button released

int currBdButtState = 0;                  // Button's current state
int lastBdButtState = 0;                  // Button's state in the last loop
int bdButtTimeStartPressed = 0;           // Time the button was pressed
int bdButtTimeEndPressed = 0;             // Time the button was released
int bdButtTimeHold = 0;                   // How long the button was held
int bdButtTimeReleased = 0;               // How long the button released

NewPing sonar(SENSE_TRIGPIN, SENSE_ECHOPIN);
double distance;

Adafruit_NeoPixel mypixels(N_PIXELS, MYNEOPIXEL_PIN, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel bdpixels(N_PIXELS, BDNEOPIXEL_PIN, NEO_GRB + NEO_KHZ800);

int timeLedChange = 0;
bool flagLEDBlink_awtConn = false;

int vibDuration = 0;
bool flagConstVibrate = false;

int timeVibChange = 0;
int vibCounter = 0;
int vibNTimes = 0;
bool flagInterVibrate = false;

/* --- FUNCTIONALITY VARIABLES --- */

int cigarettesSmoked = 0;
int limit = 10;
int buddieCigarettesSmoked = 0;
int buddieLimit = 15;

/* --- SETUP & SETUP_AUX METHODS --- */

void setup() {
  Serial.begin(115200);

  esp32BT.begin("TB Packet");
  flagLEDBlink_awtConn = true;
  esp32BT.register_callback(callback);

  pinMode(MYCIGS_BUTTONPIN, INPUT);
  pinMode(BDCIGS_BUTTONPIN, INPUT);

  pinMode(SENSE_ECHOPIN, INPUT);
  pinMode(SENSE_TRIGPIN, OUTPUT);

  ledcSetup(R_LED, 5000, 8);
  ledcSetup(G_LED, 5000, 8);
  ledcSetup(B_LED, 5000, 8);
  ledcAttachPin(R_PIN, R_LED);
  ledcAttachPin(G_PIN, G_LED);
  ledcAttachPin(B_PIN, B_LED);

  mypixels.begin(); setColorBOS_Aux(-1);
  bdpixels.begin(); setBuddieColorBOS_Aux(-1);

  setColorBasedOnSmoked();
  setBuddieColorBasedOnSmoked();
}    

/* --- LOPP & LOOP_AUX METHODS --- */

void loop() {

  distance = sonar.ping_cm();
  if (distance != 0 && distance <= 10) {
    incrementCigsSmoked();
  }

  currMyButtState = digitalRead(MYCIGS_BUTTONPIN);
  if (currMyButtState != lastMyButtState) {
    updateIncrButtonState();
    if (currMyButtState == LOW && myButtTimeHold <= LONG_PRESS) {
      incrementCigsSmoked();
    }
    if (currMyButtState == LOW && myButtTimeHold >= LONG_PRESS) {
      decrementCigsSmoked();
    }
  }
  else
    updateIncrButtonCounter();

  currBdButtState = digitalRead(BDCIGS_BUTTONPIN);
  if (currBdButtState != lastBdButtState) {
    updateDecrButtonState();
    if (currBdButtState == LOW && bdButtTimeHold <= LONG_PRESS) {
      incrementBuddiesCigsSmoked();
    }
    if (currBdButtState == LOW && bdButtTimeHold >= LONG_PRESS) {
      decrementBuddiesCigsSmoked();
    }
  }
  else
    updateDecrButtonCounter();

  int ledChangeElapsedTime = millis() - timeLedChange;
  if (flagLEDBlink_awtConn && ledChangeElapsedTime >= 5000) {
    ledBlink(2, 0.15, 0, 0, 255);
  }

  int vibChangeElapsedTime = millis() - timeVibChange;
  if (flagConstVibrate && vibChangeElapsedTime >= vibDuration) {
    vibrationOff();
    flagConstVibrate = false;
  }

  if (flagInterVibrate) {
    int vibState = digitalRead(VIBRMOD_PIN);
    if (vibChangeElapsedTime >= vibDuration && vibState == HIGH){
      vibrationOff();
    }
    if (vibChangeElapsedTime >= vibDuration && vibState == LOW) {
      vibrationOn();
      vibCounter++;
      if (vibCounter == vibNTimes)
        flagInterVibrate = false;
    }
  }

  btReceiveMessage();
  if (receivedMessage.substring(0, 6).compareTo("MLIMIT") == 0){
    setMyLimit(receivedMessage.substring(7).toInt());
  }
  if (receivedMessage.substring(0, 6).compareTo("BUDDIE") == 0){
    setBuddiesCigsSmoked(receivedMessage.substring(7).toInt());
  }
  if (receivedMessage.substring(0, 6).compareTo("BLIMIT") == 0){
    setBuddiesLimit(receivedMessage.substring(7).toInt());
  }
  receivedMessage = "";

  lastMyButtState = currMyButtState;
  lastBdButtState = currBdButtState;
}

void updateIncrButtonState() {
  /*  HIGH == Button Pressed 
      LOW == Button Released */
  if(currMyButtState == HIGH) {
    myButtTimeStartPressed = millis();                                // Registers the time the button was pressed
    myButtTimeReleased = myButtTimeStartPressed - myButtTimeEndPressed;   // Registers how long the button was released
  }
  else {
    myButtTimeEndPressed = millis();                                  // Registers the time the button was released
    myButtTimeHold = myButtTimeEndPressed - myButtTimeStartPressed;       // Registers how long the button was held down
  }
}

void updateIncrButtonCounter() {
  if(currMyButtState == HIGH)
    myButtTimeHold = millis() - myButtTimeStartPressed;                 // Registers for how long the button has been pressed currently
  else
    myButtTimeReleased = millis() - myButtTimeEndPressed;               // Registers for how long the button has been released currently
}

void updateDecrButtonState() {
  /*  HIGH == Button Pressed 
      LOW == Button Released */
  if(currBdButtState == HIGH) {
    bdButtTimeStartPressed = millis();                                // Registers the time the button was pressed
    bdButtTimeReleased = bdButtTimeStartPressed - bdButtTimeEndPressed;   // Registers how long the button was released
  }
  else {
    bdButtTimeEndPressed = millis();                                  // Registers the time the button was released
    bdButtTimeHold = bdButtTimeEndPressed - bdButtTimeStartPressed;       // Registers how long the button was held down
  }
}

void updateDecrButtonCounter() {
  if(currBdButtState == HIGH)
    bdButtTimeHold = millis() - bdButtTimeStartPressed;                 // Registers for how long the button has been pressed currently
  else
    bdButtTimeReleased = millis() - bdButtTimeEndPressed;               // Registers for how long the button has been released currently
}

/* --- AUXILIARY METHODS --- */

void setColor (uint32_t red, uint32_t green, uint32_t blue) {
  ledcWrite(R_LED, red);   
  ledcWrite(G_LED, green); 
  ledcWrite(B_LED, blue); 
}

void vibrationOn() {
  digitalWrite(VIBRMOD_PIN, HIGH);
  timeVibChange = millis();
}

void vibrationOff() {
  digitalWrite(VIBRMOD_PIN, LOW);
  timeVibChange = millis();
}

void constVibrate(float duration){
  vibDuration = duration * 1000;
  flagConstVibrate = true;
  vibrationOn();
}

void interVibrate(float nTimes, float nTimeAppart) {
  vibNTimes = nTimes;
  vibDuration = nTimeAppart;
  flagInterVibrate = true;
  vibrationOn();
}

void ledBlink(unsigned char red, unsigned char green, unsigned char blue) {
  if (ledcRead(R_LED) == 0 && ledcRead(G_LED) == 0 && ledcRead(B_LED) == 0)
    setColor(red, green, blue);
  else
    setColor(0, 0, 0);
  timeLedChange = millis();
}

void ledBlink(int nTimes, float nTimeAppart, unsigned char red, unsigned char green, unsigned char blue) {
  int prevColor[] = {ledcRead(R_LED), ledcRead(G_LED), ledcRead(B_LED)};
  int mills = nTimeAppart * 1000;
  for (int i = 0; i <= nTimes - 1; i++) {
    setColor(red, green, blue); delay(mills); setColor(0, 0, 0); delay(mills);
  }
  setColor(prevColor[0], prevColor[1], prevColor[2]);
  timeLedChange = millis();
}

void callback(esp_spp_cb_event_t event, esp_spp_cb_param_t *param){
  if(event == ESP_SPP_SRV_OPEN_EVT){
    Serial.println("Client Connected");
    esp32BT.print("SMOKED "); esp32BT.println(cigarettesSmoked);
    flagLEDBlink_awtConn = false;
  }

  if(event == ESP_SPP_CLOSE_EVT){
    Serial.println("Client disconnected");
    flagLEDBlink_awtConn = true;
    timeLedChange = millis();
  }
}

/* --- FUNCTIONALITY METHODS --- */

void incrementCigsSmoked() {
  cigarettesSmoked++;
  Serial.print("Number of Cigarettes Smoked: "); Serial.println(cigarettesSmoked);
  esp32BT.print("SMOKED "); esp32BT.println(cigarettesSmoked);
  if (cigarettesSmoked <= limit)
    ledBlink(2, 0.5, 255, 0, 0);
  else
    ledBlink(5, 0.25, 255, 0, 0);
  setColorBasedOnSmoked();
}

void decrementCigsSmoked() {
  if (cigarettesSmoked > 0) {
    cigarettesSmoked--;
    Serial.print("Number of Cigarettes Smoked: "); Serial.println(cigarettesSmoked);
    esp32BT.print("SMOKED "); esp32BT.println(cigarettesSmoked);
    ledBlink(2, 0.5, 0, 255, 0);
    setColorBasedOnSmoked();
  }
}

void setMyLimit(int nCigarettes) {
  limit = nCigarettes;
  Serial.print("Limit changed to: "); Serial.println(limit);
  setColorBasedOnSmoked();
}

void setBuddiesCigsSmoked(int nCigarettes) {
  buddieCigarettesSmoked = nCigarettes;
  Serial.print("Buddie's Number of Cigarettes Smoked: "); Serial.println(buddieCigarettesSmoked);
  //esp32BT.print("BUDDIE_SMOKED "); esp32BT.println(buddieCigarettesSmoked);
  setBuddieColorBasedOnSmoked();
}

void incrementBuddiesCigsSmoked() {
  buddieCigarettesSmoked++;
  Serial.print("Buddie's Number of Cigarettes Smoked: "); Serial.println(buddieCigarettesSmoked);
  //esp32BT.print("BUDDIE_SMOKED "); esp32BT.println(buddieCigarettesSmoked);
  setBuddieColorBasedOnSmoked();
}

void decrementBuddiesCigsSmoked() {
  if (buddieCigarettesSmoked > 0) {
    buddieCigarettesSmoked--;
    Serial.print("Buddie's Number of Cigarettes Smoked: "); Serial.println(buddieCigarettesSmoked);
    //esp32BT.print("BUDDIE_SMOKED "); esp32BT.println(buddieCigarettesSmoked);
    setBuddieColorBasedOnSmoked();
  }
}

void setBuddiesLimit(int nCigarettes) {
  buddieLimit = nCigarettes;
  Serial.print("Buddie's Limit changed to: "); Serial.println(buddieLimit);
  //esp32BT.print("BUDDIE_SMOKED "); esp32BT.println(buddieCigarettesSmoked);
  setBuddieColorBasedOnSmoked();
}

void setColorBasedOnSmoked() {
  float percentage = (float) cigarettesSmoked / (float) limit;
  int red = analogRead(R_PIN);
  int green = analogRead(G_PIN);
  if (percentage <= 0.5) {
    red = (int) round(255 * (percentage * 2));
    if (red < 0) red = 0;
    if (red > 255) red = 255;
    green = 255;
  }
  if (percentage >= 0.5) {
    green = (int) round(255 * ((1 - percentage) * 2));
    red = 255;
    if (green < 0) green = 0;
    if (green > 255) green = 255;
  }
  setColor(red, green, 0);

  uint32_t color = mypixels.Color(red , green, 0);
  
  if (percentage * 100 > 0) {
   mypixels.setPixelColor(0, color);
   setColorBOS_Aux(0);
  }
  if (percentage * 100 > 12.5) {
    mypixels.setPixelColor(1, color);
    setColorBOS_Aux(1);
  }
  if (percentage * 100 > 25) {
    mypixels.setPixelColor(2, color);
    setColorBOS_Aux(2);
  }
  if (percentage * 100 > 37.5) {
    mypixels.setPixelColor(3, color);
    setColorBOS_Aux(3);
  }
  if (percentage * 100 > 50) {
    mypixels.setPixelColor(4, color);
    setColorBOS_Aux(4);
  }
  if (percentage * 100 > 62.5) {
    mypixels.setPixelColor(5, color);
    setColorBOS_Aux(5);
  }
  if (percentage * 100 > 75) {
    mypixels.setPixelColor(6, color);
    setColorBOS_Aux(6);
  }
  if (percentage * 100 > 87.5) {
    mypixels.setPixelColor(7, color);
    setColorBOS_Aux(7);
  }
  mypixels.show();
}

void setColorBOS_Aux(int pixel) {
  uint32_t black = mypixels.Color(0, 0, 0);
  for (int i = pixel + 1; i <= 8; i++)
    mypixels.setPixelColor(i, black);
}

void setBuddieColorBasedOnSmoked() {
  float percentage = (float) buddieCigarettesSmoked / (float) buddieLimit;
  int red = analogRead(R_PIN);
  int green = analogRead(G_PIN);
  if (percentage <= 0.5) {
    red = (int) round(255 * (percentage * 2));
    if (red < 0) red = 0;
    if (red > 255) red = 255;
    green = 255;
  }
  if (percentage >= 0.5) {
    green = (int) round(255 * ((1 - percentage) * 2));
    red = 255;
    if (green < 0) green = 0;
    if (green > 255) green = 255;
  }

  uint32_t color = bdpixels.Color(red , green, 0);
  
  if (percentage * 100 > 0.1) {
   bdpixels.setPixelColor(0, color);
   setBuddieColorBOS_Aux(0);
  }
  if (percentage * 100 > 12.5) {
    bdpixels.setPixelColor(1, color);
    setBuddieColorBOS_Aux(1);
  }
  if (percentage * 100 > 25) {
    bdpixels.setPixelColor(2, color);
    setBuddieColorBOS_Aux(2);
  }
  if (percentage * 100 > 37.5) {
    bdpixels.setPixelColor(3, color);
    setBuddieColorBOS_Aux(3);
  }
  if (percentage * 100 > 50) {
    bdpixels.setPixelColor(4, color);
    setBuddieColorBOS_Aux(4);
  }
  if (percentage * 100 > 62.5) {
    bdpixels.setPixelColor(5, color);
    setBuddieColorBOS_Aux(5);
  }
  if (percentage * 100 > 75) {
    bdpixels.setPixelColor(6, color);
    setBuddieColorBOS_Aux(6);
  }
  if (percentage * 100 > 87.5) {
    bdpixels.setPixelColor(7, color);
    setBuddieColorBOS_Aux(7);
  }
  bdpixels.show();
}

void setBuddieColorBOS_Aux(int pixel) {
  uint32_t black = bdpixels.Color(0, 0, 0);
  for (int i = pixel + 1; i <= 8; i++)
    bdpixels.setPixelColor(i, black);
}

void btReceiveMessage() {
  byte i;
  while (esp32BT.available()) {
    char c = esp32BT.read();
    if (c == MESSAGE_START_CHAR) {
      i = 0;
      receivedMessageBuffer[i] = '\0';
    }
    else if (c == MESSAGE_ENDED_CHAR) {
      receivedMessage = String(receivedMessageBuffer);
      Serial.print("Received: "); Serial.println(receivedMessage);
      break;
    }
    else {
      if (i < 99) {
        receivedMessageBuffer[i] = c;
        i++;
        receivedMessageBuffer[i] = '\0';
      }
    }
  }
  receivedMessageBuffer[0] = '\0';
}