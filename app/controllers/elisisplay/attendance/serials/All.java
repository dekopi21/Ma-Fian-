package controllers.elisisplay.attendance.serials;

import controllers.elisisplay.attendance.objects.ActionOnPage;
import controllers.elisisplay.attendance.objects.Page;
import controllers.elisisplay.attendance.objects.PageAttended;
import controllers.elisisplay.attendance.objects.Visitor;

import java.util.ArrayList;

/**
 * Links on All objects of Attendance.
 */
public class All {

    public static ArrayList<ActionOnPage> actions() {return new ActionsOnPages(true).all();}

    public static ArrayList<PageAttended> pageAttendeds() {return new PagesAttended(true).all();}

    public static ArrayList<Page> pages() {return new Pages(true).all();}

    public static ArrayList<Visitor> visitors() {return new Visitors(true).all();}
}
