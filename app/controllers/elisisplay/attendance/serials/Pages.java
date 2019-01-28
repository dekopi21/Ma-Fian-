package controllers.elisisplay.attendance.serials;

import controllers.elisisplay.attendance.Attendance;
import controllers.elisisplay.attendance.objects.Page;
import controllers.elisisplay.serial.ElisisSerial;

/**
 *
 */
public class Pages extends ElisisSerial<Page> {

    public Pages(boolean initSerial) {
        super(true);
    }

    private static final long serialVersionUID = 1000000000000000004L;

    @Override
    public String filename() {
        return Attendance.pagesSerFileCompleteName;
    }

    @Override
    public String dirPlayPath() {
        return Attendance.attendanceDirPath;
    }
}
