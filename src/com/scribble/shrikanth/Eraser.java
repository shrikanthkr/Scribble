package com.scribble.shrikanth;

import java.util.ArrayList;

import android.graphics.Path;

public class Eraser extends Shape{
private Path _erasegraphicspath;
int stroke;
int color;
	
	public Eraser(){
		super();
		_erasegraphicspath = new Path();
		
	}
	


	public void setEraserPath(Path temp) {
		// TODO Auto-generated method stub
		_erasegraphicspath=temp;
		
	}



	public Path getEraserPath() {
		// TODO Auto-generated method stub
		return _erasegraphicspath;
	}



	public int getColor() {
		// TODO Auto-generated method stub
		return color;
	}

}
