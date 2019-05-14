package romain.com.multigames.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by romai on 26/03/2019.
 */

public class Player extends RealmObject{

    @PrimaryKey
    private String lastname;
    private String firstname;
    private int age;
    private String location;
    private String picture;

    public Player(){}

    public Player(String lastname, String firstname, int age, String location, String picture) {
        this.setLastname(lastname);
        this.setFirstname(firstname);
        this.setAge(age);
        this.setLocation(location);
        this.setPicture(picture);
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
