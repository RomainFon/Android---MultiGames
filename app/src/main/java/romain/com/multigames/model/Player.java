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

    private int scoreDragNDrop;
    private int scoreSwipe;
    private int scoreFastTap;
    private int scoreIpacGame;

    public Player(){}

    public Player(String lastname, String firstname, int age, String location, String picture) {
        this.setLastname(lastname);
        this.setFirstname(firstname);
        this.setAge(age);
        this.setLocation(location);
        this.setPicture(picture);

        this.setScoreDragNDrop(0);
        this.setScoreFastTap(0);
        this.setScoreIpacGame(0);
        this.setScoreSwipe(0);
    }

    public String getLastname() {
        return lastname;
    }

    private void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    private void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    private void setPicture(String picture) {
        this.picture = picture;
    }

    public int getScoreDragNDrop() {
        return scoreDragNDrop;
    }

    public void setScoreDragNDrop(int scoreDragNDrop) {
        this.scoreDragNDrop = scoreDragNDrop;
    }

    public int getScoreSwipe() {
        return scoreSwipe;
    }

    public void setScoreSwipe(int scoreSwipe) {
        this.scoreSwipe = scoreSwipe;
    }

    public int getScoreFastTap() {
        return scoreFastTap;
    }

    public void setScoreFastTap(int scoreFastTap) {
        this.scoreFastTap = scoreFastTap;
    }

    public int getScoreIpacGame() {
        return scoreIpacGame;
    }

    public void setScoreIpacGame(int scoreIpacGame) {
        this.scoreIpacGame = scoreIpacGame;
    }

}
