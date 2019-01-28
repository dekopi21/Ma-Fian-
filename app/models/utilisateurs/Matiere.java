package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "matiere")
public class Matiere extends ElisisModel {

    @Column(nullable = false, unique = true, length = 50, name = "libelle_matiere")
    public String libelle;

    @Column(nullable = false)
    public Calendar date_update;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Competence> competence;
    @Override
    public String abbvr() {
        return "MATIERE";
    }

    public Matiere(String libelle) {
        this.libelle = libelle;
        this.competence = new ArrayList<Competence>();
        setCode();
        edit_date();
    }
}
