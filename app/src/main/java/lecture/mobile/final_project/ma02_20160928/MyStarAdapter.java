package lecture.mobile.final_project.ma02_20160928;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyStarAdapter extends CursorAdapter {
    LayoutInflater inflater;
    Cursor cursor;

    public MyStarAdapter(Context context, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(R.layout.star_item, parent, false);
        return listItemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvStore_star = (TextView)view.findViewById(R.id.tvStore_star);
        TextView tvCategory_star = (TextView)view.findViewById(R.id.tvCategory_star);
        TextView tvAddress_star = (TextView)view.findViewById(R.id.tvAddress_star);
        TextView tvPhone_star = (TextView)view.findViewById(R.id.tvPhone_star);

        tvStore_star.setText(cursor.getString(cursor.getColumnIndex("StoreName")));
        tvCategory_star.setText(cursor.getString(cursor.getColumnIndex("category")));
        tvAddress_star.setText(cursor.getString(cursor.getColumnIndex("address")));
        tvPhone_star.setText(cursor.getString(cursor.getColumnIndex("phone")));

    }
}
