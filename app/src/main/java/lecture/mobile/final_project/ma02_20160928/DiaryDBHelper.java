package lecture.mobile.final_project.ma02_20160928;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DiaryDBHelper extends SQLiteOpenHelper {
    private final static String TAG = "DiaryDBHelper";

    private final static String DB_NAME ="diary_db";
    public final static String TABLE_NAME = "diary_table";
    public final static String ID = "_id";
    public final static String PATH = "photoPath";
    public final static String DATE = "date";
    public final static String FOODNAME = "foodName";
    public final static String STORENAME = "storeName";
    public final static String ADDRESS = "address";
    public final static String PHONE = "phone";
    public final static String RECIPE = "recipe";
    public final static String MEMO = "memo";

    public DiaryDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID + " integer primary key autoincrement, "
                + PATH + " text, " + DATE + " text, " + FOODNAME + " text, " + STORENAME + " text, " + ADDRESS + " text, " + PHONE + " text, " + RECIPE + " text, " + MEMO + " text);";
        Log.d(TAG, sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
