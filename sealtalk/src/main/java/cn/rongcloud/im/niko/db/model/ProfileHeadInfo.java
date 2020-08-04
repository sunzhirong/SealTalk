package cn.rongcloud.im.niko.db.model;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "profile_head")
public class ProfileHeadInfo {
    /**
     * UID : 0
     * Name : string
     * NameColor : string
     * UserIcon : string
     * Gender : true
     */

    @PrimaryKey
    @NonNull
    private int UID;
    @ColumnInfo
    private String Name;
    @ColumnInfo
    private String NameColor;
    @ColumnInfo
    private String UserIcon;
    @ColumnInfo
    private boolean Gender;

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
        ProfileHeadInfo info = (ProfileHeadInfo) o;
        return UID == info.UID &&
                Gender == info.Gender &&
                Objects.equals(Name, info.Name) &&
                Objects.equals(NameColor, info.NameColor) &&
                Objects.equals(UserIcon, info.UserIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID, Name, NameColor, UserIcon, Gender);
    }
}
