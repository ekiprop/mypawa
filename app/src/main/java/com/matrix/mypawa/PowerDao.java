package com.matrix.mypawa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PowerDao {

    @Insert
    void insert(Power power);

    @Update
    void update(Power power);

    @Delete
    void delete(Power power);

    @Query("SELECT * FROM power_table ORDER BY id ASC")
    LiveData<List<Power>> getAllPowers();
}
