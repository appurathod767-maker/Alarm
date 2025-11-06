package com.appu.smarttunealarmx.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RingtoneDao {
    @Query("SELECT * FROM ringtones")
    suspend fun getAll(): List<RingtoneEntity>

    @Insert
    suspend fun insert(ringtone: RingtoneEntity)
}
