package controllers.elisisplay.attendance.serials;

import controllers.elisisplay.attendance.Attendance;
import controllers.elisisplay.attendance.objects.PageAttended;
import controllers.elisisplay.serial.ElisisSerial;

/**
 * Created by Bienvenu on 14/10/2018 in schoolar_dev.
 */
public class PagesAttended extends ElisisSerial<PageAttended> {

    public PagesAttended(boolean initSerial) {
        super(initSerial);
    }

    private static final long serialVersionUID = 1000000000000000005L;

    @Override
    public String filename() {
        return "pagesattended.ser";
    }

    @Override
    public String dirPlayPath() {
        return Attendance.attendanceDirPath;
    }
}
