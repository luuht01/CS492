package com.example.myapplication;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Item {
    protected long id;
    protected String productNumber;
    protected String description;
    protected String websiteUrl;
    protected double price;
    protected User updateBy;
    protected Category category;
    protected ArrayList<String> keywords;
    protected ZonedDateTime updateDate;
    protected byte image[];
    Item() {
    }

    Item(String _productNumber,
         String _description,
         String _websiteUrl,
         double _price,
         User _user,
         Category _category,
         byte[] _image,
         ArrayList<String> _keywords) {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
        this.id = 0;
        this.productNumber = _productNumber;
        this.description = _description;
        this.websiteUrl = _websiteUrl;
        this.price = _price;
        this.updateBy = _user;
        this.category = _category;
        this.keywords = _keywords;
        this.updateDate = ZonedDateTime.now();
        this.image=_image;
    }

    //Setters
    void setProductNumber(String _productNumber) {
        this.productNumber = _productNumber;
    }

    void setDescription(String _description) {
        this.description = _description;
    }

    void setWebsiteUrl(String _websiteUrl) {
        this.websiteUrl = _websiteUrl;
    }

    void setPrice(double _price) {
        this.price = _price;
    }

    void setCategory(Category _category) {
        this.category = _category;
    }

    void setUpdateUser(User _user) {
        this.updateBy = _user;
    }

    void addKeyword(String _keyword) {
        keywords.add(_keyword);
    }

    void setKeywords(ArrayList<String> _keywords) {
        this.keywords = _keywords;
    }

    void setImage(byte[] _image){this.image=_image;}

    //Getters
    long getId() {
        return this.id;
    }

    String getProductNumber() {
        return this.productNumber;
    }

    String getDescription() {
        return this.description;
    }

    String getWebsiteUrl() {
        return this.websiteUrl;
    }

    double getPrice() {
        return this.price;
    }

    String getPriceString() {
        return "$" + String.valueOf(this.price);
    }

    User getUpdateBy() {
        return this.updateBy;
    }

    Category getCategory() {
        return this.category;
    }

    byte[] getImage(){return this.image;}

    LocalDate getUpdateDate() {
        return this.updateDate.toLocalDate();
    }

    ArrayList<String> getKkeywords() {
        return this.keywords;
    }
}
