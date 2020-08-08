package cn.rongcloud.im.niko.db.model;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_head")
public class ProfileHeadInfo {

    /**
     * Alias : string
     * IsStar : 是否挚友
     * IsFriend : true
     * IsFollowing : true
     * IsFollower : true
     * IsBlock : true
     * UID : 0
     * Name : string
     * NameColor : string
     * UserIcon : string
     * Gender : true
     */
    @NonNull
    @PrimaryKey
    private int UID;

    @ColumnInfo
    private String Alias;
    @ColumnInfo
    private String IsStar;
    @ColumnInfo
    private boolean IsFriend;
    @ColumnInfo
    private boolean IsFollowing;
    @ColumnInfo
    private boolean IsFollower;
    @ColumnInfo
    private boolean IsBlock;
    @ColumnInfo
    private String Name;
    @ColumnInfo
    private String NameColor;
    @ColumnInfo
    private String UserIcon;
    @ColumnInfo
    private boolean Gender;


    public String getAlias() {
        return Alias;
    }

    public void setAlias(String Alias) {
        this.Alias = Alias;
    }

    public String getIsStar() {
        return IsStar;
    }

    public void setIsStar(String IsStar) {
        this.IsStar = IsStar;
    }

    public boolean isIsFriend() {
        return IsFriend;
    }

    public void setIsFriend(boolean IsFriend) {
        this.IsFriend = IsFriend;
    }

    public boolean isIsFollowing() {
        return IsFollowing;
    }

    public void setIsFollowing(boolean IsFollowing) {
        this.IsFollowing = IsFollowing;
    }

    public boolean isIsFollower() {
        return IsFollower;
    }

    public void setIsFollower(boolean IsFollower) {
        this.IsFollower = IsFollower;
    }

    public boolean isIsBlock() {
        return IsBlock;
    }

    public void setIsBlock(boolean IsBlock) {
        this.IsBlock = IsBlock;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNameColor() {
        return NameColor;
    }

    public void setNameColor(String NameColor) {
        this.NameColor = NameColor;
    }

    public String getUserIcon() {
        return UserIcon;
    }

    public void setUserIcon(String UserIcon) {
        this.UserIcon = UserIcon;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean Gender) {
        this.Gender = Gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileHeadInfo that = (ProfileHeadInfo) o;
        return UID == that.UID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID);
    }
}
