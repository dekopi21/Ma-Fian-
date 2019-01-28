package controllers.elisisplay.time;

import controllers.elisisplay.cachecookie.cache.DurationType;
import play.libs.Time;

/**
 * Created by Bienvenu on 18/10/2018 in schoolar_dev.
 */
public class TimeDuration {

    private String durationPattern;

    private DurationType durationType;

    private int duration;

    private int _duration;

    public TimeDuration(DurationType durationType, int duration) {
        this.durationType = durationType;
        this.duration = duration;
        durationPattern = duration(duration, durationType);
        _duration = Time.parseDuration(durationPattern);
    }

    public void reset(){
        durationPattern = duration(duration, durationType);
        _duration = Time.parseDuration(durationPattern);
    }

    public void reset(int duration){
        this.duration = duration;
        durationPattern = duration(duration, durationType);
        _duration = Time.parseDuration(durationPattern);
    }

    public void reset(DurationType durationType){
        this.durationType = durationType;
        durationPattern = duration(duration, durationType);
        _duration = Time.parseDuration(durationPattern);
    }

    public void reset(int duration, DurationType durationType){
        this.durationType = durationType;
        this.durationType = durationType;
        durationPattern = duration(duration, durationType);
        _duration = Time.parseDuration(durationPattern);
    }

    /**
     * Define a duration which match to the duration pattern as request by Play Time class.
     *
     * @param expiration   Duration
     * @param durationType Duration Type (Hour, Day...)
     * @return A duration that match to duration pattern as request by Play Time class.
     */
    public static String duration(int expiration, DurationType durationType) {
        if (durationType == null)
            return (expiration > 0) ? (String.valueOf(expiration) + 's') : "0s";

        return (expiration > 0) ? (String.valueOf(expiration) + durationType.abbreviation) : ('0' + durationType.abbreviation);
    }

    @Override
    public String toString() {
        return duration(duration, durationType);
    }
}
