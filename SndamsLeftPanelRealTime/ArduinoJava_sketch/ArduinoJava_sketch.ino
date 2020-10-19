/*
 * Arduino serial read with Java
 * how to serial read data from Arduino board 
 * with your PC using Java 
 * communication protocol based on Ardulink by Luciano Zu
 * http://www.ardulink.org/
 * author  Physics Light
 * date    December 2014
 * license creative commons 4.0 international (CC BY 4.0)
 * http://creativecommons.org/licenses/by/4.0/
 */

int maxNumber = 100;
int randNumber;

// Serial write data Ardulink communication protocol
void printData(int data){
 Serial.print("SERIAL");
 Serial.print(data);
 Serial.write(255);  
}

void setup(){
  // init serial port baud rate
  Serial.begin(115200);
  // generate different seed number if pin 0 is unconnected
  randomSeed(analogRead(0));
}

void loop(){
  // generate a random number between 0 and max
  randNumber = random(maxNumber);
  // serial write data for Java
  printData(randNumber);
  // take your time
  delay(250);  
}
