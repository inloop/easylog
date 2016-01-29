EasyLog
----

EasyLog is <a href="http://developer.android.com/reference/android/util/Log.html">android.util.Log</a> wrapper making logging even easier.

Basic Usage
-----

Usage of EasyLog is simple. First you have to configure it. Best place is `Application.onCreate()` method.

```Java
    @Override
    public void onCreate() {
        super.onCreate();
        EasyLog.init(this, BuildConfig.DEBUG, "SampleApp");
    }
```

EasyLog has couple of static methods:

```Java
    EasyLog.v("Hello from MainActivity");

    List args = Arrays.asList("one", "two");

    EasyLog.d("Called with %d arguments", args.size());
    EasyLog.d("Arguments are %s", args);
    EasyLog.e("Error occurred: %s", new Exception());
```

Output of those sample method is:

```
01-29 15:56:59.040 32228-32228/eu.inloop.easylogsample V/SampleApp: main                          eu.inloop.easylogsample.MainActivity.onCreate(): Hello from MainActivity
01-29 15:56:59.040 32228-32228/eu.inloop.easylogsample D/SampleApp: main                          eu.inloop.easylogsample.MainActivity.onCreate(): Called with 2 arguments
01-29 15:56:59.040 32228-32228/eu.inloop.easylogsample D/SampleApp: main                          eu.inloop.easylogsample.MainActivity.onCreate(): Arguments are [one, two]
01-29 15:56:59.040 32228-32228/eu.inloop.easylogsample E/SampleApp: main                          eu.inloop.easylogsample.MainActivity.onCreate(): Error occurred: null
                                                                    java.lang.Exception
                                                                        at eu.inloop.easylogsample.MainActivity.onCreate(MainActivity.java:24)
                                                                        at android.app.Activity.performCreate(Activity.java:6237)
                                                                        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1107)
                                                                        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2369)
                                                                        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2476)
                                                                        at android.app.ActivityThread.-wrap11(ActivityThread.java)
                                                                        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1344)
                                                                        at android.os.Handler.dispatchMessage(Handler.java:102)
                                                                        at android.os.Looper.loop(Looper.java:148)
                                                                        at android.app.ActivityThread.main(ActivityThread.java:5417)
                                                                        at java.lang.reflect.Method.invoke(Native Method)
                                                                        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:726)
                                                                        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:616)
```

Arguments
-----
Basically it uses the same format as `java.util.Formatter`. 

Download
--------

Easylog is published in <a href="https://bintray.com/bintray/jcenter">JCenter repo</a>. So you can simply include it 
in your project by following line of code in `build.gradle`.
 
```groovy
compile 'eu.inloop:easylog:0.6.+'
```

Credits
-------

Code is inspired by class `com.scvngr.levelup.core.util.LogManager` from <a href="https://github.com/TheLevelUp/levelup-sdk-android">https://github.com/TheLevelUp/levelup-sdk-android</a> license under Apache 2.0 License.