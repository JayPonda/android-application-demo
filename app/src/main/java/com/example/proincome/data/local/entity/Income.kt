package com.example.proincome.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.proincome.data.local.utils.ApplyTimeEntity
import com.example.proincome.data.local.utils.TransactionType

@Entity(
    tableName = "income",
    foreignKeys = [ForeignKey(entity = Group::class, parentColumns = ["id"], childColumns = ["group_id"], onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION)],
    indices = [Index("group_id")])
data class Income(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "group_id")
    val groupId: Int,

    @ColumnInfo(name = "amount", defaultValue="0", typeAffinity = ColumnInfo.REAL)
    val amount: Float,

    @ColumnInfo(name = "target_timestamp", typeAffinity = ColumnInfo.INTEGER)
    val targetTimestamp: Long,

    @ColumnInfo(name = "enable", defaultValue="1")
    val enable: Boolean,

    ) : ApplyTimeEntity()