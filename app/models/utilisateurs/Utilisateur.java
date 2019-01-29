package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;
import play.data.validation.Unique;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "utilisateur")
public class Utilisateur extends ElisisModel {

    @Column(name = "nom_utilisateur",nullable = false, length = 25)
    public String nom;

    @Column(name = "prenom_utilisateur",nullable = false, length = 25)
    public String prenom;

    @Lob
    @Column(name = "domicile",nullable = false)
    public String domicile;

    @Column(name = "Sexe_utilisateur",nullable = false, length = 1)
    public char sexe;

    @Column(name = "date_naissance_utilisateur",nullable = false)
    public Calendar dateNaissance;

    @Column(name = "numero_telephone",nullable = false, length = 25, unique = true)
    public String numeroTelephone;

    @Column(name = "numero_identite",nullable = false, length = 25, unique = true)
    public String numeroIdentide;

    @OneToMany(cascade = CascadeType.ALL)
    public List<CompteUtilisateur> compteUtilisateurs;

    /**
     *
     * @param nom
     * @param prenom
     * @param domicile
     * @param sexe
     * @param dateNaissance
     * @param numeroTelephone
     * @param numeroIdentide
     */
    public Utilisateur(String nom, String prenom,String domicile, char sexe, Calendar dateNaissance, String numeroTelephone, String numeroIdentide) {
        // verifier si l'utilisateur poccede moins de (2) comptes
        //  verifier si les comptes sont differents a la création d'un nouveau compte
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.numeroTelephone = numeroTelephone;
        this.domicile = domicile;
        this.compteUtilisateurs = new ArrayList<CompteUtilisateur>();
        this.numeroIdentide = numeroIdentide;
        edit_date();
        setCode();
    }

    @Override
    public String abbvr() {
        return "UTILI";
    }
}
