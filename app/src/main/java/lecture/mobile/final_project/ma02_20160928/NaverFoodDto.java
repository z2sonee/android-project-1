package lecture.mobile.final_project.ma02_20160928;

import android.text.Html;
import android.text.Spanned;

import com.google.android.gms.maps.model.LatLng;

public class NaverFoodDto {
    private int _id;
    private String StoreName;
    private String category;
    private String address;
    private String phone;
    private String mapx;
    private String mapy;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getStoreName() {
        Spanned spanned = Html.fromHtml(StoreName);
        return spanned.toString();
        //return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public void setMapy(String mapy) {
        this.mapy = mapy;
    }

    @Override
    public String toString() {
        return "NaverFoodDto{" +
                "_id=" + _id +
                ", StoreName='" + StoreName + '\'' +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", mapx='" + mapx + '\'' +
                ", mapy='" + mapy + '\'' +
                '}';
    }
}
