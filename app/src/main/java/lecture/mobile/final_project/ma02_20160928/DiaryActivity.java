package lecture.mobile.final_project.ma02_20160928;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

//나의 맛집 일기장의 데이터 리스트를 보여주고 추가하는 버튼이 있는 액티비티
public class DiaryActivity extends AppCompatActivity {
    public static final String TAG = "DiaryActivity";
    DiaryAdapter diaryAdapter;
    Cursor cursor;
    DiaryDBHelper helper;
    ListView diary_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_main);

        helper = new DiaryDBHelper(this);
        diary_list = (ListView)findViewById(R.id.diary_list);

//        어댑터에 SimpleCursorAdapter 연결
        // memoAdapter = new SimpleCursorAdapter( /* 매개변수 설정 */);
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from diary_table", null);

        diaryAdapter = new DiaryAdapter(this, cursor);

        diary_list.setAdapter(diaryAdapter);


        diary_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long _id) {
//                ShowMemoActivity 호출
                Intent itt = new Intent(DiaryActivity.this, ShowDiaryActivity.class);
                itt.putExtra("id", _id); //_id값을 ShowMemoActivity 클래스로 넘겨줌.

                startActivity(itt);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        DB 에서 모든 레코드를 가져와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from diary_table", null);
        diaryAdapter.changeCursor(cursor);
        helper.close();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd_diary:
//                AddMemoActivity 호출
                Intent it = new Intent(this, AddDiaryActivity.class);
                startActivity(it);

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
    }

}
