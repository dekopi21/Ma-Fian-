package controllers.elisisplay.logging;

/**
 * Created by Bienvenu on 27/08/2018 in controllers.elisisplay.
 */
public enum Level {

    FATAL("FATAL", 6),
    SEVERE("SEVERE", 5),
    WARNING("WARNING", 4),
    INFO("INFO", 3),
    CONFIG("CONFIG", 2),
    DEBUG("DEBUG", 1),
    FINE("FINE", 0);

    Level(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * Nom du Level.
     */
    private String name;

    /**
     * La valeur entière du level.
     */
    private int value;

    @Override
    public String toString() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
