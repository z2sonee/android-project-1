package lecture.mobile.final_project.ma02_20160928;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyFoodAdapter extends BaseAdapter {
    public static final String TAG = "MyFoodAdapter";

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<NaverFoodDto> list;
    private ViewHolder viewHodler = null;


    public MyFoodAdapter(Context context, int resource, ArrayList<NaverFoodDto> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NaverFoodDto getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView with position : " + position);
        View view = convertView;

        if (view == null) {
            viewHodler = new ViewHolder();
            view = inflater.inflate(layout, parent, false);
            viewHodler.tvStore = (TextView)view.findViewById(R.id.tvStore);
            viewHodler.tvCategory = (TextView)view.findViewById(R.id.tvCategory);
            viewHodler.tvAddress = (TextView)view.findViewById(R.id.tvAddress);
            viewHodler.tvPhone = (TextView) view.findViewById(R.id.tvPhone);

            view.setTag(viewHodler);
        } else {
            viewHodler = (ViewHolder)view.getTag();
        }

        NaverFoodDto dto = list.get(position);

        viewHodler.tvStore.setText(dto.getStoreName());
        viewHodler.tvCategory.setText(dto.getCategory());
        viewHodler.tvAddress.setText(dto.getAddress());
        viewHodler.tvPhone.setText(dto.getPhone());

        return view;
    }

    public void setList(ArrayList<NaverFoodDto> list) {
        this.list = list;
    }

    public void clear() {
        this.list = new ArrayList<NaverFoodDto>();
    }


    static class ViewHolder {
        public TextView tvStore = null;
        public TextView tvCategory = null;
        public TextView tvAddress = null;
        public TextView tvPhone = null;
    }


}
