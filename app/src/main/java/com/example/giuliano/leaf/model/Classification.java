package com.example.giuliano.leaf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Classification implements Serializable{

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("created")
    @Expose
    private Date created;

    @SerializedName("image")
    @Expose
    private String imageUrl;

    @SerializedName("name")
    @Expose
    private String name;

    /*@SerializedName("classes")
    @Expose
    private List<String> classes;*/

    @SerializedName("severidades")
    @Expose
    private List<Double> severidades;

    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("user")
    @Expose
    private User user;

    public Classification(){

    }

    public Classification(String _id, Date created, String imageUrl, String name, List<Double> severidades, String size, User user) {
        this._id = _id;
        this.created = created;
        this.imageUrl = imageUrl;
        this.name = name;
        //this.classes = classes;
        this.severidades = severidades;
        this.size = size;
        this.user = user;
    }

    /*public int getClasse(){
        double max=0;
        int pos=0;
        for(int i=0; i<getPorcentagens().size(); i++){
            if(getPorcentagens().get(i)>max) {
                max = getPorcentagens().get(i);
                pos = i;
            }
        }
        return pos;
    }*/

    public String getSeveridadeAll(){
        String bxMineiro = "Bixo Mineiro:"+this.severidades.get(0);
        String ferrugem = "Ferrugem:"+this.severidades.get(1);
        String svTotal = "Severidade:"+this.severidades.get(2);
        return bxMineiro+"\n"+ferrugem+"\n"+svTotal;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<String> getClasses() {
        return classes;
    }*/

    /*public void setClasses(List<String> classes) {
        this.classes = classes;
    }*/

    public List<Double> getSeveridades() {
        return severidades;
    }

    public void setSeveridades(List<Double> severidades) {
        this.severidades = severidades;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
