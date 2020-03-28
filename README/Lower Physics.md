## Lower Physics

Hell on earth: jaengine.modules.physics
I'm going to be purposefully vague here

As of writing this I have not implemented collisions.
The important players here are "GameObject"s and "GameAttribute"s. GameObject inherits from Node. 

**Note:** The GameObject's super class is Node\<GameObject\>. This implies that if you have a Node\<GameObject\>, you can get it's GameObject either by (GameObject)(the_node) or the_node.getData(). Casting has overhead, so I will use .getData() most of the time. 

GameObjects only carry around its center, name, the environment its in, and its attributes.

A GameObject's attributes are GameAttributes (shockedpikachu). They were once stored in an ArrayList but now they're in a HashMap(key: attribute name, value: the attribute object).

Attributes are the general form of more specific Attributes (RigidBody, Hitbox, Mesh); their general criteria is defining how an object behaves. 

Environment is an extension of GameObject, but IT MUST NEVER TAKE IN ANY ATTRIBUTES. It's the root node for all other objects.

The Physics class is a nightmare. Currently, it runs a physics tick ~every 50ms (20 times a second, like mineraft!), but according to graphics, a pixel is a meter. Oops.

In runPhysicsTick, you start with a hashmap of gameobjects and object[] (object[] is length 2, first carries a vector, second carries a double). All objects that are found to require redrawing are added into this hashmap, along with its translation and rotation.

tick is a recursor that holds the process for running a tick on the environment of the physics class (onjectTree). It basically says :
```
"starting fron environment, look at your [environments] children"
"If there are RigidBody'd children, move them according to their forces and velocities"
"If they moved a meaningful distance, add them into the hashmap"
"If there are non-RigidBody'd children, move them according to how it's parent moved"
"Repeat this process, but start from the children you just scanned"
```

Notice thaat if you tried to tick environment, you'd enter an infinite loop because the environment's parent is itself (if no rigidbody) or an infinite slide (ALL OBJECTS FALL because the environment is falling). 
