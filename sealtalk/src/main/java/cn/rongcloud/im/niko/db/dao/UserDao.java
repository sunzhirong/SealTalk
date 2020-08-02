package cn.rongcloud.im.niko.db.dao;

import cn.rongcloud.im.niko.model.sc.UserInfo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE id=:id")
    LiveData<UserInfo> getUserById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserInfo userInfo);

    @Query("SELECT * FROM user WHERE id=:id")
    UserInfo getUserByIdSync(String id);
}
