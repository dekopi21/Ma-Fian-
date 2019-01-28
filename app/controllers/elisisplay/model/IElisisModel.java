package controllers.elisisplay.model;

import play.db.Model;

import javax.persistence.Transient;

/**
 * Interface for ElisisModel objects.
 */
public interface IElisisModel extends Model {

    @Transient
    byte DEFAULT_CODE_LENGTH = 15;


}
