package jaengine.modules.physics;

//this is designed to be INHERITED FROM
//NEVER UE THIS RAW

/**
 * The generic layout for an attribute
 */
public class GameAttribute {
    public final String name;
    protected boolean active = true;
    /**
     * Create a new GameAttribute with a name; active by default
     * @param s the name of the attribute
     */
    public GameAttribute(String s) {
        this.name = s;
    }
    /**
     * Create a new GameAttribute with a string and an activation state
     * @param s the name of this attribute
     * @param a the activity of this attribute; true means the attribute is active and false means that attribute is inactive; active by default
     */
    public GameAttribute(String s, boolean a) {
        this.name = s;
        this.active = a;
    }
    /**
     * Deactivate the attribute
     */
    public void deactivate() {
        this.active = false;
    }
    /**
     * Activate the attribute
     */
    public void activate() {
        this.active = true;
    }
}