package eu.inloop.easylogsample;


import android.app.Activity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import eu.inloop.easylog.EasyLog;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyLog.v("Hello from MainActivity");

        List args = Arrays.asList("one", "two");

        EasyLog.d("Called with %d arguments", args.size());
        EasyLog.d("Arguments are %s", args);
        EasyLog.e("Error occurred: %s", new Exception());
    }

}
