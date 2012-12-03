package SolarSystem.pn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.graphics.Color;



import android.util.Log;



public class SolarActivity extends Activity {
    
	
	

	 private static final String TAG = SolarActivity.class.getSimpleName();
	 	 
	   
	     double    ey = 00.0000 ;
	     
	     double    ex = 00.0000 ;
	   
	     double    theta = 00.0000 ;
	     
	     
	     int    degrees = 1 ;
		   
	     int    edegrees = 1 ;
	     
	     int    marsdegrees = 1 ;
			
			
	     double    earthcx = 160;
	     
	     double    earthcy = 160;
	     
	     int    earthrx = 140;
	     
	     int    earthry = 170;
	     
	     
			
	     double   marscx = 160;
	     
	     double    marscy = 160;
	     
	     int    marsrx = 70;
	     
	     int    marsry = 60;
	     
	     
	 	
	     double    emooncx = 160;
	     
	     double    emooncy = 160;
	     
	     int    emoonrx = 25;
	     
	     int    emoonry = 25;
	     
	    
	     
	     
	     int   centerXi = 160;
	        
	     int    centerYi = 160;
	       
		   double   distanceR = 80.0000;
		   
		   
		   int    rotatefactor = -5;
		   
	     
	     int   xi = centerXi;
	        
	     int    yi = centerYi;
	   
	   
	 

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
       Object pathname = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED;
        
           Log.d(TAG, "oject pathname   "   + pathname);
       
       
             String pathnamex = Environment.getExternalStorageDirectory().getAbsolutePath();

            Log.d(TAG, "string pathnamex  " +  pathnamex  );
             
         
            
      /////      generateNoteOnSD("FILELUIS","test line1");
            
          
        
        // requesting to turn the title OFF
        	        requestWindowFeature(Window.FEATURE_NO_TITLE);
        	        // making it full screen
        	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        	        // set our MainGamePanel as the View
        	        setContentView(new MainGamePanel(this));
        	//        Log.d(TAG, "View added");
        
        //  setContentView(R.layout.main);
    
    }
        	        private void generateNoteOnSD(String sFileName, String sBody) {
        	        	
        	        	{
        	        	    try
        	        	    {
        	        	    	
        	        	    	
        	        	    	
        	        	    	
        	        	        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        	        	        if (!root.exists()) 
        	        	        {
        	        	            root.mkdirs();
        	        	        }

        	        	        File gpxfile = new File(root, sFileName);
        	        	        FileWriter writer = new FileWriter(gpxfile);
        	        	        writer.append(sBody);
        	        	        writer.flush();
        	        	        writer.close();
        	        	        
        	        	        

        	                    Log.d(TAG, "written to  file  of   " + sFileName + sBody );
        	        	        
        	        	        
        	        	        

        	        	       
        	        	    }
        	        	    catch(IOException e)
        	        	    {
        	        	    	

        	                    Log.d(TAG, "error on file   " + sFileName + sBody
        	                    		);
        	                     
        	                 
        	        	    	
        	        	    	
        	        	         e.printStackTrace();
        	        	         String importError = e.getMessage();
        	        	        
        	        	    }	
        	        	
        	        	
        	        	}
        	        	
        	        	
        	        	
        	        	
        	        	
        	        	
        	        	
        	        	
		// TODO Auto-generated method stub
		
	}
					@Override
        	        	    protected void onDestroy() {
        	        	 //       Log.d(TAG, "Destroying...");
        	        	        super.onDestroy();
        	        	    }
    
    
    
    
    public class MainGamePanel extends SurfaceView implements
	SurfaceHolder.Callback {
    	

   	 private final String TAG = MainGamePanel.class.getSimpleName();
   	 
    	private MainThread thread;
    	
    	
    	private Sun sun;
    	
    	private Earth earth;
    	
    	private eMoon emoon;
    	
    	private Mars mars;
    	
             

    public MainGamePanel(Context context) {
	super(context);
	
	
	// adding the callback (this) to the surface holder to intercept events
		
	getHolder().addCallback(this);
	
	
	
	
	
	 // create sun and load bitmap
	    sun = new Sun(BitmapFactory.decodeResource(getResources(), R.drawable.sun), 150, 150);
	 

		 // create earth and load bitmap
		    earth = new Earth(BitmapFactory.decodeResource(getResources(), R.drawable.earth), 75, 75);
		 
		    // create earth and load bitmap
		    mars = new Mars(BitmapFactory.decodeResource(getResources(), R.drawable.mars), 75, 75);
		
		    // create earth and load bitmap
		    emoon = new eMoon(BitmapFactory.decodeResource(getResources(), R.drawable.emoon), 75, 75);
		
		
		    
		    
	  thread = new MainThread(getHolder(), this);
	
		 
	
	
	// make the GamePanel focusable so it can handle events
	setFocusable(true);
}

@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
}

@Override
public void surfaceCreated(SurfaceHolder holder) {
	
	
	
	
	// thread = new MainThread(getHolder(), this);
	// thread = new MainThread(holder, null);
	thread.setRunning(true);
	thread.start();
	
	
	
}

@Override
public void surfaceDestroyed(SurfaceHolder holder) {
//	 Log.d(TAG, "Surface is being destroyed");
	 	        // tell the thread to shut down and wait for it to finish
	 	        // this is a clean shutdown
	 	        boolean retry = true;
	 	        while (retry) {
	 	            try {
	 	                thread.join();
	 	                retry = false;
	 	            } catch (InterruptedException e) {
	 	                // try again shutting down the thread
	 	            }
	 	        }
	 	  //      Log.d(TAG, "Thread was shut down cleanly");
	
	
	
	
	
	
}



public  void circleorbit(int degrees, double cx,  double cy, int rx, int ry   ) { 
	
	        ey = 0;
	        ex = 0;
	       
	                theta = degrees * (Math.PI/180);
	        
			      	 ex = 0;
			      	 ey = 0;

	          if  ( theta <= 360 ) {
	        	  
	        	    ex =  ( cx + rx * Math.cos(theta) );
	        	    
	        	    ey =  ( cy + ry  * Math.sin(theta) );
	        	  
	          }
	             
	  		
}




private void iError() {
	// TODO Auto-generated method stub
	
}

@Override
public boolean onTouchEvent(MotionEvent event) {
	
	
	if (event.getAction() == MotionEvent.ACTION_DOWN) {
		
		
			        if (event.getY() > getHeight() - 50) {
			            thread.setRunning(false);
			            ((Activity)getContext()).finish();
			        }
			        
			        else 
			        
			        
			        {
			//            Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			        }
			    }if (event.getAction() == MotionEvent.ACTION_MOVE) {
			    		            // the gestures
			    		            if (sun.isTouched()) {
			    		                // the sun was picked up and is being dragged
			    		            	
			    		                sun.setX((int)event.getX());
			    		                
			    		                sun.setY((int)event.getY());
			    		               
			    		                
			    		                
			    		            }
			    		        } if (event.getAction() == MotionEvent.ACTION_UP) {
			    		        	
			    		        	
			    		        	
			    		            // touch was released
			    		            if (sun.isTouched()) {
			    		            				    		            	
			    		                sun.setTouched(false);
			    		                
			    		                
			    		                
			    		            }
			    		        }
			    	        return true;
	
	
	
}

@Override
protected void onDraw(Canvas canvas) {
	
	  // fills the canvas with black
		        canvas.drawColor(Color.BLACK);
		        
		        
		        sun.draw(canvas);
		        
		           // work with earth
		         
			   	 	  if  (edegrees >360)  { edegrees = 1; };
			    	    
		            
			   	 	  circleorbit(edegrees, earthcx, earthcy,  earthrx,  earthry);
		            
		            //Ê---
		        
		               xi  = (int) ex; 
		               yi  = (int) ey; 
			       	 	
	                earth.setX(xi);
	                
	                earth.setY(yi);
	                
	                rotatefactor = rotatefactor - 1;
	        
			        earth.draw(canvas);
			        

		               emooncx   =  xi ; 
		               emooncy  =  ey; 
			       	 	
			   	 	  circleorbit(edegrees, emooncx, emooncy,  emoonrx,  emoonry);
		         
		               xi  = (int) ex; 
		               yi  = (int) ey; 
			       
                    emoon.setX(xi );
	                
	                emoon.setY(yi );
	                
	                rotatefactor = rotatefactor - 5;
	        
			        emoon.draw(canvas);
			       
			        
	            	edegrees = edegrees +  1 ;
	            
			        
			       //  work with mars 
			        

			   	 	  if  (marsdegrees >360)  { marsdegrees = 1; };
		            
			          
			   	 	  circleorbit(marsdegrees, marscx, marscy,  marsrx,  marsry);
		            
		               xi  = (int) ex; 
		               
		               yi  = (int) ey;   
		              
			        
	                mars.setX(xi);
	                
	                mars.setY(yi);
	                
	                rotatefactor = rotatefactor - 7;
	        
			        mars.draw(canvas);
			      
		            marsdegrees = marsdegrees +  1 ;
		            	
		        
		

}
	
	
}

    
    
    

    public class MainThread extends Thread {
    	
 	
    	private  final String TAG = MainThread.class.getSimpleName();
    	 

        private SurfaceHolder surfaceHolder;
        private MainGamePanel gamePanel;
    	
    	// flag to hold game state
    	private boolean running;
    	public void setRunning(boolean running) {
    		this.running = running;
    	}
     
    	
    	
    	
        
        public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
    			    super();
    			    this.surfaceHolder = surfaceHolder;
    			    this.gamePanel = gamePanel;
    			}
    	
    	
    	

    

    	@Override
    	public void run() {
    		
    		
    		
    		
    		 Canvas canvas;
    		 	//        Log.d(TAG, "Starting game loop");
    		 	        while (running) {
    		 	            canvas = null;
    		 	            // try locking the canvas for exclusive pixel editing on the surface
    		 	            try {
    		 	                canvas = this.surfaceHolder.lockCanvas();
    		 	                synchronized (surfaceHolder) {
    		 	                    // update game state
    		 	                    // draws the canvas on the panel
    		 	                    this.gamePanel.onDraw(canvas);
    		 	                }
    		 	            } finally {
    		 	                // in case of an exception the surface is not left in
    		 	                // an inconsistent state
    		 	                if (canvas != null) {
    		 	                    surfaceHolder.unlockCanvasAndPost(canvas);
    		 	                }
    		 	            }   // end finally
    		 	        }
    		
    	}
    }

    

	
	
	
    
    // ---------------------
    
    public class Sun {
    		 
    		    private Bitmap bitmap;  // the actual bitmap
    		    private int x;          // the X coordinate
    		    private int y;          // the Y coordinate
				private boolean touched;
    		 
    		    public Sun(Bitmap bitmap, int x, int y) {
    		        this.bitmap = bitmap;
    		        this.x = x;
    		        this.y = y;
    		    }
    		 
    		    public Bitmap getBitmap() {
    		        return bitmap;
    		    }
    		    public void setBitmap(Bitmap bitmap) {
    		        this.bitmap = bitmap;
    		    }
    		    public int getX() {
    		        return x;
    		    }
    		    public void setX(int x) {
    		        this.x = x;
    		    }
    		    public int getY() {
    		        return y;
    		    }
    		    public void setY(int y) {
    		        this.y = y;
    		   
    		    }
    		    
    		    
    		    
    		    public boolean isTouched() {
    		    	
    		    	// check
    		    	
    		    		        return true;
    		    		    }
    		    		 
    		    
    		    public void setTouched(boolean touched) {
    		    	
    		    	// check
    		    	
    		    		        this.touched = touched;
    		    		    }
    		    		 
    		    
    		    public void draw(Canvas canvas) {
    		    		        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    		    		    }
    		    		 
    		    		    public void handleActionDown(int eventX, int eventY) {
    		    		        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
    		    		            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
    		    		                // sun touched
    		    		                setTouched(true);
    		    		            } else {
    		    		                setTouched(false);
    		    		            }
    		    		        } else {
    		    		            setTouched(false);
    		    		        }
    		    		 
    		    		    }    
    		    
    		    
    		    
    		    
    		}  
    
    
    
    //----------    
    
    public class eMoon {
    	
    	
	    // moon
       private Bitmap bitmap;  // the actual bitmap
	    
	    
	    private int x;          // the X coordinate
	    private int y;          // the Y coordinate
    
	   
	 
	    public eMoon(Bitmap bitmap, int x, int y) {
	        this.bitmap = bitmap;
	        this.x = x;
	        this.y = y;
	    }
	 
	    public Bitmap getBitmap() {
	        return bitmap;
	    }
	    public void setBitmap(Bitmap moonbitmap) {
	        this.bitmap = moonbitmap;
	    }
	
	    public void setX(int x) {
	        this.x = x;
	    }

	    public void setY(int y) {
	        this.y = y;
	   
	    }
	    
		

	    
	    
	    public void draw(Canvas canvas) {
	    	// create a matrix object
	    	
	    	Matrix matrix = new Matrix();
	    	
    	matrix.postRotate(rotatefactor); // anti-clockwise by 90 degrees
	    	
	    	// create a new bitmap from the original using the matrix to transform the result
	    
	    	Bitmap rBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
   	 	
	    	
	    	canvas.drawBitmap(rBitmap, x - (rBitmap.getWidth() / 2), y - (rBitmap.getHeight() / 2), null);
	   
	    
	    }
	    	
    	
    }
    	
    
  
    
    
    public class Earth {
    	
    
    		    // earth
    		    private Bitmap bitmap;  // the actual bitmap
    		    
    		    
    		    private int x;          // the X coordinate
    		    
    		    
    		    private int y;          // the Y coordinate
    		    
    		  
    		    
    		    public Earth(Bitmap bitmap, int x, int y) {
    		        this.bitmap = bitmap;
    		        this.x = x;
    		        this.y = y;
    		    }
    		 
    		    public Bitmap getBitmap() {
    		        return bitmap;
    		    }
    		    public void setBitmap(Bitmap bitmap) {
    		        this.bitmap = bitmap;
    		    }
    		    public int getX() {
    		        return x;
    		    }
    		    public void setX(int x) {
    		        this.x = x;
    		    }
    		    
    		    
    		    public int getY() {
    		        return y;
    		    }
    		    
    		    
    		    public void setY(int y) {
    		        this.y = y;
    		   
    		    }
    		    
    		    
    		    
    		    public void draw(Canvas canvas) {
    		    	// create a matrix object
    		    	
    		    	Matrix matrix = new Matrix();
    		    	
    	    	matrix.postRotate(rotatefactor); // anti-clockwise by 90 degrees
    		    	
    		    	// create a new bitmap from the original using the matrix to transform the result
    		    
    		    	Bitmap rBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
    		    
    		    	// display the rotated bitmap
    		    	
    	///	    	Object img1;
    		    	
			////		((Object) img1).setImageBitmap(rotatedBitmap);

    		    
    		    	
    		////////////   	  canvas.drawBitmap(rotatedBitmap, x, y, null); 
    		    	
    		    
    		    	
    		    	
    		    	canvas.drawBitmap(rBitmap, x - (rBitmap.getWidth() / 2), y - (rBitmap.getHeight() / 2), null);
    		    		 
    		    
    		    
    		    }
    		    	
    		    			
    		    		    
    		}  
    

    //---
    
    
    public class Mars {
    		 
    		    private Bitmap bitmap;  // the actual bitmap
    		    
    		    
    		    private int x;          // the X coordinate
    		 
    		    
    		    
    		    private int y;          // the Y coordinate
    		    
    		    
    		    
				private boolean touched;
    		 
    		    public Mars(Bitmap bitmap, int x, int y) {
    		        this.bitmap = bitmap;
    		        this.x = x;
    		        this.y = y;
    		    }
    		 
    		    public Bitmap getBitmap() {
    		        return bitmap;
    		    }
    		    public void setBitmap(Bitmap bitmap) {
    		        this.bitmap = bitmap;
    		    }
    		    public int getX() {
    		        return x;
    		    }
    		    public void setX(int x) {
    		        this.x = x;
    		    }
    		    
    		    
    		    public int getY() {
    		        return y;
    		    }
    		    
    		    
    		    public void setY(int y) {
    		        this.y = y;
    		   
    		    }
    		    
    		 
    	
    		    
    		    public void draw(Canvas canvas) {
    		    	
    		    	
    		    	Matrix matrix = new Matrix();
    		    	
    		    	matrix.postRotate(rotatefactor); // anti-clockwise by 90 degrees
    			    	
    			    	// create a new bitmap from the original using the matrix to transform the result
    			    
    			    	Bitmap rBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
    		   	 	
    			    	
    			    	canvas.drawBitmap(rBitmap, x - (rBitmap.getWidth() / 2), y - (rBitmap.getHeight() / 2), null);
    			   	    	
    		    
    		  	
    		///////  	    canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    		    		 
    		    
    		    
    		    }
    		    		
    		    
    		    		
    		    		    
    		}  
    
    
    
    
    
}



