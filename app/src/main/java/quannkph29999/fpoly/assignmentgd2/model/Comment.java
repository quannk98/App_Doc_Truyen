package quannkph29999.fpoly.assignmentgd2.model;

public class Comment {
    private String _id;
    private String id_comic;
    private String id_user;
    private String username;
    private String content;
    private String date;

    public Comment() {
    }

    public Comment(String id_comic, String id_user, String username, String content, String date) {
        this.id_comic = id_comic;
        this.id_user = id_user;
        this.username = username;
        this.content = content;
        this.date = date;
    }

    public String getId_comic() {
        return id_comic;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setId_comic(String id_comic) {
        this.id_comic = id_comic;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
