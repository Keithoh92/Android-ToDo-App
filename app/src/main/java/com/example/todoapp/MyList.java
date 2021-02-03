package com.example.todoapp;

public class MyList {

    private String _id;
    private String name;
    private String lists;

    public MyList(){

    }
    public MyList(String _id, String name, String lists){
        this._id = _id;
        this.name = name;
        this.lists = lists;
    }

    public MyList(String name, String lists){
        this.name = name;
        this.lists = lists;
    }

    public MyList(String _id){this._id = _id;}

    public String get_id(){return _id;}

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLists() {
        return lists;
    }

    public void setLists(String lists) {
        this.lists = lists;
    }
}
