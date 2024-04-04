package quannkph29999.fpoly.assignmentgd2.model;

import java.util.List;

public class Comic {
    private String _id;
    private String namecomic;
    private String price;
    private String author;
    private String publishing_year;
    private String describe;
    private String genres;
    private String cover_image;
    private String comic_content;
    private List<Comment> comment;

    public Comic() {
    }

    public Comic(String namecomic, String price, String author, String publishing_year, String describe, String genres, String cover_image, String comic_content, List<Comment> comment) {
        this.namecomic = namecomic;
        this.price = price;
        this.author = author;
        this.publishing_year = publishing_year;
        this.describe = describe;
        this.genres = genres;
        this.cover_image = cover_image;
        this.comic_content = comic_content;
        this.comment = comment;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNamecomic() {
        return namecomic;
    }

    public void setNamecomic(String namecomic) {
        this.namecomic = namecomic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing_year() {
        return publishing_year;
    }

    public void setPublishing_year(String publishing_year) {
        this.publishing_year = publishing_year;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getComic_content() {
        return comic_content;
    }

    public void setComic_content(String comic_content) {
        this.comic_content = comic_content;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
