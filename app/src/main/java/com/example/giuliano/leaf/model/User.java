package com.example.giuliano.leaf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class User implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("displayName")
    @Expose
    private String displayName;

    @SerializedName("provider")
    @Expose
    private String provider;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("created")
    @Expose
    private Date created;

    @SerializedName("roles")
    @Expose
    private List<String> roles = null;

    @SerializedName("profileImageURL")
    @Expose
    private String profileImageURL;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("__v")
    @Expose
    private Integer v;

    private String cookie;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param lastName
     * @param profileImageURL
     * @param provider
     * @param id
     * @param v
     * @param username
     * @param created
     * @param email
     * @param roles
     * @param displayName
     * @param firstName
     */
    public User(String cookie, String id, String displayName, String provider, String username,
                Date created, List<String> roles, String profileImageURL,
                String email, String lastName, String firstName, Integer v) {
        super();
        this._id = id;
        this.displayName = displayName;
        this.provider = provider;
        this.username = username;
        this.created = created;
        this.roles = roles;
        this.profileImageURL = profileImageURL;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.v = v;
        this.cookie = cookie;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + _id +
                ", displayName='" + displayName + '\'' +
                ", provider='" + provider + '\'' +
                ", username='" + username + '\'' +
                ", created=" + created +
                ", roles=" + roles +
                ", profileImageURL='" + profileImageURL + '\'' +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", v=" + v +
                '}';
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}