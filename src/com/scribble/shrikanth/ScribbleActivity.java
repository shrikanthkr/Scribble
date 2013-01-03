package com.scribble.shrikanth;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ScribbleActivity extends Activity implements OnClickListener,OnLongClickListener{
	
    DrawLine drawLine;
    LinearLayout ll;
    Canvas canvas;
    Paint paint = new Paint();
    Button pencilbutton;
    Button brushbutton;
    Button eraserbutton;
    Button colourbutton;
    
    
    
    
    Shape currentObject;
    int currentSelectedShape=1;
    int Freehand=1,eraser=10;
    int colourVariable=Color.BLACK;
    
    private ArrayList<Shape> graphicobjects;
    int brushWidth=2;
    int brushSizeRetainer=6;
    int graphicObjectCount=0; 
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       // Bitmap result = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888);
         canvas=new Canvas();
        ll=(LinearLayout)findViewById(R.id.ll);
        pencilbutton=(Button)findViewById(R.id.pencilbutton);
        brushbutton=(Button)findViewById(R.id.brushbutton);
        eraserbutton=(Button)findViewById(R.id.eraserbutton);
        colourbutton=(Button)findViewById(R.id.colourbutton);
        pencilbutton.setOnClickListener(this);
        brushbutton.setOnClickListener(this);
        eraserbutton.setOnClickListener(this);
        colourbutton.setOnClickListener(this);
        brushbutton.setOnLongClickListener(this);
        graphicobjects=new ArrayList<Shape>();
        eraserbutton.setOnLongClickListener(this);
        drawLine = new DrawLine(this);
        drawLine.draw(canvas);
        drawLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        drawLine.setBackgroundColor(Color.WHITE);
       ll.addView(drawLine);
        
      
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		MenuInflater inflate=getMenuInflater();
		inflate.inflate(R.menu.menu, menu);
		
		
	
		return true;
	}
    
    
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub

    	switch(item.getItemId()){
    	
    	
    	
    	
    		
    	
    	case R.id.clear:
    		graphicobjects.clear();
    		Intent i=new Intent(this.getApplicationContext(),ScribbleActivity.class);
    		startActivity(i);
    		break;
    	
    	
    	}
    
    	
		return super.onMenuItemSelected(featureId, item);
    	
	}

    
    



    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}






	public class DrawLine extends View implements OnTouchListener {
    	
        Paint paint = new Paint();
        Path pencilPath=new Path();
        Path erasePath=new Path();
        int flag,restore=0,eraseFlag=0;
      
        Button b;
    	public DrawLine(Context context) {
    		// TODO Auto-generated constructor stub
    		
    		super(context);
    		setFocusable(true);
            setFocusableInTouchMode(true);
            this.setOnTouchListener(this);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
    		paint.setStrokeWidth(2);
    		paint.setStyle(Paint.Style.STROKE);  
    		paint.setStrokeJoin(Paint.Join.ROUND);  
    		paint.setStrokeCap(Paint.Cap.ROUND);
    		
            
            
    	}
  

    	Path temp;

    	public boolean onTouch(View arg0, MotionEvent event) {
    		
    		
    	
    		if(event.getAction()==MotionEvent.ACTION_DOWN){
    			
    			
    				if(currentSelectedShape==1){
    				currentObject=new FreeHand();
    				((FreeHand)currentObject).color=colourVariable;
    				
    				((FreeHand)currentObject).stroke=brushWidth;
    				((FreeHand) currentObject).setStrokeWidthFreeHand(brushWidth);
    				temp=currentObject.getPath();
    				temp.moveTo(event.getX(), event.getY());
    				}
    				else if(currentSelectedShape==10){
    					currentObject=new Eraser();
    					((Eraser)currentObject).color=colourVariable;
    					((Eraser)currentObject).stroke=brushWidth;
        				
        				temp=currentObject.getPath();
        				temp.moveTo(event.getX(), event.getY());
        				//Toast.makeText(getApplicationContext(), "Eraser", 1).show();
    					
    				}
    				
    				
    			}
    			else if(event.getAction()==MotionEvent.ACTION_MOVE){
    				temp=currentObject.getPath();
    				temp.lineTo(event.getX(), event.getY());
    				
    			}
    		
    			else{
    				if(currentSelectedShape==1){
    				temp=currentObject.getPath();
    				((FreeHand)currentObject).setFreePath(temp);
    				graphicobjects.add((FreeHand)currentObject);
    				}
    				else if(currentSelectedShape==10){
    					temp=currentObject.getPath();
        				((Eraser)currentObject).setEraserPath(temp);
        				graphicobjects.add((Eraser)currentObject);
    					
    				}
    				
    			}

    	        invalidate();
    			
    		return true;
    	}
    	@Override
    	protected void onDraw(Canvas canvas) {
    		// TODO Auto-generated method stub
    		
        			
        	
        try{		
        	for(Shape s :graphicobjects){
        		if(s instanceof FreeHand){
        			paint.setColor(((FreeHand)s).getColor());
        			paint.setStrokeWidth(((FreeHand)s).getStrokeWidthFreeHand());
        			canvas.drawPath(((FreeHand)s).getFreeHandPath(), paint);
        		}
        		else if(s instanceof Eraser)
        		{
        			paint.setColor(Color.WHITE);
        			paint.setStrokeWidth(((Eraser)s).stroke);
        			canvas.drawPath(((Eraser)s).getEraserPath(), paint);
        		}
        		
        }
        	
        		sideDraw(canvas);
        
        	
    		 
        }catch(Exception e){}
    		
      
    		super.onDraw(canvas);
    	}
		private void sideDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			if(currentObject instanceof FreeHand){
				paint.setColor(((FreeHand)currentObject).getColor());
				paint.setStrokeWidth(((FreeHand)currentObject).getStrokeWidthFreeHand());
			
			}
			else if(currentObject instanceof Eraser) {
				paint.setStrokeWidth(((Eraser)currentObject).stroke);
				paint.setColor(Color.WHITE);
			}
			canvas.drawPath(temp, paint);
		}
    	
    	

    }






	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.pencilbutton :
			currentSelectedShape=1;
    		brushWidth=2;
			break;
		case R.id.brushbutton :
			currentSelectedShape=1;
    		brushWidth=brushSizeRetainer;
			
			break;
		case R.id.colourbutton :
			
			
			final CharSequence[] items = {"Red", "Green", "Blue","Black","Gray","LGray","Magenta","Yellow"};
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("Pick a color");
        	builder.setItems(items, new DialogInterface.OnClickListener() {
        	    public void onClick(DialogInterface dialog, int item) {
        	    	switch(item+1){
        	    	case 1:
        	    		colourVariable=Color.RED;
        	    		break;
        	    	case 2:
        	    		colourVariable=Color.GREEN;
        	    		break;
        	    	case 3:
        	    		colourVariable=Color.BLUE;
        	    		break;
        	    	case 4:
        	    		colourVariable=Color.BLACK;
        	    		break;
        	    	case 5:
        	    		colourVariable=Color.GRAY;
        	    		break;
        	    	case 6:
        	    		colourVariable=Color.LTGRAY;
        	    		break;
        	    	case 7:
        	    		colourVariable=Color.MAGENTA;
        	    		break;
        	    	case 8:
        	    		colourVariable=Color.YELLOW;
        	    		break;
        	    		
        	    	}
        	        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
        	    }
        	});
        	
        	builder.show();
			
			break;
		case R.id.eraserbutton :
			brushWidth=20;
	    	currentSelectedShape=10;
			break;
		}
		
	}
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		
		if(arg0.getId()==R.id.brushbutton){
			currentSelectedShape=1;
			final CharSequence[] sizes = {"2","4","6","8","10"};
			builder1.setTitle("Pick Required Size");
			
			builder1.setItems(sizes, new DialogInterface.OnClickListener() {
	    	    public void onClick(DialogInterface dialog, int item) {
	    	    	switch(item+1){
	    	    	case 1:
	    	    		brushWidth=4;
	    	    		break;
	    	    	case 2:
	    	    		brushWidth=8;
	    	    		break;
	    	    	case 3:
	    	    		brushWidth=12;
	    	    		break;
	    	    	case 4:
	    	    		brushWidth=16;
	    	    		break;
	    	    	case 5:
	    	    		brushWidth=20;
	    	    		break;
	    	    		
	    	    	}
	    	    	brushSizeRetainer=brushWidth;
	    	        Toast.makeText(getApplicationContext(), sizes[item] +" Width " +brushWidth, Toast.LENGTH_SHORT).show();
	    	    }
	    	});
			builder1.show();
		}
		else{
			currentSelectedShape=10;
			final CharSequence[] sizes = {"2","4","6","8","10"};
			builder1.setTitle("Pick Required Size");
			
			builder1.setItems(sizes, new DialogInterface.OnClickListener() {
	    	    public void onClick(DialogInterface dialog, int item) {
	    	    	switch(item+1){
	    	    	case 1:
	    	    		brushWidth=5;
	    	    		break;
	    	    	case 2:
	    	    		brushWidth=8;
	    	    		break;
	    	    	case 3:
	    	    		brushWidth=10;
	    	    		break;
	    	    	case 4:
	    	    		brushWidth=15;
	    	    		break;
	    	    	case 5:
	    	    		brushWidth=30;
	    	    		break;
	    	    		
	    	    	}
	    	    	
	    	        Toast.makeText(getApplicationContext(), sizes[item] +" Width " +brushWidth, Toast.LENGTH_SHORT).show();
	    	    }
	    	});
			builder1.show();
		}
		
		return false;
	}
    



}