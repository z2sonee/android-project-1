package lecture.mobile.final_project.ma02_20160928;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//api결과를 이용해 맛집 검색 결과를 보여주는 액티비티
public class LIstActivity extends AppCompatActivity {
    public static final String TAG = "LIstActivity";

    ListView list;
    EditText etList;

    String apiAddress;
    String query;
    MyFoodAdapter adapter;
    ArrayList<NaverFoodDto> resultList;
    NaverFoodXmlParser parser;
    NaverFoodDBHelper helper;
    StarDBHelper helper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);

        list = (ListView) findViewById(R.id.list);
        etList = (EditText)findViewById(R.id.etList);

        resultList = new ArrayList();

        adapter = new MyFoodAdapter(this, R.layout.list_item, resultList);
        list.setAdapter(adapter);

        helper = new NaverFoodDBHelper(LIstActivity.this);
        helper2 = new StarDBHelper(LIstActivity.this);

        apiAddress = getResources().getString(R.string.api_url);
        parser = new NaverFoodXmlParser();

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position;
                SQLiteDatabase db = helper2.getWritableDatabase();

                ContentValues row = new ContentValues();
                //row.put("_id", resultList.get(pos).get_id());
                row.put("StoreName", resultList.get(pos).getStoreName());
                row.put("category", resultList.get(pos).getCategory());
                row.put("address", resultList.get(pos).getAddress());
                row.put("phone", resultList.get(pos).getPhone());

                db.insert(StarDBHelper.TABLE_NAME, null, row);

                Toast.makeText(LIstActivity.this, "즐겨찾기에 추가 완료!",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnShow:
                query = etList.getText().toString();
                new NaverAsyncTask().execute();
                break;


        }
    }



    class NaverAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(LIstActivity.this, "Wait", "Downloading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer response = new StringBuffer();

            // 클라이언트 아이디 및 시크릿 그리고 요청 URL 선언
            //  String clientId = getResources().getString(R.string.client_id);
            String clientId = "eCiMZFm7SOMRJUgdG4xx";

            //String clientSecret = getResources().getString(R.string.client_secret);
            String clientSecret = "hLv70vEk1R";

            try {
                String apiURL = apiAddress + URLEncoder.encode(query, "UTF-8");
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");  //헤더, naver 접속시 필요한 부분
                con.setRequestProperty("X-Naver-Client-Id", clientId);  //naver 접속시 필요한 부분
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret); //naver 접속시 필요한 부분
                // response 수신
                int responseCode = con.getResponseCode();
                if (responseCode==200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } else {
                    Log.e(TAG, "API 호출 에러 발생 : 에러코드=" + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.toString(); //xml 결과
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, result);

            resultList = parser.parse(result);  //xml을 파싱한 dto를 담은 arrayList
            adapter.setList(resultList);
            adapter.notifyDataSetChanged(); //adapter의 getView 호출

            progressDlg.dismiss();
        }
    }
}
