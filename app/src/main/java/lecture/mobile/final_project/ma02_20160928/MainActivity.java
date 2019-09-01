package lecture.mobile.final_project.ma02_20160928;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Calendar;

//처음 홈 메인 액티비티. 버튼들을 눌러 각각의 기능을 수행하면된다!
public class MainActivity extends AppCompatActivity {

    ImageView ivList;
    ImageView ivSearch;
    ImageView ivStar;
    ImageView ivGame;
    ImageView ivAround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivList = (ImageView)findViewById(R.id.ivList);
        ivSearch = (ImageView)findViewById(R.id.ivSearch);
        ivStar = (ImageView)findViewById(R.id.ivStar);
        ivGame = (ImageView)findViewById(R.id.ivGame);
        ivAround = (ImageView)findViewById(R.id.ivAround);

        ivList.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, DiaryActivity.class);
                startActivity(intent1);
            }
        });
        ivSearch.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, LIstActivity.class);
                startActivity(intent2);
            }
        });

        ivStar.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, StarActivity.class);
                startActivity(intent3);
            }
        });

        ivAround.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent4);
            }
        });

        ivGame.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent5);
            }
        });

    }

}
