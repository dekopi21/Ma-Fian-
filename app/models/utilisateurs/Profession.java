package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Calendar;

@Entity
@Table(name = "profession")
public class Profession extends ElisisModel {

    @Column(name = "libelle_profession", nullable = false, unique = true)
    public String libelle;

    @OneToOne
    public Categorie categorie;

    /**
     *
     * @param libelleProfession
     */
    public Profession(String libelleProfession) {
        this.libelle = libelleProfession;
        setCode();
        edit_date();
    }

    @Override
    public String abbvr() {
        return null;
    }
}
