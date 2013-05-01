package com.jiahaoliuliu.android.futbol.realmadridvsborussia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PreviewCamera extends Activity {

    private static final String LOG_TAG = PreviewCamera.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lock the screen to landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get the extra content
        Intent startedIntent = getIntent();

        // Get the photo id
        Long photoIdLong = startedIntent.getLongExtra(PhotoGallery.TOP_PHOTO_ID_NAME, new Long(-1));

        if (photoIdLong > -1) {
            Log.v(LOG_TAG, "The phot id is " + photoIdLong);
        } else {
        	Log.w(LOG_TAG, "PhotoId not found!");
        }

    }
}
