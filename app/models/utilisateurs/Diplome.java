package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;
import play.data.validation.Required;
import play.data.validation.Unique;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "diplome")
public class Diplome extends ElisisModel {
    @Unique
    @Required
    @Column(nullable = false, name = "Libelle_Diplome", unique = true)
    public String libelleDiplome;

    @Column(unique = true, nullable = false,name = "Description_Diplome")
    public String description;

    /**
     *
     * @param libelleDiplome
     * @param description
     */
    public Diplome(String libelleDiplome, String description) {
        this.libelleDiplome = libelleDiplome;
        this.description = description;
        setCode();
    }

    @Override
    public String abbvr() {
        return null;
    }
}
