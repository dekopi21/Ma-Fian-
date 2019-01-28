package controllers.elisisplay.attendance;

import controllers.elisisplay.attendance.objects.AttendanceImplType;
import controllers.elisisplay.attendance.objects.Page;
import controllers.elisisplay.attendance.objects.Visitor;
import controllers.elisisplay.attendance.serials.All;
import controllers.elisisplay.cachecookie.cache.ElisisCache;
import controllers.elisisplay.secure.UserType;
import controllers.elisisplay.utils.Utils;
import play.mvc.Controller;
import play.mvc.Http;

import java.lang.reflect.Method;

/**
 *
 */
public class Attendance extends Controller{

    /**
     * Nombre de fichiers de sérialisation utilisés.
     */
    public static final int nbFilesSer = 4;

    /**
     * Dossier des fichiers sérialisées pour la visualisation de
     * l'afflux sur le site.
     */
    public static final String attendanceDirPath = "/datas/attendance/";

    /**
     * Nom complet du fichier contenant tous les visiteurs du site.
     */
    public static final String visitorsFileCompleteName = "allvisitors.ser";

    /**
     * Nom complet du fichier contenant toutes les pages du site.
     */
    public static final String pagesSerFileCompleteName = "allpages.ser";

    /**
     * Attendance implementation type, use by default.
     */
    public static final AttendanceImplType defaultImplType = AttendanceImplType.SERIALIZATION_CACHE_COOKIE;

    /**
     * Attendance implementation type, who is actually in use.
     */
    public static AttendanceImplType implType = defaultImplType;

    public static String getVisitor() {
        return null;
    }

    public static Visitor _getVisitor() {
        return null;
    }

    public static String getUserCode() {
        return null;
    }

    public static UserType getUserOnPageType() {
        return null;
    }

    public static String getPageCode(Method method) {
        Page page = (Page) Utils.retrieveObjectFromList(All.pages(), "method", method);
        return page != null ? page.code : null;
    }

    public static String getPageCode(Http.Request request) {
        return getPageCode(request.invokedMethod);
    }

    public static boolean visitorIsCached(){
        return ElisisCache.contains(session.getId());
    }



}
