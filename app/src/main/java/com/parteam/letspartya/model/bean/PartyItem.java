package com.parteam.letspartya.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class PartyItem implements Serializable {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopical() {
        return topical;
    }

    public void setTopical(String topical) {
        this.topical = topical;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganiger() {
        return organiger;
    }

    public void setOrganiger(String organiger) {
        this.organiger = organiger;
    }

    public String getOrg_tel() {
        return org_tel;
    }

    public void setOrg_tel(String org_tel) {
        this.org_tel = org_tel;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public long getHolderID() {
        return holderID;
    }

    public void setHolderID(long holderID) {
        this.holderID = holderID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getFav() {
        return fav;
    }

    public void setFav(long fav) {
        this.fav = fav;
    }

    public ArrayList<CommentItem> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentItem> comments) {
        this.comments = comments;
    }

    long id = -1;
    private String topical = null;
    private String county = null;
    private String province = null;
    private String city = null;
    private String location = null;
    private String organiger = null;
    private String org_tel = null;
    private long time = 0;
    private String detail = null;
    private ArrayList<String> images;
    private String thumbnail = null;
    private long holderID = -1;
    private long fav;
    private ArrayList<CommentItem> comments;
}
