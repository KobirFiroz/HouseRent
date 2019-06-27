package com.homerent.bd;

public class Upload {
     String imgName;
     String imgUrl;
     String imgDiv;
     String imgUpo;
     String phone;
     String lat;
     String lng;

    public Upload() {
    }

    public Upload(String imgName, String imgUrl, String imgDiv, String imgUpo, String phone, String lat,String lng) {
        if(imgName.trim().equals(""))
        {
            imgName="No name";
        }
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.imgDiv = imgDiv;
        this.imgUpo = imgUpo;
        this.phone = phone;
        this.lat= lat;
        this.lng=lng;

    }


    //for division
    public String getImgDiv() {
        return imgDiv;
    }

    public void setImgDiv(String imgDiv) {
        this.imgDiv = imgDiv;
    }


    public String getphone() {
        return phone;
    }
    public void setphone(String phone) {
        this.phone = phone;
    }

    //upozila
    public String getImgUpo() {
        return imgUpo;
    }

    public void setImgUpo(String imgUpo) {
        this.imgUpo = imgUpo;
    }

    //for name

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    //for uri
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
