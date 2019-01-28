package controllers.elisisplay.cachecookie.cache;

import controllers.elisisplay.ElisisPlay;
import controllers.elisisplay.reflect.Match;
import controllers.elisisplay.serial.ElisisSerial;

/**
 * Created by Bienvenu on 22/11/2018 in elisisplay.
 */
public class CacheSerial extends ElisisSerial<Match> {

    /**
     * Local cache serial file name.
     */
    public static final String LOCAL_CACHE_SERIAL_NAME = "localCache.ser";

    /**
     * Local cache serial file directory path (e.g. /datas/cache on Windows System)
     */
    public static final String LOCAL_CACHE_SERIAL_DIRPATH = ElisisPlay.FILE_SEP + "datas" + ElisisPlay.FILE_SEP + "cache";

    private static final long serialVersionUID = -8801112691169117818L;

    public CacheSerial(boolean initSerial) {
        super(initSerial);
    }

    /**
     * @return The complete filename of the serialization file of this serial object.
     */
    @Override
    public String filename() {
        return LOCAL_CACHE_SERIAL_NAME;
    }

    /**
     * @return The directory where this object will or has been serialized path.
     */
    @Override
    public String dirPlayPath() {
        return LOCAL_CACHE_SERIAL_DIRPATH;
    }
}
