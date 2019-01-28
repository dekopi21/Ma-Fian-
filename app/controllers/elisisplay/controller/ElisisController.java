package controllers.elisisplay.controller;

import controllers.CRUD;
import controllers.elisisplay.attendance.Attendance;
import controllers.elisisplay.attendance.IObservator;
import controllers.elisisplay.attendance.Observator;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.logging.Logger;
import controllers.elisisplay.secure.ApplicationSection;
import controllers.elisisplay.secure.ElisisSecure;
import controllers.elisisplay.secure.Profiles;
import play.mvc.After;
import play.mvc.Before;

/**
 * Un controleur Play avec quelques fonctionnalités en plus.
 * <p>
 * Est différent de {@code ElisisModelController} par le
 * fait que ses principales actions s'opérent sur les vues.
 * <p>
 * Les classes qui héritent de ce controlleur ne doivent à priori
 * qu'afficher des vues.
 * <p>
 * Tous les autres traitements complémentaires, doivent s'effectuer
 * dans les ModelController.
 */
public abstract class ElisisController extends CRUD implements IElisisController {

    /**
     * Controller Description.
     * <p>
     * Used to create page object from the controller.
     *
     * @return Controller Description.
     */
    protected String description() {
        return "description";
    }

    /**
     * Définit si par défaut les controleurs soon observés ou non.
     */
    private static boolean observeControllers = Boolean.FALSE;

    /**
     * Valeur par défaut de la Section d'un controlleur.
     */
    public static final ApplicationSection DEFAULT_SECTION = ApplicationSection.PUBLIC;

    /**
     * Objet par défaut occupé de l'observation des controlleurs.
     */
    public static IObservator defaultObservator = new Observator(1, 1);

    /**
     * Objet observator in use.
     */
    protected IObservator observator;

    /**
     * Section du controlleur.
     * <p>
     * Doit être surchargée.
     *
     * @return La section de ce controlleur.
     */
    public ApplicationSection mySection() {
        return DEFAULT_SECTION;
    }

    public void initObservator() {
        observator = defaultObservator;
    }

    /**
     * Détermine l'observator actuelle.
     *
     * @return L'observateur qui doit être utilisé pour profiler les controleurs.
     */
    private IObservator _observator() {
        if (observator != null)
            return observator;

        return defaultObservator;
    }

    /**
     * Méthode à redéfinir pour indiquer si un controleur doit être observé ou non.
     *
     * @return Indique si un controleur doit etre observé ou pas.
     */
    protected boolean observable() {
        return observeControllers;
    }

    /**
     * Est utilisé pour savoir dans un controleur si l'observation est activé ou non.
     *
     * @return Indique si ce controleur est observé ou non.
     */
    private boolean _observable() {
        //observable() must be redefined in inherit classes.
        return observable() && observeControllers;
    }

    /**
     * Sécurise l'accès aux Vues rendues par cette page.
     *
     * @return {@code true} si l'utilisateur peut accèder à la vue correspondante.
     * <p>
     * Ce retour n'est utile que pour ne pas avoir pour signature à cette méthode,
     * le "public static void" qui est utilisé dans les controlleurs pour
     * retourner les vues.
     */
    public boolean secure() {
        return Profiles.canSafelyAccess(mySection());
    }

    /**
     * Méthode de classe qui sécurise l'accès à une vue suivant la section
     * qui lui est passé entre paramêtres.
     *
     * @return {@code true} si l'utilisateur peut accèder à la vue correspondante.
     * <p>
     * Ce retour n'est utile que pour ne pas avoir pour signature à cette méthode,
     * le "public static void" qui est utilisé dans les controlleurs pour
     * retourner les vues.
     */
    public static boolean secure(ApplicationSection applicationSection) {
        return Profiles.canSafelyAccess(applicationSection);
    }

    @Before
    public static void sessionInit() {
        //TODO View utility of this. This can't be done by other method ?
        ElisisSecure.safeInit();
    }

    /**
     * Securiser toutes les actions dans ce controlleur.
     * <p>
     * Ne devrait pas avoir d'impact, puisque le controlleur ne devrait
     * implémenter que de simples méthodes
     */
    @Before
    public void secureAll() {
        secure();
    }

    protected ElisisController() {
    }

    @After
    public void observe() {
        //if (_observable())
            _observator().observe(request, response, mySection(), description(), Attendance._getVisitor());
    }

    public static boolean flash(boolean isError, String error, String success) {
        if (isError)
            flash.error(error);

        else
            flash.success(success);

        return isError;
    }

    public static boolean flashDebug(boolean isError, String error, String success) {
        if (isError) {
            Logger.debug(error);
            flash.error(error);
        } else
            flash.success(success);

        return isError;
    }

    public static boolean flashDebug(boolean isError, String error, String success, Throwable throwable) {
        if (isError) {
            Logger.debug(error, throwable);
            flash.error(error);
        } else
            flash.success(success);

        return isError;
    }

    public static boolean flashError(String error, String success) {
        return flash(StringUtils.isNotEmpty(error), error, success);
    }

    public static boolean flashSuccess(String error, String success) {
        return flash(StringUtils.isEmpty(success), error, success);
    }

    @Override
    public String toString() {
        return description();
    }
}
