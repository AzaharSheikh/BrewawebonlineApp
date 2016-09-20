package com.thoughtinteract.brewawebonlineapp.Model;

/**
 * Created by AzaharSheikh on 20-09-2016.
 */
public class Product {
    private String title, thumbnailUrl;
    private String  p_address;
    private String  p_details;

    public Product(){}
    public Product(String name, String thumbnailUrl, String p_address, String p_details) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.p_address = p_address;
        this.p_details = p_details;

    }
    public String getP_details() {
        return p_details;
    }

    public void setP_details(String p_details) {
        this.p_details = p_details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
    }




}
