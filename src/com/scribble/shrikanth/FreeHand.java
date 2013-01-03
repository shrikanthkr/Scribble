package com.scribble.shrikanth;

import java.util.ArrayList;

import android.graphics.Path;

public class FreeHand extends Shape{

	private Path _graphics;
	int stroke;
	int color;
	public FreeHand(){
		super();
		 _graphics = new Path();
	}
	
	public Path getFreeHandPath(){
		return _graphics;
		
	}
	
	public Path getGraphicsPath(){
		return _graphics;
	}
	
	public void setStrokeWidthFreeHand(int i){
		stroke=i;
		
	}
	public int  getStrokeWidthFreeHand(){
	
		return stroke;
	}

	public void setFreePath(Path temp) {
		// TODO Auto-generated method stub
		_graphics=temp;
		
	}

	public int getColor() {
		// TODO Auto-generated method stub
		return color;
	}
}

