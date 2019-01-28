package controllers.elisisplay.jobs.cache;

import controllers.elisisplay.cachecookie.cache.ElisisCache;
import play.jobs.Job;

import java.io.FileNotFoundException;

/**
 * Created by Bienvenu on 21/11/2018 in elisisplay.
 */
public class CacheJobStart extends Job{

    public void doJob() throws FileNotFoundException {
        _init();
    }

    public static void _init() throws FileNotFoundException {
        ElisisCache.init();
    }
}
