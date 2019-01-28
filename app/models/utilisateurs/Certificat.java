package models.utilisateurs;

import controllers.elisisplay.model.ElisisModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Calendar;

@Entity
@Table(name = "certificat")
public class Certificat extends ElisisModel {

    @Column(name = "libelle_certificat", unique = true, nullable = false)
    public String libelle;

    @Lob()
    @Column(name = "description_certificat", nullable = false)
    public String description;

    public Certificat(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
        setCode();
        edit_date();
    }

    @Override
    public String abbvr() {
        return "CERTIF";
    }
}
