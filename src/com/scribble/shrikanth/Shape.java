package com.scribble.shrikanth;

import android.graphics.Path;

public class Shape extends Object{

	
	private int[] color_rgb;
	protected Path path_of_shape;
	protected float strokeWidth;
	
	public Shape(){
		//paint = new Paint();
		path_of_shape = new Path();
		
		
	}
	
	
	public Path getPath(){
		return path_of_shape;
	}
	
	public void setPath(Path p){
		path_of_shape = p;
	}
	
	
	
	public int[] getrgb(){
		return color_rgb;
	}
	
	public void setStrokeWidth(float w){
		strokeWidth = w;
	}
	
	public float getStrokeWidth(){
		return strokeWidth;
	}
}
