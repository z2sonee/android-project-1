package lecture.mobile.final_project.ma02_20160928;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryAdapter extends CursorAdapter {
    public static final String TAG = "DiaryAdapter";

    LayoutInflater inflater;
    Cursor cursor;

    public DiaryAdapter(Context context, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(R.layout.diary_item, parent, false);
        return listItemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        ImageView ivPhoto = (ImageView)view.findViewById(R.id.ivPhoto);
        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        TextView tvFoodName = (TextView)view.findViewById(R.id.tvFoodName);
        TextView tvStoreName = (TextView)view.findViewById(R.id.tvStoreName);
//        TextView tvAddress = (TextView)view.findViewById(R.id.tvAddress_diary);
        TextView tvPhone = (TextView)view.findViewById(R.id.tvPhone);
//        TextView tvMemo = (TextView)view.findViewById(R.id.tvMemo);

//        ivPhoto.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex("photoPath"))));
        tvDate.setText(cursor.getString(cursor.getColumnIndex("date")));
        tvFoodName.setText(cursor.getString(cursor.getColumnIndex("foodName")));
        tvStoreName.setText(cursor.getString(cursor.getColumnIndex("storeName")));
//        tvAddress.setText(cursor.getString(cursor.getColumnIndex("address")));
        tvPhone.setText(cursor.getString(cursor.getColumnIndex("phone")));
//        tvMemo.setText(cursor.getString(cursor.getColumnIndex("memo")));
    }
}
