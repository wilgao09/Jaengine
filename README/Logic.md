## Logic
### There's nothing logical here, I'm sorry

jaengine.math -> janegine.logic

This is the reason why this README documentation is a folder

This is also all the compey sciey of this project. 

DataHolder can safely be ignored; it used to be implemented by node, but was cut after realizing it make the process to bureaucratic. 

Node is a class that describes nodes. It hasn't changed from the last project and just keeps track of connections and some data. Its relationship with Gameobject is questionable. 

ZeroArgFuncWrapper, OneArgFUnc,Wrapper, and Recursor are a disasterous trio. Zero and One are both functional interfaces, one with zero ags and one with one arg (Object). They're both for lambda expressions. 

Recursor is a class that helps create recursive lambda functions. It ties the functional interface to an object that I can call. This is currently only used in the runPhysicsTick method.

Vector2D is a bunch of vector math. They can describe points too!