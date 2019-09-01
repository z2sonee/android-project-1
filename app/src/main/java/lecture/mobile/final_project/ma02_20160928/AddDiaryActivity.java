package lecture.mobile.final_project.ma02_20160928;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//나만의 맛집 일기장에 추가하는 액티비티
public class AddDiaryActivity extends AppCompatActivity {
    final static String TAG = "AddDiaryActivity";
    private static final int REQUEST_TAKE_PHOTO = 200;

    private String mCurrentPhotoPath;

    ImageView ivPhoto;
    EditText etDate;
    EditText etMenu;
    EditText etStoreName;
    EditText etAddress;
    EditText etPhone;
    EditText etRecipe;
    EditText etMemo;

    DiaryDBHelper helper;

    Uri photoURI;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_add);

        helper = new DiaryDBHelper(this);

        ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
        etDate = (EditText)findViewById(R.id.etDate);
        etMenu = (EditText)findViewById(R.id.etMenu);
        etStoreName = (EditText)findViewById(R.id.etStoreName);
        etAddress = (EditText)findViewById(R.id.etAddress);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etRecipe = (EditText)findViewById(R.id.etRecipe);
        etMemo = (EditText)findViewById(R.id.etMemo);

        ivPhoto.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    외부 카메라 호출
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (photoFile != null) {
                            photoURI = FileProvider.getUriForFile(AddDiaryActivity.this,
                                    "lecture.mobile.final_project.ma02_20160928.fileprovider", photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                        }
                    }
                    return true;
                }
                return false;
            }
        });

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
//                DB에 촬영한 사진의 파일 경로 및 메모 저장
                String imagePath = mCurrentPhotoPath;
                String date = etDate.getText().toString();
                String menu = etMenu.getText().toString();
                String storeName = etStoreName.getText().toString();
                String address = etAddress.getText().toString();
                String phone = etPhone.getText().toString();
                String recipe = etRecipe.getText().toString();
                String memo = etMemo.getText().toString();

                String sql;
                db = helper.getWritableDatabase();
                sql = String.format("INSERT INTO diary_table VALUES(NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", imagePath, date, menu, storeName, address, phone, recipe, memo);
                db.execSQL(sql);

                Toast.makeText(this, "Save!", Toast.LENGTH_SHORT).show();
                finish(); //save후 액티비티가 종료되게 함.
                break;

            case R.id.btnCancel:
                finish();
                break;
        }
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) { //사진을 찍은 후 사진을 ivPhoto에 적용하는 부분.
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ivPhoto.setImageBitmap(imageBitmap);
            ivPhoto.setImageURI(photoURI);

        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivPhoto.getWidth();
        int targetH = ivPhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivPhoto.setImageBitmap(bitmap);
    }
}
