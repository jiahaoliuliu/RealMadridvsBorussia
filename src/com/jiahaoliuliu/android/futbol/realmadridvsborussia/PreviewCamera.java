package com.jiahaoliuliu.android.futbol.realmadridvsborussia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.ProgramVertexFixedFunction.Constants;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewCamera extends Activity implements OnClickListener{
	
	private static final String LOG_TAG = PreviewCamera.class.getSimpleName();
    private Bitmap bitmap;
    private Preview mPreview;
    private Long topPhotoLong;
    
    /** Called when the activity is first created. */ 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
          super.onCreate(savedInstanceState); 
          requestWindowFeature(Window.FEATURE_NO_TITLE);
          
          // Get the intent
          Intent startedIntent = getIntent();

          // Get the team
          topPhotoLong = startedIntent.getLongExtra(PhotoGallery.TOP_PHOTO_ID_NAME, new Long(-1));

          if (topPhotoLong > -1) {
        	  Log.v(LOG_TAG, "The photoId is " + topPhotoLong);
              bitmap = BitmapFactory.decodeResource(getResources(), topPhotoLong.intValue());

          } else {
        	  Log.w(LOG_TAG, "Photo id not found");
          }

          FrameLayout Game = new FrameLayout(this);
          mPreview = new Preview(this); 
          DrawOnTop mDraw = new DrawOnTop(this);
          LinearLayout GameWidgets = new LinearLayout (this);
          
          Button EndGameButton = new Button(this);
          TextView MyText = new TextView(this);

          EndGameButton.setWidth(1200);
          EndGameButton.setText("Take photo");
          
          GameWidgets.addView(EndGameButton);

          Game.addView(mPreview);
          Game.addView(GameWidgets);
          
          setContentView(Game);
          addContentView(mDraw, new LayoutParams (LayoutParams.WRAP_CONTENT,
          LayoutParams.WRAP_CONTENT)); 
          
          EndGameButton.setOnClickListener(this);
          // Lock the screen to landscape
          this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
          
   } 

   class DrawOnTop extends View { 
       public DrawOnTop(Context context) { 
           super(context); 

   } 

   @Override 
   protected void onDraw(Canvas canvas) {
	   if (bitmap != null) {
		   canvas.drawBitmap(bitmap, 0, 0, null);
	   }
       super.onDraw(canvas); 
   } 
} 

   public void onClick(View v) {
	   Log.v(LOG_TAG, "Taking picture");
	   mPreview.takePicture();
   }
   
class Preview extends SurfaceView implements SurfaceHolder.Callback { 
   SurfaceHolder mHolder; 
   Camera mCamera; 

   Preview(Context context) { 
       super(context); 
       // Install a SurfaceHolder.Callback so we get notified when the 
       // underlying surface is created and destroyed. 
       mHolder = getHolder(); 
       mHolder.addCallback(this); 
       mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
   } 

   public void surfaceCreated(SurfaceHolder holder) { 
       // The Surface has been created, acquire the camera and tell it where 
       // to draw. 
       mCamera = Camera.open();
        try {
        	mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
        	e.printStackTrace();
        }
   }

   public void surfaceDestroyed(SurfaceHolder holder) { 
      // Surface will be destroyed when we return, so stop the preview. 
      // Because the CameraDevice object is not a shared resource, it's very 
      // important to release it when the activity is paused. 
      mCamera.stopPreview(); 
      mCamera = null; 
   }
   
   public void takePicture() {
	   mCamera.takePicture(null, null, new PhotoHandler(getApplicationContext()));
   }

   public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { 
       // Now that the size is known, set up the camera parameters and begin 
       // the preview. 
       Camera.Parameters parameters = mCamera.getParameters(); 
       parameters.setPreviewSize(w, h); 
       //mCamera.setParameters(parameters); 
       mCamera.startPreview(); 
  } 
} 
public class PhotoHandler implements PictureCallback {

    private final Context context;

    public PhotoHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        File pictureFileDir =  new File(sdDir, "/CameraAPIDemo");

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Log.d(LOG_TAG, "Can't create directory to save image.");
            Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;

        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "Picture_" + date + ".jpg";

        String filename = pictureFileDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast.makeText(context, "New Image saved:" + photoFile,
                    Toast.LENGTH_LONG).show();
            
            // TODO: Merge the photo
            try {
                Bitmap bottomImage = BitmapFactory.decodeFile(pictureFile.getAbsolutePath()); //blue

                bitmap = Bitmap.createBitmap(bottomImage.getWidth(), bottomImage.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                Resources res = getResources();

                Bitmap topImage = BitmapFactory.decodeResource(res, topPhotoLong.intValue()); //green
                Drawable drawable1 = new BitmapDrawable(bottomImage);
                Drawable drawable2 = new BitmapDrawable(topImage);


                drawable1.setBounds(0, 0, bottomImage.getWidth(), bottomImage.getHeight());
                drawable2.setBounds(0, 0, bottomImage.getWidth(), bottomImage.getHeight());
                drawable1.draw(c);
                drawable2.draw(c);


            } catch (Exception e) {
            }
            // To write the file out to the SDCard:
            OutputStream os = null;
            try {
                os = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, os);
            } catch(IOException e) {
                e.printStackTrace();
            }
        } catch (Exception error) {
            Log.d(LOG_TAG, "File" + filename + "not saved: "
                    + error.getMessage());
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
}