package com.example.t186.fragmentcommunication.RoomDataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.t186.fragmentcommunication.RoomDataBase.Entity.User;

import java.util.List;

/**
 * Created by T186 on 4/27/2018.
 */


@Dao
public interface UserDao {


    @Query("SELECT * from user")
    List<User> liGatAll();

    @Query("SELECT * from user where first_name Like :firstName AND last_name LIKE :lastName")
    User findByName(String firstName, String lastName);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

}
