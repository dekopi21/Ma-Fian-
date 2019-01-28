package controllers;

import models.utilisateurs.CompteUtilisateur;
import play.data.validation.Required;
import play.utils.Java;

import java.lang.reflect.InvocationTargetException;

public class Account extends CRUD {

    public static void signUp(){
        render();
    }

    public static void createAccount(@Required String username, @Required String password, @Required String conf_password, @Required String personne) throws Throwable {
        // Check tokens
        Boolean conforme = false;
        if(password.equals(conf_password))
            conforme = true;

        if(validation.hasErrors() || correctWord(password, conf_password)) {
            flash.keep("url");
            flash.error("secure.error");
            params.flash();
            signUp();
        }
        // Mark user as connected
        session.put("username", username);
        if(personne.equals("techer"))
            new CompteUtilisateur(username,password,true).save();
        else
            if(personne.equals("parent"))
                new CompteUtilisateur(username,password,false).save();

        // Remember if needed
        // Redirect to the original URL (or /)
        //renderHtml(personne);
        Application.index();
    }

    private static boolean lengthMax(String a){
        if(a.length() > 8)
            return true;
        return false;
    }

    private static boolean sameWord(String a, String b){
        if(a.equals(b))
            return true;
        return false;
    }

    private static boolean correctWord(String a, String b){
        if(lengthMax(a) || sameWord(a,b) || lengthMax(b))
            return true;
        return false;
    }

    private static Object invoke(String m, Object... args) throws Throwable {

        try {
            return Java.invokeChildOrStatic(Secure.Security.class, m, args);
        } catch(InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

private int pop(){
        return 0;
}

}
