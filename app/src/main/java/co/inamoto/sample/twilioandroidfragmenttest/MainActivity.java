package co.inamoto.sample.twilioandroidfragmenttest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private final String FRAGMENT = "TWILIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.addTwilioFragmentButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment();
            }
        });

    }

    // add Twilio fragment

    private void addFragment() {

        Fragment f = getFragmentManager().findFragmentByTag(FRAGMENT);
        if (f == null) {
            TwilioFragment twilioFragment = new TwilioFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, twilioFragment, FRAGMENT)
                    .commit();
        }

    }


    //
    // remove Twilio fragment
    //
    // this function includes the 'runOnUiThread' because it could potentially be called
    // from a background thread (e.g. a disconnect event in a Twilio listener)
    //
    public void removeFragment() {

        runOnUiThread(new Runnable() {
            public void run() {
                Fragment f = getFragmentManager().findFragmentByTag(FRAGMENT);
                if (f != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .remove(f)
                            .commit();
                }
            }
        });
    }

}

