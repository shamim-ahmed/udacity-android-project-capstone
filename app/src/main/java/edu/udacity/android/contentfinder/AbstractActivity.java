package edu.udacity.android.contentfinder;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by shamim on 8/4/16.
 */
public abstract class AbstractActivity extends AppCompatActivity {
    public void loadAdvertisement() {
        AdView adView = (AdView) findViewById(R.id.adView);

        if (BuildConfig.FLAVOR.equals("paid")) {
            adView.setVisibility(View.GONE);
        } else {
            String deviceId = getString(R.string.device_id);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice(deviceId).build();
            adView.loadAd(adRequest);
        }
    }
}
