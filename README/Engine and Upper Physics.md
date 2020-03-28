## The Engine

The engine is the intuitive part that the top-most interacts with. The engine class is part of the jaengine.core package. 

The engine comes with some functionality: it can start the entire module system and send the start signal. It can also add objects, move objects, and probably be able to do other stuff.

The class exists to simplify much of the modules and their codes. It should be able to translate commands into Messages. Currently, its only sibling is the jaengine.physics package (not to be confused which jaengine.module.physics)

## Upper Physics
The only file in the janegine.physics package is Block right now. Block is a generalization of rectangular objects (or should be at least). It will automatically create the mesh, rigidbody, and hitbox associated with the box. 