package com.example.mortrza.myadvertismentdispaly.ADV;

public class Agahi {

    private String id;
    private String Title;
    private String Description;
    private String cat;
    private byte[] ax;
    private int like;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public byte[] getAx() {
        return ax;
    }

    public void setAx(byte[] ax) {
        this.ax = ax;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
