package quannkph29999.fpoly.assignmentgd2.model;

import java.util.List;

public class User {
    private String _id;
    private String username;
    private String password;
    private String money;
    private String email;
    private String fullname;
    private List<YourComic> yourComics;

    public User(String _id, String username, String password, String money, String email, String fullname, List<YourComic> yourComics) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.money = money;
        this.email = email;
        this.fullname = fullname;
        this.yourComics = yourComics;
    }

    public User() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<YourComic> getYourComics() {
        return yourComics;
    }

    public void setYourComics(List<YourComic> yourComics) {
        this.yourComics = yourComics;
    }
}
