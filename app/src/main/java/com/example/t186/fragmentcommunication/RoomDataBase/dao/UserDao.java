package com.example.t186.fragmentcommunication.RoomDataBase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.t186.fragmentcommunication.RoomDataBase.Entity.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by T186 on 4/27/2018.
 */


@Dao
public interface UserDao {


    @Query("SELECT * from user")
    Maybe<List<User>> liAllUser();

    @Query("SELECT * from user where first_name Like :firstName AND last_name LIKE :lastName")
    User findByName(String firstName, String lastName);


    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    Flowable<List<User>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE uid = :userId")
    Single<User> getUserById(String userId);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertlist(List<User> userEntities);

    @Delete
    void delete(User user);

}
