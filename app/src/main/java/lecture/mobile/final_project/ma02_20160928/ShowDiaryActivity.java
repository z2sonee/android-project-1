package lecture.mobile.final_project.ma02_20160928;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//리스트중 한 행을 누르면 이 액티비티에서 자세한 정보를 볼 수 있고 공유도 할 수 있는 액티비티!
public class ShowDiaryActivity extends AppCompatActivity {
    final static String TAG = "ShowDiaryActivity";
    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 1;
    boolean permissionCheck = false;

    DiaryDBHelper helper;
    ImageView ivPhoto;
    TextView tvDate;
    TextView tvFoodName;
    TextView tvStoreName;
    TextView tvAddress;
    TextView tvPhone;
    TextView tvRecipe;
    TextView tvMemo;

    Cursor cursor; //이미지 경로를 얻어오기 위한 커서
    Cursor cursor2; //날짜 값
    Cursor cursor3; //메모 값을 얻어오기 위한 커서
    Cursor cursor4; //음식 이름 값
    Cursor cursor5; //가게 이름 값
    Cursor cursor6; //주소
    Cursor cursor7; //전화번호
    Cursor cursor8; //레시피


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_show);
        helper = new DiaryDBHelper(this);

        ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvFoodName = (TextView)findViewById(R.id.tvFoodName);
        tvStoreName = (TextView)findViewById(R.id.tvStoreName);
        tvAddress = (TextView)findViewById(R.id.tvAddress_diary);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        tvRecipe = (TextView)findViewById(R.id.tvRecipe);
        tvMemo = (TextView)findViewById(R.id.tvMemo);


//        MainActivity 에서 전달 받은 _id 값을 사용하여 DB 레코드를 가져온 후 ImageView 와 TextView 설정
        Intent itt = getIntent();
        Long data = itt.getLongExtra("id", 0);

        Log.v("ShowDiaryActivity", "id" + data);

        SQLiteDatabase db = helper.getReadableDatabase(); //디비를 읽기전용으로 불러옴.
        //이미지불러오기
        cursor = db.rawQuery("select photoPath from diary_table where _id = " + data, null); //테이블에서 path값만 셀렉트함.

        String result = "";
        while (cursor.moveToNext()) {
            String imagePath = cursor.getString(cursor.getColumnIndex("photoPath"));
            result += imagePath;
            Log.d(TAG, result);
        }
        cursor.close();

        try {
            ivPhoto.setImageURI(Uri.parse(result)); //커서를 이용해 찾은 이미지 경로값을 ivPhoto에 적용.
//            ivPhoto.setImageResource(Integer.parseInt(result));
        }catch (NumberFormatException e) { //이미지가 없을 경우 NumberFormat예외 발생
           ivPhoto.setImageResource(R.drawable.ic_launcher_background); //기본 이미지를 대입해준다
        }
            Log.e("Response", result); //로그 확인.
        //날짜 불러오기
        cursor3 = db.rawQuery("select date from diary_table where _id = " + data, null);

        String result3 = "";
        while (cursor3.moveToNext()) {
            String date = cursor3.getString(cursor3.getColumnIndex("date"));
            result3 += date;
        }
        cursor3.close();
        tvDate.setText(result3);
        //음식이름 불러오기
        cursor4 = db.rawQuery("select foodName from diary_table where _id =" + data, null); //테이블에서 음식이름만 셀렉트함

        String result4 = "";
        while (cursor4.moveToNext()) {
            String foodName = cursor4.getString(cursor4.getColumnIndex("foodName"));
            result4 += foodName;
        }
        cursor4.close();
        tvFoodName.setText(result4);
        //가게 이름 불러오기
        cursor5 = db.rawQuery("select storeName from diary_table where _id =" + data, null); //테이블에서 가게이름만 셀렉트함

        String result5 = "";
        while (cursor5.moveToNext()) {
            String storeName = cursor5.getString(cursor5.getColumnIndex("storeName"));
            result5 += storeName;
        }
        cursor5.close();
        tvStoreName.setText(result5);
        //주소 불러오기
        cursor6 = db.rawQuery("select address from diary_table where _id =" + data, null); //테이블에서 주소만 셀렉트함

        String result6 = "";
        while (cursor6.moveToNext()) {
            String address = cursor6.getString(cursor6.getColumnIndex("address"));
            result6 += address;
        }
        cursor6.close();
        tvAddress.setText(result6);
        //전화번호 불러오기
        cursor7 = db.rawQuery("select phone from diary_table where _id =" + data, null); //테이블에서 전화번호만 셀렉트함

        String result7 = "";
        while (cursor7.moveToNext()) {
            String phone = cursor7.getString(cursor7.getColumnIndex("phone"));
            result7 += phone;
        }
        cursor7.close();
        tvPhone.setText(result7);
        //메모 불러오기
        cursor2 = db.rawQuery("select memo from diary_table where _id =" + data, null); //테이블에서 메모만 셀렉트함

        String result2 = "";
        while (cursor2.moveToNext()) {
            String memo = cursor2.getString(cursor2.getColumnIndex("memo"));
            result2 += memo;
        }
        cursor2.close();
        tvMemo.setText(result2); //커서를 이용해 가져온 메모 값을 tvMemo에 적용
        //레시피 불러오기
        cursor8 = db.rawQuery("select recipe from diary_table where _id =" + data, null); //테이블에서 나만의 레시피를 셀렉트함

        String result8 = "";
        while (cursor8.moveToNext()) {
            String recipe =  cursor8.getString(cursor8.getColumnIndex("recipe"));
            result8 += recipe;
        }
        cursor8.close();
        tvRecipe.setText(result8);
        helper.close(); //다 썼으니 클로즈 시켜줌.


        //버튼을 누르면 바로 다이얼 화면으로 이동해 그 전화번호로 바로 전화를 걸 수 있게 하는 부분
        Button btnCall = (Button)findViewById(R.id.btnCall);
        final String finalResult = result7;
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "전화 거는 화면으로 이동", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:/" + finalResult));
                startActivity(intent);
                finish();
            }
        });

        //메일로 맛집 정보 전송하여 공유하는 부분
        Button btnSend = (Button)findViewById(R.id.btnSend);
        final String stName = result5;
        final String fdName = result4;
        final String addr = result6;
        final String mm = result2;
        final String myRec = result8;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "메일 보내는 화면으로 이동", Toast.LENGTH_LONG).show();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plane/text");
                String[] address = {"jisun4789@naver.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "나만의 맛집을 메일로 공유하기");
                email.putExtra(Intent.EXTRA_TEXT, "음식점 이름: " + stName + "\n음식 이름: " + fdName + "\n음식점 위치: " + addr + "\n메모: " + mm + "\n나만의 레시피: " + myRec);
                startActivity(email);
            }
        });

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnClose:
                finish();
                break;
        }
    }

}
