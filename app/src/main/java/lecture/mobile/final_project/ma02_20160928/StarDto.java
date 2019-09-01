package lecture.mobile.final_project.ma02_20160928;

public class StarDto {
    private int _id;
    private String StoreName;
    private String category;
    private String address;
    private String phone;

    public StarDto(int _id, String storeName, String category, String address, String phone) {
        this._id = _id;
        StoreName = storeName;
        this.category = category;
        this.address = address;
        this.phone = phone;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "StarDto{" +
                "_id=" + _id +
                ", StoreName='" + StoreName + '\'' +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
