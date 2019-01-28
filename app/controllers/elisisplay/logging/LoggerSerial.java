package controllers.elisisplay.logging;

import controllers.elisisplay.serial.ElisisSerial;

/**
 * Created by Bienvenu on 08/11/2018 in schoolar_dev.
 */
public class LoggerSerial extends ElisisSerial<LogMessage> {

    public LoggerSerial(boolean init) {
        super(init);
    }

    @Override
    public String filename() {
        return "logs.ser";
    }

    @Override
    public String dirPlayPath() {
        return "/logs/";
    }
}
