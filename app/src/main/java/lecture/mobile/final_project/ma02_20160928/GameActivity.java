package lecture.mobile.final_project.ma02_20160928;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//게임 버튼을 누르면 실행되는, 뭐먹을지 고를때 쓰는 센서이용화면
public class GameActivity extends Activity {
    public static final String TAG = "GameActivity";
    BallView ballView;

    private SensorManager mSensorMgr;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private Sensor light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
        ballView = new BallView(this);

        setContentView(ballView);

        mSensorMgr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        light = mSensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mSensorMgr.unregisterListener(ballView);
        mSensorMgr.unregisterListener(mLightSensorListener);
    }


    @Override
    protected void onResume() {
        super.onResume();

        mSensorMgr.registerListener(ballView, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorMgr.registerListener(ballView, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorMgr.registerListener(mLightSensorListener, light, SensorManager.SENSOR_DELAY_UI);
    }

    SensorEventListener mLightSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.accuracy != SensorManager.SENSOR_STATUS_UNRELIABLE) {
                if (sensorEvent.values[0] < 30) {
                    if (ballView.paint.getColor() == Color.GREEN) {
                        ballView.paint.setColor(Color.BLUE);
                    } else {
                        ballView.paint.setColor(Color.GREEN);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    class BallView extends View implements SensorEventListener {

        float[] mGravity = null;
        float[] mGeomagnetic= null;

        float pitch;
        float roll;

        Paint paint;

        int width;
        int height;

        int x;
        int y;
        int r;

        boolean isStart;

        public BallView(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setAntiAlias(true);
            isStart = true;
            r = 100;
        }

        public void onDraw(Canvas canvas) {

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.foooo);
            canvas.drawBitmap(bm, 0, 0, null);

            if(isStart) {
                width = canvas.getWidth();
                height = canvas.getHeight();
                x =  width / 2;
                y =  height / 2;
                //Rect rect = new Rect(400, 800, 400 + x, 800 + y);
                isStart = false;

            }

            canvas.drawCircle(x, y, r, paint);
        }


        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
//			중력과 자기장 값 획득, 각각 획득되므로 모두 수집할 때까지 멤버변수에 저장
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mGravity = event.values.clone();
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                mGeomagnetic = event.values.clone();

//		    두 센서 값이 모두 수집되었을 경우
            if (mGravity != null && mGeomagnetic != null) {
                float rotationMatrix[] = new float[9];
//		    	기기 측정 중력 및 자기장 값을 토대로 회전 정보 획득, rotationMatrix에 해당 값 저장
                boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, mGravity, mGeomagnetic);

                if (success) {
                    float values[] = new float[3];
//		    		회전 정보 매트릭스를 통해 기기의 orientation 획득, values 에 저장
                    SensorManager.getOrientation(rotationMatrix, values);

//		    		from rad. to degree
                    for (int i=0; i < values.length; i++) {
                        Double degrees = Math.toDegrees(values[i]);
                        values[i] = degrees.floatValue();
                    }

                    float pitch = values[1];
                    float roll = values[2];

                    if (pitch > 0) {
                        if (y > r) y -= 1;
                        //Toast.makeText(GameActivity.this, "'고기'를 추천합니당!", Toast.LENGTH_SHORT).show();
                    } else if (pitch < 0) {
                        if (y < (height - r)) y += 1;
                        //Toast.makeText(GameActivity.this, "'초밥'을 추천합니당!", Toast.LENGTH_SHORT).show();
                    }

                    if (roll > 0) {
                        if (x < (width - r)) x += 1;
                        //Toast.makeText(GameActivity.this, "'치킨'을 추천합니당!", Toast.LENGTH_SHORT).show();
                    } else if (roll < 0) {
                        if (x > r) x -= 1;
                        //Toast.makeText(GameActivity.this, "'햄버거'를 추천합니당!", Toast.LENGTH_SHORT).show();
                    }

                    invalidate();
                }
            }
        }

    }
}
