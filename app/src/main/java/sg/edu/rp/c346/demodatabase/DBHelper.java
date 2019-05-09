package sg.edu.rp.c346.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //Start version with 1
    //Increment by 1 whenever db schema changes
    private static final int DATABASE_VER = 1;
    //Filename of the database
    private static final String DATABASE_NAME = "tasks.db";

    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSq1 = "CREATE TABLE " + TABLE_TASK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE + " TEXT," + COLUMN_DESCRIPTION + " TEXT )";
        db.execSQL(createTableSq1);
        Log.i("info","created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        //Create table(s) again
        onCreate(db);
    }

    public void insertTask(String description, String date){
        //GET AN INSTANCE OF THE DATABASE FOR WRITING
        SQLiteDatabase db = this.getWritableDatabase();
        //USE ContentValues object to store the values for the db operation
        ContentValues values = new ContentValues();
        //Store column name as key and description as value
        values.put(COLUMN_DESCRIPTION, description);
        //Store column name as key and date as value
        values.put(COLUMN_DATE, date);
        //Insert row into the TABLE_TASK
        db.insert(TABLE_TASK,null,values);
        //Close database connection
        db.close();
    }

    public ArrayList<String> getTaskContent(){
        //create arraylist that holds string objects
        ArrayList<String> tasks = new ArrayList<String>();
        //select all tasks' description
        String selectQuery = "SELECT " + COLUMN_DESCRIPTION + " FROM " + TABLE_TASK;

        //Get instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        //run SL and get back Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst() moves to first row
        if(cursor.moveToFirst()){
            //loop while moveToNest() points to next row and return truel moveToNest() returns false when no more next row to move to
            do{
                //add task content to arrayList, 0 in getString(0) return data in the first position.
                tasks.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        //close connection
        cursor.close();
        db.close();

        return  tasks;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_DESCRIPTION + ", "
                + COLUMN_DATE
                + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                String date = cursor.getString(2);
                Task obj = new Task(id, description, date);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

}
