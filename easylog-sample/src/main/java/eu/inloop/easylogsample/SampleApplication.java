package eu.inloop.easylogsample;

import android.app.Application;

import eu.inloop.easylog.EasyLog;

/**
 * Created by tom on 29.01.16.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EasyLog.init(this, BuildConfig.DEBUG, "SampleApp");
    }

}
