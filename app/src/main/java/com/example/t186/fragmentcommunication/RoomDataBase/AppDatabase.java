package com.example.t186.fragmentcommunication.RoomDataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.t186.fragmentcommunication.RoomDataBase.Entity.User;
import com.example.t186.fragmentcommunication.RoomDataBase.dao.UserDao;

/**
 * Created by T186 on 4/27/2018.
 */
@Database(
        entities = {User.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "user_db";
    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            // allowing main thread queries, just for testing
                            .allowMainThreadQueries()
                            //  .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            //database.execSQL("ALTER TABLE tracking " + " ADD COLUMN test INTEGER");
        }
    };

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
