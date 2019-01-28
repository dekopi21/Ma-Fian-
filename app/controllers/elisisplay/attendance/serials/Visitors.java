package controllers.elisisplay.attendance.serials;

import controllers.elisisplay.attendance.Attendance;
import controllers.elisisplay.attendance.objects.Visitor;
import controllers.elisisplay.serial.ElisisSerial;

/**
 * Created by Bienvenu on 13/10/2018 in schoolar_dev.
 */
public class Visitors extends ElisisSerial<Visitor> {

    public Visitors(boolean initSerial) {
        super(initSerial);
    }

    private static final long serialVersionUID = 1000000000000000005L;

    @Override
    public String filename() {
        return "visitors.ser";
    }

    @Override
    public String dirPlayPath() {
        return Attendance.attendanceDirPath;
    }
}
