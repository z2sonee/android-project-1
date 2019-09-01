package lecture.mobile.final_project.ma02_20160928;

public class DiaryDto {
    private long _id;
    private String date;
    private String photoPath;
    private String foodName;
    private String storeName;
    private String address;
    private String phone;
    private String recipe;
    private String memo;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "DiaryDto{" +
                "_id=" + _id +
                ", date='" + date + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", foodName='" + foodName + '\'' +
                ", storeName='" + storeName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", recipe='" + recipe + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
