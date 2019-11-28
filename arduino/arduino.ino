#include <math.h>
#include <NewPing.h>
#include <BluetoothSerial.h>

/* --- GLOBAL VARIABLES --- */

const int INCR_BUTTON = 23;
const int DECR_BUTTON = 22;

const uint8_t SENSE_ECHOPIN = 27;
const uint8_t SENSE_TRIGPIN = 26;

const uint8_t R_PIN = 18;
const uint8_t G_PIN = 19;
const uint8_t B_PIN = 21;
const uint8_t R_LED = 1;
const uint8_t G_LED = 2;
const uint8_t B_LED = 3;

const char MESSAGE_START_CHAR = '<';
const char MESSAGE_ENDED_CHAR = '>';

BluetoothSerial esp32BT;

char messageReceived[100];

/* --- COMPONENTS VARIABLES --- */

const int LONG_PRESS = 3000;
const int MAX_DISTANCE = 50;

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

NewPing sonar(SENSE_TRIGPIN, SENSE_ECHOPIN);
double distance;

int timeLedChange = 0;
bool flagLEDBlink_awtConn = false;

/* --- FUNCTIONALITY VARIABLES --- */

int cigarettesSmoked = 0;
int limit = 10;

/* --- SETUP & SETUP_AUX METHODS --- */

void setup() {
  Serial.begin(115200);

  esp32BT.begin("TB Packet");
  flagLEDBlink_awtConn = true;
  esp32BT.register_callback(callback);

  pinMode(INCR_BUTTON, INPUT);
  pinMode(DECR_BUTTON, INPUT);

  pinMode(SENSE_ECHOPIN, INPUT);
  pinMode(SENSE_TRIGPIN, OUTPUT);

  ledcSetup(R_LED, 5000, 8);
  ledcSetup(G_LED, 5000, 8);
  ledcSetup(B_LED, 5000, 8);
  ledcAttachPin(R_PIN, R_LED);
  ledcAttachPin(G_PIN, G_LED);
  ledcAttachPin(B_PIN, B_LED);

  setColorBasedOnSmoked();
}    

/* --- LOPP & LOOP_AUX METHODS --- */

void loop() {

  distance = sonar.ping_cm();
  Serial.print(distance); Serial.println(" cm");

  currIncrButtState = digitalRead(INCR_BUTTON);
  if (currIncrButtState != lastIncrButtState) {
    updateIncrButtonState();
    if (currIncrButtState == HIGH) {
      incrementCigsSmoked();
    }
  }
  else
    updateIncrButtonCounter();

  currDecrButtState = digitalRead(DECR_BUTTON);
  if (currDecrButtState != lastDecrButtState) {
    updateDecrButtonState();
    if (currDecrButtState == HIGH) {
      decrementCigsSmoked();
    }
  }
  else
    updateDecrButtonCounter();

  int ledChangeElapsedTime = millis() - timeLedChange;
  if(flagLEDBlink_awtConn && ledChangeElapsedTime >= 5000) {
    ledBlink(2, 0.15, 0, 0, 255);
  }

  btReceiveMessage();
  setColorBasedOnSmoked();
  lastIncrButtState = currIncrButtState;
  lastDecrButtState = currDecrButtState;
}

void updateIncrButtonState() {
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

void updateIncrButtonCounter() {
  if(currIncrButtState == HIGH)
    incrTimeHold = millis() - incrTimeStartPressed;                 // Registers for how long the button has been pressed currently
  else
    incrTimeReleased = millis() - incrTimeEndPressed;               // Registers for how long the button has been released currently
}

void updateDecrButtonState() {
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

void updateDecrButtonCounter() {
  if(currDecrButtState == HIGH)
    decrTimeHold = millis() - decrTimeStartPressed;                 // Registers for how long the button has been pressed currently
  else
    decrTimeReleased = millis() - decrTimeEndPressed;               // Registers for how long the button has been released currently
}

/* --- AUXILIARY METHODS --- */

void setColor (uint32_t red, uint32_t green, uint32_t blue) {
  ledcWrite(R_LED, red);   
  ledcWrite(G_LED, green); 
  ledcWrite(B_LED, blue); 
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
  for(int i = 0; i <= nTimes - 1; i++) {
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
}

void decrementCigsSmoked() {
  if (cigarettesSmoked > 0) {
    cigarettesSmoked--;
    Serial.print("Number of Cigarettes Smoked: "); Serial.println(cigarettesSmoked);
    esp32BT.print("SMOKED "); esp32BT.println(cigarettesSmoked);
    ledBlink(2, 0.5, 0, 255, 0);
  }
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
}

void btReceiveMessage() {
  byte i;
  while (esp32BT.available()) {
    char c = esp32BT.read();
    if (c == MESSAGE_START_CHAR) {
      i = 0;
      messageReceived[i] = '\0';
    }
    else if (c == MESSAGE_ENDED_CHAR) {
      Serial.print("Received: "); Serial.println(messageReceived);
      break;
    }
    else {
      if (i < 99) {
        messageReceived[i] = c;
        i++;
        messageReceived[i] = '\0';
      }
    }
  }
  messageReceived[0] = '\0';
}