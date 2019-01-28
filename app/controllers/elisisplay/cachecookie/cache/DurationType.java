package controllers.elisisplay.cachecookie.cache;

/**
 * An enum for duration.
 */
public enum DurationType {
    SECOND("s"),
    MINUTE("min"),
    HOUR("h"),
    DAY("d");

    DurationType(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public final String abbreviation;

    @Override
    public String toString() {
        return abbreviation;
    }
}
