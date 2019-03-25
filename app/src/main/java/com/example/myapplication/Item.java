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
    protected  Category category;
    protected ArrayList<String> keywords;
    protected ZonedDateTime updateDate;


    //private ShopcastDBHelper DBhelper;
    Item (){}
    Item(String _productNumber,
         String _description,
         String _websiteUrl,
         double _price,
         User _user,
         Category _category,
         ArrayList<String> _keywords){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
            this.id=0;
            this.productNumber=_productNumber;
            this.description = _description;
            this.websiteUrl=_websiteUrl;
            this.price = _price;
            this.updateBy = _user;
            this.category =_category;
            this.keywords=_keywords;
            this.updateDate= ZonedDateTime.now();


    }
    //Setters
    public void setProductNumber(String _productNumber) {
        this.productNumber = _productNumber;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public void setWebsiteUrl(String _websiteUrl) {
        this.websiteUrl = _websiteUrl;
    }

    public void setPrice(double _price) {
        this.price = _price;
    }

    public void setCategory(Category _category){this.category=_category; }
public void setUpdateUser(User _user){this.updateBy=_user;}
    public void addKeyword(String _keyword) {
        keywords.add(_keyword);
    }

    public void setKeywords(  ArrayList<String> _keywords){this.keywords=_keywords;}

    //Getters
    public long getId() {
        return this.id;
    }

    public String getProductNumber() {
        return this.productNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public String getWebsiteUrl() {
        return this.websiteUrl;
    }

    public double getPrice(){
        return this.price;
    }
    public String getPriceString(){
        return "$"+String.valueOf(this.price);
    }

    public User getUpdateBy(){
        return this.updateBy;
    }

    public Category getCategory(){return this.category;}

    public LocalDate getUpdateDate(){
        return this.updateDate.toLocalDate();
    }

    public ArrayList<String> getKkeywords(){
        return this.keywords;
    }


}
