package com.jiahaoliuliu.android.futbol.realmadridvsborussia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RMadridvsBorussia extends Activity {

    private static final String LOG_TAG = RMadridvsBorussia.class.getSimpleName();
    private Context context;

    public static final String TEAM_NAME = "teamName";
    public enum team {
    	REAL_MADRID, BORUSSIA
    }
    private Button realMadridButton;
    private Button borussiaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rmadridvsborussia);
        context = this;

        // Lock the screen to landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        realMadridButton = (Button)findViewById(R.id.button_real);
		realMadridButton.setOnClickListener(onClickListener);

        borussiaButton = (Button)findViewById(R.id.button_borussia);
        borussiaButton.setOnClickListener(onClickListener);
        
}
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            Intent checkLikableIntent = new Intent(context, LikeUnlike.class);

            switch (v.getId()) {
	            case R.id.button_real:
	            	Log.v(LOG_TAG, "Real Madrid button pressed");
	            	checkLikableIntent.putExtra(TEAM_NAME, team.REAL_MADRID.ordinal());
	                break;
	            case R.id.button_borussia:
	            	Log.v(LOG_TAG, "Borussia button pressed");
	            	checkLikableIntent.putExtra(TEAM_NAME, team.BORUSSIA.ordinal());
	                break;
	            default:
	                Log.w(LOG_TAG, "Button not recognized " + v.getId());
	                break;
	            }
            context.startActivity(checkLikableIntent);
        }
    };
}

