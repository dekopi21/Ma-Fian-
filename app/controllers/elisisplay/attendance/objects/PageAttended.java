package controllers.elisisplay.attendance.objects;

import controllers.elisisplay.attendance.Attendance;
import controllers.elisisplay.secure.UserType;
import play.mvc.Http;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Page consultée par un utilisateur.
 */
public class PageAttended implements Serializable {

    private static final long serialVersionUID = 1000000000000000003L;

    /**
     * Code de la page actuelle.
     */
    public String pageCode;

    /**
     * Type de l'utilisateur présent actuellement sur la page.
     */
    public UserType userOnPageType = UserType.VISITOR;

    /**
     * Date à laquelle la page a été consultée.
     */
    public Calendar dateReached = new GregorianCalendar();

    /**
     * Indique si l'utilisateur a été enrégistré avec un compte utilisateur.
     *
     * @return {@code true} si l'utilisateur est connecté,
     * {@code false} si l'utilisateur est un simple visiteur ou
     * ne s'est jamais connecté dans la session actuelle.
     */
    public boolean isUser() {
        return userCode != null;
    }

    /**
     * Code de l'utilisateur sur la page.
     * <p>
     * Par défaut est égale à {@code null}, c'est à dire un visiteur
     * simple.
     */
    public String userCode = null;

    /**
     * Code du visiteur de la page.
     */
    public String visitorCode;

    public PageAttended(Http.Request request) {
        this.pageCode = Attendance.getPageCode(request.invokedMethod);
        this.userOnPageType = Attendance.getUserOnPageType();
        this.dateReached = new GregorianCalendar();
        this.userCode = Attendance.getUserCode();
        this.visitorCode = Attendance.getVisitor();
    }
}
