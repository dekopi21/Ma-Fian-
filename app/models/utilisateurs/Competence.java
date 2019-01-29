package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "competance")
public class Competence extends ElisisModel {

    @Column(name = "libelle_competance",unique = true, nullable = false, length = 50)
    public String libelle;

    @Lob
    @Column(name = "description_competance",nullable = false)
    public String descriptioin;


    @ManyToOne(cascade = CascadeType.ALL)
    public Matiere matiere;

    public Competence(String libelle, String descriptioin ) {
        this.libelle = libelle;
        this.descriptioin = descriptioin;
        edit_date();
        setCode();
    }



    @Override
    public String abbvr() {
        return "COMPET";
    }
}
