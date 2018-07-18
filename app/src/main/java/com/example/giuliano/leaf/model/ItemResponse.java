package com.example.giuliano.leaf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemResponse {

    @SerializedName("items")
    @Expose
    private List<Classification> items;

    public List<Classification> getItems(){
        return items;
    }
    public void setItems(List<Classification> items){
        this.items = items;
    }
}
