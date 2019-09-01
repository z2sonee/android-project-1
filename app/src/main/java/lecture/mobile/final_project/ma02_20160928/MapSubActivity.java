package lecture.mobile.final_project.ma02_20160928;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import lecture.mobile.final_project.ma02_20160928.model.MyPlace;

public class MapSubActivity extends AppCompatActivity {
    final static String TAG = "MapSubActivity";

    MyPlace place;

    TextView tvName_map;
    TextView tvPhone_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapsub_main);

        Intent intent = getIntent();
        tvName_map = (TextView)findViewById(R.id.tvName_map);
        tvPhone_map = (TextView)findViewById(R.id.tvPhone_map);

        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        tvName_map.setText(name);
        tvPhone_map.setText(phone);


        Button btnCall_map = (Button)findViewById(R.id.btnCall_map);
        final String p = phone;
        btnCall_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "전화 거는 화면으로 이동", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:/" + p));
                startActivity(intent);
                finish();
            }
        });

    }
}
