package c9.edu.lab411.sensortracking;

import c9.edu.lab411.sensortracking.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
@SuppressLint("DrawAllocation")
public class DrawMap extends View{
Database database;
	public DrawMap(Context context) {
		super(context);		
		database = new Database(context);
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
		int count = database.getSensorCount();
		while(count>0){
			canvas.drawBitmap(myBitmap,100*count, 100*count, null);
			count--;
		}	
	}

}
