package cn.rongcloud.im.niko.db.model;


import java.util.List;

import androidx.room.Embedded;
import androidx.room.PrimaryKey;

public class ProfileInfo {
    /**
     * Head : {"UID":0,"Name":"string","NameColor":"string","UserIcon":"string","Gender":true}
     * Bio : string
     * Location : string
     * School : string
     * Followers : 0
     * Followings : 0
     * Likes : 0
     * Moments : 0
     * FavorTopicIDs : [0]
     */



//    @Embedded
    private ProfileHeadInfo Head;


//    @PrimaryKey
    private String Bio;//个人简介
//    @PrimaryKey
    private String Location;
//    @PrimaryKey
    private String School;
//    @PrimaryKey
    private int Followers;
//    @PrimaryKey
    private int Followings;
//    @PrimaryKey
    private int Likes;
//    @PrimaryKey
    private int Moments;
//    @PrimaryKey
    private List<Integer> FavorTopicIDs;
//    @PrimaryKey
    private String DOB;

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public ProfileHeadInfo getHead() {
        return Head;
    }

    public void setHead(ProfileHeadInfo Head) {
        this.Head = Head;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String Bio) {
        this.Bio = Bio;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String School) {
        this.School = School;
    }

    public int getFollowers() {
        return Followers;
    }

    public void setFollowers(int Followers) {
        this.Followers = Followers;
    }

    public int getFollowings() {
        return Followings;
    }

    public void setFollowings(int Followings) {
        this.Followings = Followings;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int Likes) {
        this.Likes = Likes;
    }

    public int getMoments() {
        return Moments;
    }

    public void setMoments(int Moments) {
        this.Moments = Moments;
    }

    public List<Integer> getFavorTopicIDs() {
        return FavorTopicIDs;
    }

    public void setFavorTopicIDs(List<Integer> FavorTopicIDs) {
        this.FavorTopicIDs = FavorTopicIDs;
    }


}
