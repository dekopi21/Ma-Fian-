package controllers.elisisplay.attendance.observator;

import controllers.elisisplay.serial.ElisisSerial;

import java.io.Serializable;

/**
 * Created by Bienvenu on 31/10/2018 in schoolar_dev.
 */
public abstract class Observator<S extends Serializable> implements IObserver {

    protected ElisisSerial<S> serial;

    @Override
    public final boolean flush() {
        return serial.flush();
    }

    @Override
    public final boolean load() {
        return serial.load();
    }

    @Override
    public final void init() {
        serial.init();
    }

    public final void safeInit() {
        serial.safeInit();
    }
}
