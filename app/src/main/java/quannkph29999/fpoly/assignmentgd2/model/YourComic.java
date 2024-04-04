package quannkph29999.fpoly.assignmentgd2.model;

public class YourComic {
    private String _id;
    private String id_user;
    private String id_comic;
    private String  namecomic;
    private String img;
    private String price;
    private String date;

    public YourComic(String id_user, String id_comic, String namecomic, String img, String price, String date) {
        this.id_user = id_user;
        this.id_comic = id_comic;
        this.namecomic = namecomic;
        this.img = img;
        this.price = price;
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_comic() {
        return id_comic;
    }

    public void setId_comic(String id_comic) {
        this.id_comic = id_comic;
    }

    public String getNamecomic() {
        return namecomic;
    }

    public void setNamecomic(String namecomic) {
        this.namecomic = namecomic;
    }
}
