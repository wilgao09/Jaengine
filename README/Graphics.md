## Graphics

I f-ing hate JavaFX.

JavaFX operations usually have to all happen on its own thread so it extends Application and implements Messageable.

The constructors look weird because they are weird. When you run Application.launch(), it creates a mirror based on the zeroarg construction for the class that inherits from Application. BUT because it implements Messageable, it needs to set its hub *somehow*. 

The method for creating the Graphics is to make its zero arg constructor retrieve the hub data from some static variable in the class. 

Note that Javafx's thread takes over the current thread you're on, so I pushed that into its own thread so it can take that random thread over.

Just know that everything a normal module should do is eventually done, though in a super roundabout way. 

It's method of message reading is different. It has to run a background daemon that continuously checks for messages. When the daemon thread finds a message, it pushes the command to read the message to the FX thread. 

Some message operations are covered by teh Map class, which stored and moves **VISIBLE** representations ov the objects in the environment. Yes, objects can be invisible by having no mesh (10/10 physics)

ShapeBuilder is a work in progress for defaults, but it might be scrapped.