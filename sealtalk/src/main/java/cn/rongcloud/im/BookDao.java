package cn.rongcloud.im;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import cn.rongcloud.im.db.model.FriendInfo;

@Dao
public interface BookDao {

    @Query("SELECT * FROM Book")
    List<Book> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);
}
