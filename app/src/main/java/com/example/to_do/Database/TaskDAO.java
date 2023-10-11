package com.example.to_do.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insertTask(Task... task);

    @Update
    void updateTask(Task... task);

    @Delete
    void deleteTask(Task... task);

    @Query("SELECT * FROM task ORDER BY status ASC")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT COUNT(*) FROM task WHERE status = :status")
    LiveData<Integer> getTaskCountByStatus(boolean status);

}
