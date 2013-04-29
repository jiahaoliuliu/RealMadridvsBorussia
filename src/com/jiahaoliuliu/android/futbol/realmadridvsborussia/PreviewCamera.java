package com.jiahaoliuliu.android.futbol.realmadridvsborussia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PreviewCamera extends Activity {

    private static final String LOG_TAG = PreviewCamera.class.getSimpleName();

    private RMadridvsBorussia.team team;
    private String teamName;
    
    private LikeUnlike.likeble likeble;
    private String likebleMessage;

    private TextView simpleMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.preview_camera);

        // Lock the screen to landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get the extra content
        Intent startedIntent = getIntent();

        // Get the team
        int teamInt = startedIntent.getIntExtra(RMadridvsBorussia.TEAM_NAME, -1);

        if (teamInt >= 0 && teamInt < RMadridvsBorussia.team.values().length) {
            team = RMadridvsBorussia.team.values()[teamInt];
            Log.v(LOG_TAG, "The team is " + team);
        } else {
        	Log.w(LOG_TAG, "Team not found!");
        }

        // Get the likeble
        int likebleInt = startedIntent.getIntExtra(LikeUnlike.LIKE_UNLIKE_NAME, -1);

        if (likebleInt >= 0 && likebleInt < LikeUnlike.likeble.values().length) {
        	likeble = LikeUnlike.likeble.values()[likebleInt];
        	Log.v(LOG_TAG, "The likabe is " + likeble);
        } else {
        	Log.w(LOG_TAG, "Likable not found!");
        }

        if (team == RMadridvsBorussia.team.BORUSSIA) {
        	teamName = "Borussia dortmund"; 
        } else if (team == RMadridvsBorussia.team.REAL_MADRID) {
        	teamName = "Real Madrid";
        }

        if (likeble == LikeUnlike.likeble.LIKE) {
        	likebleMessage = "Te gusta";
        } else if (likeble == LikeUnlike.likeble.UNLIKE) {
        	likebleMessage = "No te gusta";
        }

        // output the message
        if (teamName != null && likebleMessage != null) {
        	simpleMessage = (TextView)findViewById(R.id.simpleMessage);
        	simpleMessage.setText(likebleMessage + " " + teamName);
        }
    }
}
