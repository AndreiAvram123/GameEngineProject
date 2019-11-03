package sample.model;

public enum TAGS {
    NONE("NONE"), PLAYER("PLAYER"), ENEMY("ENEMY"), COLLECTABLE("COLLECTABLE"), CHECKPOINT("CHECKPOINT");
    private String name;

    TAGS(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public  static TAGS getObjectByName(String name) {
        switch (name) {
            case "PLAYER":
                return PLAYER;
            case "ENEMY":
                return ENEMY;
            case "COLLECTABLE":
                return COLLECTABLE;
            case "CHECKPOINT":
                return CHECKPOINT;

        }
        return NONE;
    }

}
