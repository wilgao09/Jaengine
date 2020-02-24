package jaengine.modules.physics;

//this is designed to be INHERITED FROM
//NEVER UE THIS RAW

public class GameAttribute {
    protected String name;
    protected boolean active = true;
    public GameAttribute(String s) {
        this.name = s;
    }
    public GameAttribute(String s, boolean a) {
        this.name = s;
        this.active = a;
    }
    public void deactivate() {
        this.active = false;
    }
    public void activate() {
        this.active = true;
    }
}