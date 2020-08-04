package cn.rongcloud.im;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDataBase extends RoomDatabase {
    public abstract BookDao bookDao();
    private static BookDataBase instance;

    public static BookDataBase getInstance(Context context){
        if (instance == null){
            synchronized (BookDataBase.class){
                if (instance == null){
                    instance = create(context);
                }
            }
        }
        return instance;
    }

    private static BookDataBase create(Context context) {
        return Room.databaseBuilder( context,BookDataBase.class,"book-db").allowMainThreadQueries().build();
    }
}
