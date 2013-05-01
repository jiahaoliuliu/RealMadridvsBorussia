package com.jiahaoliuliu.android.futbol.realmadridvsborussia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PhotoGallery extends Activity {
	
    private static final String LOG_TAG = PhotoGallery.class.getSimpleName();
    private Context context;
    
    private RMadridvsBorussia.team team;
    private String teamName;
    
    private LikeUnlike.likeble likeble;
    private String likebleMessage;
    
    public static final String TOP_PHOTO_ID_NAME = "topPhoto";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        context = this;
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

        setContentView(R.layout.gallery);

        Gallery g = (Gallery) findViewById(R.id.gallery);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        g.setAdapter(imageAdapter);
 
        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(@SuppressWarnings("rawtypes") AdapterView parent, View v, int position, long id) {
            	// Starting the camera preview activity
            	Intent startPreviewCameraIntent = new Intent(context, PreviewCamera.class);
            	startPreviewCameraIntent.putExtra(TOP_PHOTO_ID_NAME, id);
            	context.startActivity(startPreviewCameraIntent);
            }
        });

    }
 
public class ImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;
 
    private List<Integer>mImageIds;
 
    public ImageAdapter(Context c) {
        mContext = c;
        TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        a.recycle();
        
        mImageIds = new ArrayList<Integer>();

        if (team == RMadridvsBorussia.team.BORUSSIA) {
            if (likeble == LikeUnlike.likeble.LIKE) {
            	// Borussia like
            	mImageIds.add(R.drawable.bp1);
            	mImageIds.add(R.drawable.bp2);
            	mImageIds.add(R.drawable.bp3);
            	mImageIds.add(R.drawable.bp4);
            	mImageIds.add(R.drawable.bp5);
            	
            } else if (likeble == LikeUnlike.likeble.UNLIKE) {
            	// Borussia unlike
            	mImageIds.add(R.drawable.bn1);
            	mImageIds.add(R.drawable.bn2);
            	mImageIds.add(R.drawable.bn3);
            	mImageIds.add(R.drawable.bn4);
            }

        } else if (team == RMadridvsBorussia.team.REAL_MADRID) {
            if (likeble == LikeUnlike.likeble.LIKE) {
            	// R.Madrid like
            	mImageIds.add(R.drawable.rmp1);
            	mImageIds.add(R.drawable.rmp2);
            	mImageIds.add(R.drawable.rmp3);
            	mImageIds.add(R.drawable.rmp4);
            	mImageIds.add(R.drawable.rmp5);

            } else if (likeble == LikeUnlike.likeble.UNLIKE) {
            	// R.Madrid unlike
            	mImageIds.add(R.drawable.rmn1);
            	mImageIds.add(R.drawable.rmn2);
            	mImageIds.add(R.drawable.rmn3);
            	mImageIds.add(R.drawable.rmn4);
            	mImageIds.add(R.drawable.rmn5);
            }
        }
    }
 
    public int getCount() {
        return mImageIds.size();
    }
 
    public Object getItem(int position) {
        return mImageIds.get(position);
    }
 
    public long getItemId(int position) {
        return mImageIds.get(position);
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView i = new ImageView(mContext);
 
        Log.v(LOG_TAG, "Getting position " + position);
	        i.setImageResource(mImageIds.get(position));
	        i.setLayoutParams(new Gallery.LayoutParams(900, 600));
	        i.setScaleType(ImageView.ScaleType.FIT_XY);
	        i.setBackgroundResource(mGalleryItemBackground);
        return i;
    }
}
}