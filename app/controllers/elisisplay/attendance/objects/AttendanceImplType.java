package controllers.elisisplay.attendance.objects;

/**
 * Method Type for storing the Attendance datas.
 */
public enum AttendanceImplType {

    /**
     * Storing all visitors on Database,
     * others objects and stats in serials files.
     *
     * Cookie serves to identifiy each user.
     */
    DATABASE_SERIALIZATION_COOKIE,

    /**
     * Store all objects in serials files,
     * and frequently updated files on cache.
     *
     * Cookie serves to identifiy each user.
     */
    SERIALIZATION_CACHE_COOKIE,

    /**
     * Make datas of attendance redundant on all possible implementations.
     */
    ALL,
    ;
}
