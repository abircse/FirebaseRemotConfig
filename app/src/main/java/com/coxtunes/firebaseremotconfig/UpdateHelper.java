package com.coxtunes.firebaseremotconfig;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper  {

    public static String KEY_UPDATE_ENABLE = "is_Update";
    public static String KEY_UPDATE_VERSION= "version";
    public static String KEY_UPDATE_URL = "https://play.google.com/store/apps/details?id=com.wordpress.studioabir.flashlight";

    public interface OnUpdateCheckListener
    {
        void onUpdateCheckListener(String urlApp);
    }

    static Builder with(Context context)
    {
        return new Builder(context);
    }

    private OnUpdateCheckListener onUpdateCheckListener;
    private Context context;

    private UpdateHelper(Context context, OnUpdateCheckListener onUpdateCheckListener) {
        this.onUpdateCheckListener = onUpdateCheckListener;
        this.context = context;
    }

    private void check()
    {

        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(KEY_UPDATE_ENABLE))
        {
            String currentVersion = remoteConfig.getString(KEY_UPDATE_VERSION);
            String appVersion = getAppVersion(context);
            String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);

            if (!TextUtils.equals(currentVersion,appVersion) && onUpdateCheckListener != null)
                onUpdateCheckListener.onUpdateCheckListener(updateUrl);
        }

    }

    private String getAppVersion(Context context) {

        String result = "";

        try{

            result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                    .versionName;

            result = result.replaceAll("[a-zA-Z]|-","");


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static class Builder
    {
        private Context context;
        private OnUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context) {
            this.context = context;
        }

    public Builder onUpdateCheack(OnUpdateCheckListener onUpdateCheckListener)
    {
        this.onUpdateCheckListener = onUpdateCheckListener;
        return this;

    }


    public UpdateHelper build()
    {
        return new UpdateHelper(context,onUpdateCheckListener);
    }

    public UpdateHelper check()
    {
        UpdateHelper updateHelper = build();
        updateHelper.check();

        return updateHelper;

    }




    }

}
