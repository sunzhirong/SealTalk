package cn.rongcloud.im.niko.model.sc;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserInfo {

    /**
     * Firstname : string
     * Lastname : string
     * Email : string
     * Phone : string
     * Gender : true
     * DOB : 2020-07-11T06:10:22.204Z
     * Address : string
     * Address2 : string
     * City : string
     * State : string
     * Country : string
     * Origin : string
     * Height : 0
     * Weight : 0
     */

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "first_name")
    private String Firstname;
    @ColumnInfo(name = "last_name")
    private String Lastname;
    @ColumnInfo(name = "email")
    private String Email;
    @ColumnInfo(name = "phone")
    private String Phone;
    @ColumnInfo(name = "gender")
    private boolean Gender;
    @ColumnInfo(name = "dob")
    private String DOB;
    @ColumnInfo(name = "address")
    private String Address;
    @ColumnInfo(name = "address2")
    private String Address2;
    @ColumnInfo(name = "city")
    private String City;
    @ColumnInfo(name = "state")
    private String State;
    @ColumnInfo(name = "country")
    private String Country;
    @ColumnInfo(name = "origin")
    private String Origin;
    @ColumnInfo(name = "height")
    private int Height;
    @ColumnInfo(name = "weight")
    private int Weight;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String Lastname) {
        this.Lastname = Lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean Gender) {
        this.Gender = Gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String Address2) {
        this.Address2 = Address2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String Origin) {
        this.Origin = Origin;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int Height) {
        this.Height = Height;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int Weight) {
        this.Weight = Weight;
    }
}
