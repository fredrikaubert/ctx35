# What
The CTX35 Gateway interfaces a CTX35 unit connected to the serial port to send X10 commands.  X10 is a protocol to switch on and off lights and other appliances connected to the powerline. [[www.x10.com]]

You will need a CTX35([[http://www.xanura.nl/Portals/1/pdf/Gebr.handl_CTX35.pdf]]) and at least one other X10 compatible unit have any usage of this library. 

#Disclaimer
This library is in an early stage. Currently it only supports sending commands through CTX35, not receiving messages from the powerline. I have only tested it on my own computer. I have not tested all kinds of scenarios and commands, so the stability is still in the unkown. 


#Installation
The installation instructions are meant as a quick start, and did work for me when I tested it. If you run into problems you should consult the documentation/support of the product you have problems with. 

## Install USB serial device driver
If you are directly using your serial port on your computer you can skip this step. If you do not have an available serial port on your computer you should use a USB to serial device. Any USB-serial device should do. The installation procedure is for this product: http://www.iogear.com/product/GUC232A/

### Windows 
[driver](http://www.iogear.com/support/dm/driver/GUC232A#display)

### OSX 
[http://sourceforge.net/project/showfiles.php?group_id=157692]

### Linux
I have not tested this myself, but all new distros are supposed to have support out of the box, just plug it in. 

## Install RXTX
Rxtx is an API for communication with a serial- or parallel port in java. This is a provided dependency, and is expected by CTX35 api to be installed in your JVM. [[www.rxtx.org]]

### Download rxtx
(http://rxtx.qbang.org/pub/rxtx/rxtx-2.1-7-bins-r2.zip)
Note: Windows built in zip utility claims the file is corruped. It is not, just use a different program to unzip it. I have had success with total commander, but I guess others will also work. 

### Windows
put rxtxSerial.dll in jre\bin
put RXTXComm.jar in jre\lib\ext

### OSX
put RXTXcomm.jar in  /Library/Java/Extensions
put librxtxSerial.jnilib in /Library/Java/Extensions 

Create the directory /var/lock, and add your user to the group uucp:

	sudo mkdir /var/lock
	sudo chgrp uucp /var/lock
	sudo chmod g+w /var/lock
	sudo dscl . -append /Groups/uucp GroupMembership $USER

### Linux
Haven't testet this myself, read the README in rxtx download.

## See if it works
If you downloaded the source you could run the test Ctx35GatewayTest (change comport to where you installed the CTX35 device). 

If you downloaded the jar (not yet released) you should run this code:

	Ctx35Gateway gateway = new Ctx35Gateway();
	gateway.setCommport("COM3");
	gateway.init();
	gateway.transmit(new Transmission(new Addressing('A', 9,10,11), Command.on));



# License

Apache 2

[http://www.apache.org/licenses/LICENSE-2.0.txt]


