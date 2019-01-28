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

    @Column(nullable = false, updatable = false)
    public Calendar date_editioin;
    @OneToOne
    public Categorie categorie;

    public Profession(String libelleProfession) {
        this.libelle = libelleProfession;
        this.date_editioin = new Calendar() {
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
    }

    @Override
    public String abbvr() {
        return null;
    }
}
