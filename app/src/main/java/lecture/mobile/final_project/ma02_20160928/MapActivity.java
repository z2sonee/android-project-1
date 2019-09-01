package lecture.mobile.final_project.ma02_20160928;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import lecture.mobile.final_project.ma02_20160928.model.MyPlace;
import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

//자기 주변의 맛집들을 찾아 지도에 표시해줌
public class MapActivity extends AppCompatActivity {

    final static String TAG = "MapActivity";
    //    permission 요청 식별 코드
    private final static int REQ_PERMISSIONS = 100;

    private LocationManager mLocManager;
    private Location mLastLoc;

    private GoogleMap mGoogleMap;

    GeoDataClient mGeoDataClient;


    EditText etType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_main);

        etType = findViewById(R.id.etType);

        mGeoDataClient = Places.getGeoDataClient(this);


// LocationManager 생성
        mLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

//        permission 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQ_PERMISSIONS);
            return;
        }


// 최종 Location 확인
        mLastLoc = mLocManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if (mLastLoc == null) {
            mLastLoc.setLatitude(37.60248);
            mLastLoc.setLongitude(127.041541);
        }

        // 구글 지도 정보 가져오기 실행 - 강의자료 GoogleMap의 활용 13 page 참고
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallback);

        mGeoDataClient = Places.getGeoDataClient(this);

        mLocManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 0, locationListener);
    }


    // 구글 지도 가져오기 Callback 구현
    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLoc.getLatitude(), mLastLoc.getLongitude()), 17));
            Log.i(TAG, "Location found: " + mLastLoc.getLatitude());
        }
    };

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };



    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                // 이전 Marker 가 있을 경우 삭제
                mGoogleMap.clear();

//                주변 위치 정보 요청

                new NRPlaces.Builder().listener( placeListener )
                        .key(getResources().getString(R.string.google_api_key))
                        //.latlng(37.60248, 127.041541)
                        .latlng(mLastLoc.getLatitude(), mLastLoc.getLongitude())
                        .radius(200)
                        .type(convertType(etType.getText().toString()))
                        .build()
                        .execute();

                break;
        }
    }

    private String convertType (String type) {
        Log.i(TAG, type);
        if (type.equals(getResources().getString(R.string.type_cafe))) return PlaceType.CAFE;
        else if (type.equals(getResources().getString(R.string.type_restaurant))) return PlaceType.RESTAURANT;
        return type;
    }



    PlacesListener placeListener = new PlacesListener() {
        @Override
        public void onPlacesFailure(PlacesException e) {
        }

        @Override
        public void onPlacesStart() {
        }

        @Override
        public void onPlacesSuccess(final List<Place> places) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (final Place place : places) {
                        Log.i("PlaceResult", place.getName());

                        Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(place.getPlaceId());
                        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                            @Override
                            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                                if(task.isSuccessful()) {
                                    PlaceBufferResponse places = task.getResult();
                                    com.google.android.gms.location.places.Place myPlace = places.get(0);

                                    //강의자료 13페이지 처럼 따로 클래스로 만든후 이렇게 꺼내 쓰는 방법도 가능! 자신이 쓸 것만 다 꺼내서 설정해주면됨.
                                    final MyPlace mPlace = new MyPlace();
                                    mPlace.setName(place.getName().toString());
                                    mPlace.setType(place.getTypes().toString());
                                    mPlace.setPhone(myPlace.getPhoneNumber().toString());
                                    mPlace.setAddress(myPlace.getAddress().toString());
                                    mPlace.setLatitude(place.getLatitude());
                                    mPlace.setLongitude(place.getLongitude());

                                    Log.i(TAG, "Place found: " + myPlace.getName());
                                    MarkerOptions options = new MarkerOptions();
                                    options.position(new LatLng(place.getLatitude(), place.getLongitude()));
                                    options.title(place.getName());
                                    options.snippet(myPlace.getPhoneNumber().toString());
                                    Log.i(TAG, "Place found: " + myPlace.getPhoneNumber());
                                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                                    Marker marker = mGoogleMap.addMarker(options);
                                    marker.showInfoWindow();

                                    marker.setTag(mPlace);

                                    mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                        @Override
                                        public void onInfoWindowClick(Marker marker) {
                                            Intent intent = new Intent(MapActivity.this, MapSubActivity.class);
                                            intent.putExtra("name", marker.getTitle());
                                            intent.putExtra("phone", marker.getSnippet());
                                            startActivity(intent); //여기까지만 했고 이 후로는 서브액티비티 정의해줘야함
                                        }
                                    });

                                    places.release();
                                } else {
                                    Log.e(TAG, "Place not found");
                                }
                            }
                        });
                    }

                }
            });

        }

        @Override
        public void onPlacesFinished() {
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapActivity.this, "Permission was granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

}
