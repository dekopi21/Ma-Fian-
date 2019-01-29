package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "etablissementScolaire")
public class Etablissement extends ElisisModel {

    @Column(name = "nom_etablissement", nullable = false, unique = true)
    public String nomEtablissement;

    @Lob
    @Column(name = "adresse_de_etablissement", nullable = false)
    public String adresse;

    /**
     * return int
     * premet de definir une étoile pour l'etablissement
     */
    @Column(name = "etoile_de_renommer", nullable = false)
    public int renommer;

    @Column(name = "date_debut_Ecole", nullable = false)
    public Calendar dateDebut;

    @Column(name = "Date_fin_Ecole")
    public Calendar dateFin;

    @Column(nullable = false)
    public boolean enCours;

    /**
     * return true si la personne frequent dans cette etablissement
     */
    @Column(nullable = false)
    public boolean frequente;

    @ManyToOne(cascade = CascadeType.ALL)
    public Utilisateur utilisateur;

    /**
     *
     * @param nomEtablissement
     * @param adresse
     * @param dateDebut
     * @param dateFin
     * @param frequente
     */
    public Etablissement(String nomEtablissement, String adresse, Calendar dateDebut, Calendar dateFin, boolean frequente) {
        this.nomEtablissement = nomEtablissement;
        this.adresse = adresse;
        this.dateDebut = dateDebut;
        this.enCours = false;
        this.dateFin = dateFin;
        this.renommer = 0;
        this.frequente = frequente;
        setCode();
        edit_date();
    }

    /**
     *
     * @param nomEtablissement
     * @param adresse
     * @param dateDebut
     * @param frequente
     */
    public Etablissement(String nomEtablissement, String adresse, Calendar dateDebut, boolean frequente) {
        this.nomEtablissement = nomEtablissement;
        this.adresse = adresse;
        this.renommer = 0;
        this.dateDebut = dateDebut;
        this.enCours = true;
        this.frequente = frequente;
        setCode();
        edit_date();
    }

    @Override
    public String abbvr() {
        return "ETABL";
    }

}
