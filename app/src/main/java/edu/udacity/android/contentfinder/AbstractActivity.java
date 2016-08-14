package edu.udacity.android.contentfinder;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.udacity.android.contentfinder.util.Constants;


public abstract class AbstractActivity extends AppCompatActivity {
    private Tracker analyticsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContentFinderApplication application = (ContentFinderApplication) getApplication();
        analyticsTracker = application.getDefaultTracker();
        sendActivityName();
    }

    public void loadAdvertisement() {
        AdView adView = (AdView) findViewById(R.id.adView);
        String deviceId = getString(R.string.device_id);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(deviceId).build();
        adView.loadAd(adRequest);
    }

    public void sendActivityName() {
        String name = getActivityName();
        analyticsTracker.setScreenName(name);
        analyticsTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public String getActivityName() {
        return getClass().getSimpleName();
    }
}
