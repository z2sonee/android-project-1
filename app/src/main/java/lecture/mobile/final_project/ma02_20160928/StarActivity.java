package lecture.mobile.final_project.ma02_20160928;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//즐겨찾기를 한 맛집들만 따로 모아볼 수 있고 즐겨찾기 해제도 가능한 액티비티!
public class StarActivity extends AppCompatActivity {
    public static final String TAG = "StarActivity";

    MyStarAdapter adapter;
    ArrayList<StarDto> resultList;
    StarDBHelper helper;
    ListView list;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_main);

        resultList = new ArrayList<StarDto>();

        helper = new StarDBHelper(this);
        list = (ListView) findViewById(R.id.list_star);

        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from star_table", null);

        adapter = new MyStarAdapter(this, cursor);

        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                final int pos = position;
                final long idd = id;
                AlertDialog.Builder builder = new AlertDialog.Builder(StarActivity.this);
                builder.setTitle("즐겨찾기 삭제")
                        .setMessage("정말로 삭제하시겠어요?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = helper.getWritableDatabase();
                                //cursor = db.rawQuery("delete * from star_table where _id = " + pos, null);
                                String whereClause = StarDBHelper.ID + "=?";
                                String[] whereArgs = new String[]{String.valueOf(idd)};

                                db.delete(StarDBHelper.TABLE_NAME, whereClause, whereArgs);

                                cursor = db.rawQuery("select * from star_table", null);
                                adapter = new MyStarAdapter(getApplicationContext(), cursor);
                                list.setAdapter(adapter);
                                helper.close();

                                Toast.makeText(StarActivity.this, "삭제 완료!", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .show();
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
