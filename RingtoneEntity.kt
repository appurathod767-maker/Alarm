package com.appu.smarttunealarmx.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ringtones")
data class RingtoneEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uri: String,
    val name: String
)
