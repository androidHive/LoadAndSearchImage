package com.example.searchimage.PojoCalss;

public class PojoData {

    String id, title, imgid, link;

    public PojoData(String id, String title, String imgid, String link) {
        this.id = id;
        this.title = title;
        this.imgid = imgid;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }
}
