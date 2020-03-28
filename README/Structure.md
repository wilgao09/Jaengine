## Structure

Heavily inspired from the link in the Introduction, everything is layered: "Lasagna is better than Spaghetti." This essentially means that most functions should be able to operate without depending on other functions. In an ideal world, everything would just be a function floating in in a vacuum, but thats not possible.

### Lasagna 
Lasagna is just abstractions over abstractions, building from bottom up. This is also the approach I took while building, which is why I'm writing this the day it's due. 

The blog talks about three layers: Framework, Systems, Message Bus, and Game Logic. However, because this project isn't world-endingly large, I have adapted this.

This project looks like: Modules, Message Hub, Engine, "Game Logic?"

The Modules are of the package jaengine.modules.MODULENAME.

**Note:** Messages is in modules, but really acts as the manager from the other three. As such, I will not consider it a module when I refer to modules.

Module MODULENAME always has a class MODULENAME that implements interface Messageable from the jaengine.modules.messages package. This allows the class to interact with some MessageHub object.

## Messages
Messages are simple objects: they carry a code and an object array. The code determines what the command is and the object array has data much like String[] args carrys parameters. COdes, their functions, and their parameters are included in the Codes.txt file. 


## Rules
This is lasagna! All upper laters have access to lower layers, but lower layers cannot see the above. Children should also not be abl to see others (this is violated multiple times out of necessity).