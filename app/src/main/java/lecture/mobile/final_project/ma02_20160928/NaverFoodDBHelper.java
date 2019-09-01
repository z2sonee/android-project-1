package lecture.mobile.final_project.ma02_20160928;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class NaverFoodDBHelper extends SQLiteOpenHelper {
    private final static String TAG = "NaverFoodDBHelper";

    private final static String DB_NAME ="food_db";
    public final static String TABLE_NAME = "food_table";
    public final static String ID = "_id";
    public final static String STORENAME = "StoreName";
    public final static String CATEGORY = "category";
    public final static String ADDRESS = "address";
    public final static String PHONE = "phone";
    public final static String MAPX = "mapx";
    public final static String MAPY = "mapy";

    public NaverFoodDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + " (" + ID + " integer primary key autoincrement, "
                + STORENAME + " text, " + CATEGORY + " text, " + ADDRESS + " text, " + PHONE + " text, " + MAPX + " text, " + MAPY + " text);";
        Log.d(TAG, sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
