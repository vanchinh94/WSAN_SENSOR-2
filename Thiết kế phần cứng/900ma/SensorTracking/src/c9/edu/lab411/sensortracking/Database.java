package c9.edu.lab411.sensortracking;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class Database extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "sensor";
	private static final String TABLE_MAP = "Map";
	private static final String NODE = "node";
	private static final String ID = "id";
	private static final String CREATE_TABLE_MAP = "CREATE TABLE "
			+ TABLE_MAP + "(" + ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + NODE
			+ " INTEGER NOT NULL" + ")";
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_MAP);	
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("DROP TABLE IF EXISTS " +TABLE_MAP );
			onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	public int addMap(Map map){
		int result = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NODE, map.getNode());
		String msg;
		if (db.insert(TABLE_MAP, null, values) == -1) {
			msg = "Error Insert ";
			result = 0;
		} else {
			msg = "Success Insert";
			result = 1;
		}
		Log.i("SQL", msg);
		db.close();
		return result;
	}
	public Map getMap(){
		String selectQuery = "SELECT * FROM " + TABLE_MAP;
		Map map = new Map();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor !=null){
			cursor.moveToFirst();
		}
		do{
			map.setNode(cursor.getInt(cursor.getColumnIndex(NODE)));
		}while(cursor.moveToNext());
		cursor.close();
		db.close();
		return map;
	}
	public int getSensorCount(){
		String selectQuery = "SELECT * FROM " + TABLE_MAP;
		int result = 0;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor != null) {
			cursor.moveToFirst();
			result = cursor.getCount();
		}
		cursor.close();
		db.close();
		return result;
	}
}
