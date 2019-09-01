package lecture.mobile.final_project.ma02_20160928;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class StarDBHelper extends SQLiteOpenHelper {
    private final static String TAG = "StarDBHelper";

    private final static String DB_NAME ="star_db";
    public final static String TABLE_NAME = "star_table";
    public final static String ID = "_id";
    public final static String STORENAME = "StoreName";
    public final static String CATEGORY = "category";
    public final static String ADDRESS = "address";
    public final static String PHONE = "phone";

    public StarDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + " (" + ID + " integer primary key autoincrement, "
                + STORENAME + " text, " + CATEGORY + " text, " + ADDRESS + " text, " + PHONE + " text);";
        Log.d(TAG, sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
