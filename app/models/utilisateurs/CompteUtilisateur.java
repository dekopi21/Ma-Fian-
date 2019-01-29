package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;
import play.data.validation.Required;
import play.data.validation.Unique;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "compte" )
public class CompteUtilisateur extends ElisisModel {

    /**
     *
     */
    @Column(name = "email_compte", unique = true, nullable = false)
    public String email;

    @Column(name = "password", unique = true, nullable = false)
    public String mot_de_Passe;

    public boolean isTeacher;

    public boolean isParent;

    public boolean isAdmin;

    @Column(nullable = false)
    public Calendar dateUpdate;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Gallerie> galleries;

    @Column(name = "date_connexion", nullable = false)
    private Calendar date_connexion;

    @ManyToOne
    public Utilisateur utilisateurs;

    /**
     *
     * @param email_Compte
     * @param mot_de_Passe
     * @return true if user compte exist
     */
    public static boolean connect(String email_Compte, String mot_de_Passe) {
        return find("byEmailAndPassword", email_Compte, mot_de_Passe).first() != null;
    }


    @Override
    public String abbvr() {
        return "COMPT";
    }

    /**
     *
     * @param email_Compte
     * @param mot_de_Passe
     * @param status
     */
    public CompteUtilisateur(@Required String email_Compte,@Required String mot_de_Passe, boolean status) {
        this.email = email_Compte;
        this.mot_de_Passe = mot_de_Passe;
        this.isTeacher = status;
        this.isParent = !status;
        this.isAdmin = isAdmin;
        this.galleries = new ArrayList<Gallerie>();
        this.dateUpdate = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };
        this.date_connexion = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };
        setCode();
        edit_date();
    }

    /**
     *
     * @param email_Compte
     * @param mot_de_Passe
     */
    public CompteUtilisateur(@Required String email_Compte,@Required String mot_de_Passe) {
        this.email = email_Compte;
        this.mot_de_Passe = mot_de_Passe;
        this.isTeacher = false;
        this.isParent = false;
        this.isAdmin = true;
        this.galleries = new ArrayList<Gallerie>();
        this.dateUpdate = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };
        this.date_connexion = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };
        setCode();
        edit_date();
    }
}
