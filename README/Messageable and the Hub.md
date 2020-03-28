## Messageable and the Hub

Messageable is an interface implemented by all classes that need to communicate with the hub. Messageable always implements Runnable, which gives all modules their own threads. The Hub itself is also a thread.

### Lifetime of a Message
A message is created when soething noteworthy happens in a module or the gamelogic dictates the engine to do something. If the game logic dictates something, it can directly insert the message in without the Messageable interface. 

When the message enters the Hub, it is put on an ArrayList\<Message\>. The MessageHub thread "MHUB" is a while loop that ends only when the fxwindow is closed. It will repeatedly mail messages to every class that implements the Messageable interface until no messages are left. The MHUB thread then sleeps or 50ms.

Mailing is placing the message in each Messageable class. All messageable classes have their own messages arraylist that their thread reads. 

The interface-class relationship is slightly messy, but works nonetheless (it was originally its own class, but became an interface when I needed the Javafx Application Thread to be Messageable).

All Messageables have a readMessage(), which is a switch-case based on the Message code. Only some modules should react with some codes. 