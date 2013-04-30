package com.jiahaoliuliu.android.futbol.realmadridvsborussia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LikeUnlike extends Activity {

    private static final String LOG_TAG = LikeUnlike.class.getSimpleName();
    private Context context;

    public static final String LIKE_UNLIKE_NAME = "likeble";
    public enum likeble {
    	LIKE, UNLIKE
    }

    private Button likeButton;
    private Button unlikeButton;

    private int teamInt = -1;

    Intent photoGalleryIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.like_unlike);
        context = this;
        
        photoGalleryIntent = new Intent(this, PhotoGallery.class);

        // Get the extra content
        Intent startedIntent = getIntent();
        teamInt = startedIntent.getIntExtra(RMadridvsBorussia.TEAM_NAME, -1);
        Log.v(LOG_TAG, "The team int is " + teamInt);
        photoGalleryIntent.putExtra(RMadridvsBorussia.TEAM_NAME, teamInt);

        // Lock the screen to landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        likeButton = (Button)findViewById(R.id.button_like);
        likeButton.setOnClickListener(onClickListener);

        unlikeButton = (Button)findViewById(R.id.button_unlike);
        unlikeButton.setOnClickListener(onClickListener);
        
}
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
	            case R.id.button_like:
	            	Log.v(LOG_TAG, "Like button pressed");
	            	photoGalleryIntent.putExtra(LIKE_UNLIKE_NAME, likeble.LIKE.ordinal());
	                break;
	            case R.id.button_unlike:
	            	Log.v(LOG_TAG, "Unlike button pressed");
	            	photoGalleryIntent.putExtra(LIKE_UNLIKE_NAME, likeble.UNLIKE.ordinal());
	                break;
	            default:
	                Log.w(LOG_TAG, "Button not recognized " + v.getId());
	                break;
	            }
            context.startActivity(photoGalleryIntent);
        }
    };
}

