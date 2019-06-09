package com.coxtunes.firebaseremotconfig;

import android.app.Application;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        Map<String, Object> defaultValue = new HashMap<>();
        defaultValue.put(UpdateHelper.KEY_UPDATE_ENABLE,false);
        defaultValue.put(UpdateHelper.KEY_UPDATE_VERSION,"1.0");
        defaultValue.put(UpdateHelper.KEY_UPDATE_URL,"https://play.google.com/store/apps/details?id=com.wordpress.studioabir.flashlight");

        remoteConfig.setDefaults(defaultValue);
        //------10 is second to fetch data from firebase, best is use in 1 min--///
        remoteConfig.fetch(5).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {

                remoteConfig.activateFetched();

            }
        });

    }
}
