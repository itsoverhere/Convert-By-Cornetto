package edu.mobapde.convertbycornetto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ngoc on 3/2/2016.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper{

    public static final String SCHEMA = "units";
    public static final int VERSION = 2;

    public DatabaseOpenHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Unit.TABLE_NAME + "("
                + Unit.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Unit.COLUMN_UNITNAME + " TEXT, "
                + Unit.COLUMN_EQUIVALENCE + " REAL);";
        db.execSQL(sql);

        ContentValues cv = new ContentValues();
        cv.put(Unit.COLUMN_UNITNAME, "cornetto");
        cv.put(Unit.COLUMN_EQUIVALENCE, 1.0);
        db.insert(Unit.TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + Unit.TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public long insertUnit(Unit u){
        ContentValues cv = new ContentValues();
        cv.put(Unit.COLUMN_UNITNAME, u.getUnitName());
        cv.put(Unit.COLUMN_EQUIVALENCE, u.getCornettoEquivalence());
        return getWritableDatabase().insert(Unit.TABLE_NAME, null, cv);
    }

    public Unit queryUnit(int id){
        Unit n = new Unit();
        Cursor cursor = getReadableDatabase().query(Unit.TABLE_NAME,
                null,
                Unit.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);
        if(cursor.moveToFirst()){
            n.setUnitName(cursor.getString(cursor.getColumnIndex(Unit.COLUMN_UNITNAME)));
            n.setCornettoEquivalence(cursor.getDouble(cursor.getColumnIndex(Unit.COLUMN_EQUIVALENCE)));
            n.setId(cursor.getInt(cursor.getColumnIndex(Unit.COLUMN_ID)));
        }else{
            n = null;
        }

        return n;
    }

    public Cursor queryAllUnits(){
        return getReadableDatabase().query(Unit.TABLE_NAME, null, null, null, null, null, null, null);
    }

    public int updateUnit(Unit updatedUnit){
        ContentValues cv = new ContentValues();
        cv.put(Unit.COLUMN_UNITNAME, updatedUnit.getUnitName());
        cv.put(Unit.COLUMN_EQUIVALENCE, updatedUnit.getCornettoEquivalence());
        return getWritableDatabase().update(Unit.TABLE_NAME,
                                            cv,
                                            Unit.COLUMN_ID + "=?",
                                            new String[]{String.valueOf(updatedUnit.getId())});

    }

    public int deleteUnit(int id){
        return getWritableDatabase().delete(Unit.TABLE_NAME, Unit.COLUMN_ID +"=?", new String[]{String.valueOf(id)});
    }

    public double getConversion(int leftUnitId, int rightUnitId, double tvLeftUnit){
        double leftUnit=0, rightUnit=0;
        Cursor cursor = getReadableDatabase().query(Unit.TABLE_NAME,
                new String[]{Unit.COLUMN_EQUIVALENCE},
                Unit.COLUMN_ID+"=?",
                new String[]{String.valueOf(leftUnitId)},
                null, null, null);
        if(cursor.moveToFirst()){
            leftUnit = cursor.getDouble(0);
        }

        cursor = getReadableDatabase().query(Unit.TABLE_NAME,
                new String[]{Unit.COLUMN_EQUIVALENCE},
                Unit.COLUMN_ID+"=?",
                new String[]{String.valueOf(rightUnitId)},
                null, null, null);
        if(cursor.moveToFirst()){
            rightUnit = cursor.getDouble(0);
        }

        return (leftUnit/rightUnit)*tvLeftUnit;
    }
}





















