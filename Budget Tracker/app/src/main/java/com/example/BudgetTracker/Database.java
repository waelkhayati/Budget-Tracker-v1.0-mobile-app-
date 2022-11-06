package com.example.BudgetTracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

class Database extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ExpensesRecords.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_expenses";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_AMOUNT = "amount";

    Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_DATE + " DATE NOT NULL, " +
                COLUMN_AMOUNT + " INT NOT NULL);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRecord(String category, String date, int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_AMOUNT, amount);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT " + COLUMN_ID + "," + COLUMN_CATEGORY + "," + COLUMN_DATE + "," + COLUMN_AMOUNT + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DATE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public String totalAmount() {
        String result;
        SQLiteDatabase db = this.getReadableDatabase();
        //selecting the balance = income - expense
        String query= "select  sum(case when " + COLUMN_CATEGORY +" = 'Income' then " + COLUMN_AMOUNT + " else 0 end) - sum(case when " + COLUMN_CATEGORY +" <> 'Income' then " + COLUMN_AMOUNT + " else 0 end) " + " from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            result = String.valueOf(cursor.getInt(0));
        }else{
            result="0";
        }
        cursor.close();
        db.close();
        return result;
    }

    public int categoryAmount(String cat){
        int result = 0 ;
        SQLiteDatabase db = this.getReadableDatabase();
        //selecting the balance = income - expense
        String query= "select  sum(" + COLUMN_AMOUNT + ") from " + TABLE_NAME +" where " + COLUMN_CATEGORY + "= '" + cat + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            result = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return result;
    }

    public int totalIncome(){
        int result = 0 ;
        SQLiteDatabase db = this.getReadableDatabase();
        //selecting the balance = income - expense
        String query= "select  sum(" + COLUMN_AMOUNT + ") from " + TABLE_NAME +" where " + COLUMN_CATEGORY + "= 'Income'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            result = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return result;
    }

    public int totalExpenses(){
        int result = 0 ;
        SQLiteDatabase db = this.getReadableDatabase();
        //selecting the balance = income - expense
        String query= "select  sum(" + COLUMN_AMOUNT + ") from " + TABLE_NAME +" where " + COLUMN_CATEGORY + "<> 'Income'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            result = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return result;
    }




    void updateData(String row_id, String category, String date, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_AMOUNT, amount);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
