#Arduino Serial Read Using Java#

![Arduino-Serial-Read-Java](https://dl.dropboxusercontent.com/u/51833595/Arduino_serial_read_Java_GUI.png)

How to communicate with an Arduino board using a Java application.

##Intro##
In this example I will show how to read data sending by an Arduino board using Java application.
The Arduino board elaborates a random number and print the data via the serial port. The Java application is designed to find the COM port where the Arduino is connected, establish the connection between Arduino and your PC, read the data coming from the serial port and finally plot the data via a real-time chart using JFreeChart.

##Info##
For detailed information visit my blog [Physics Light](http://physicslight.wordpress.com/) 
at the page [Arduino Serial Read Using Java](http://physicslight.wordpress.com/2015/01/02/arduino-serial-read-using-java/) 

##Configure##

[Download](http://www.oracle.com/technetwork/java/javase/downloads/jdk-netbeans-jsp-142931.html) and install Java SE development kit JDK and NetBeans IDE Windows x86 version (32 bit)

###RXTX library installation guide Windows###

[Download](http://rxtx.qbang.org/pub/rxtx/rxtx-2.1-7-bins-r2.zip) the RXTX java library

Navigate to the JDK directory in your PC and find the jre directory:

C:\Program Files (x86)\Java\jdk1.8.0_20\jre

Open the RXTX library folder and copy the files in the jre directory

RXTXcomm.jar → …\lib\ext

rxtxSerial.dll → …\bin

##License##
The Arduino Serial Read Java app is a free software licensed under [GNU GPL v3.0](http://www.gnu.org/licenses/gpl-3.0.txt) General Public License 

##Contact##
Contact me on twitter personal account [@2m_marco](https://twitter.com/2m_marco) sciblog account [@PhysicsLight](https://twitter.com/physicslight) 

##Acknowledgements##
Luciano Zu for developing [Ardulink](http://www.ardulink.org/) the open source Java library for controlling Arduino board, and for helping me in my first Java app.
