package controllers.elisisplay.jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * Created by Bienvenu on 19/11/2018 in elisisplay.
 */
@OnApplicationStart
public class Startup extends Job{

    public void doJob() {
        //new CacheJobStart().doJob();
    }
}
