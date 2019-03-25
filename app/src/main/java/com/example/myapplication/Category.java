package com.example.myapplication;

public class Category {
    protected String name;
    protected long id;
    Category(){}

    Category(String _name){
        this.name=_name;
        this.id=0;
    }
    //Setters
    public void setName(String _name){this.name=_name;}

    //Getters
    public long getId(){return this.id;}

    public String getName(){return this.name;}
@Override
    public String toString(){
        return name;
}
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Category) {
            Category c = (Category) obj;
            if (c.id == id) {
                return true;
            }
        }
                return false;


    }
}
