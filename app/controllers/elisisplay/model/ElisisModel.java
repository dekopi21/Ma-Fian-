package controllers.elisisplay.model;

import controllers.elisisplay.controller.ElisisModelController;
import controllers.elisisplay.lang.StringUtils;
import controllers.elisisplay.utils.Utils;
import play.db.jpa.JPABase;
import play.db.jpa.Model;
import play.mvc.Before;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Base class of models|entities.
 *
 * You must call setCode() before saving objects.
 */
@SuppressWarnings("ALL")
@MappedSuperclass
public abstract class ElisisModel extends Model implements IElisisModel, Serializable {

    private static final long serialVersionUID = 5666931662764454660L;

    /**
     * ElisisModel key used instead of Play default Model Id.
     * <p>
     * Don't try to manipulate this key directly only for research or
     * to read it.
     */
    @Column(unique = true, length = DEFAULT_CODE_LENGTH, nullable = false)
    public String code;



    @Column(nullable = true, name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar createdAt;
    /*
    @Column(nullable = true, name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar updateAt;*/

    protected ElisisModel() {
    }

    /**
     * Generate code of a new Entity Model object.
     * <p>
     * Don't use this method to set code of an entity read
     * from the database.
     *
     * @return an entity valid code.
     */
    public String generateCodeSafe() {
        if (StringUtils.isEmpty(code) || exists(code))
            return generateCode();

        return code;
    }

    /**
     * Generate a valid entity model code.
     *
     * @return a valid entity model code.
     */
    public String generateCode() {
        return new ElisisModelController(this.getClass()).codeGenerated(15);
    }

    /**
     * If a entity model code value equal an object field {@code code} value.
     *
     * @param code code
     * @return {@code true} if an entity code value already
     */
    public boolean exists(String code) {
        return this.find("code", code) != null;
    }

    /**
     * If a entity model code value equal this object field {@code code} value.
     *
     * @return
     */
    public boolean exists() {
        return exists(code);
    }

    /**
     * Set this entity valid code.
     */
    @Before
    public void setCode() {
        code = generateCodeSafe();
    }

    @Before
    public void edit_date(){
       createdAt = new Calendar() {
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
    }

    /**
     * Used to generate entity name.
     *
     * @return This model entities abbreviation.
     */
    public abstract String abbvr();


    @Override
    public String toString() {
        return code;
    }
}
