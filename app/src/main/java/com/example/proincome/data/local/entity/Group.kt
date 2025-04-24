package com.example.proincome.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proincome.data.local.utils.ApplyTimeEntity
import com.example.proincome.data.local.utils.TransactionType


@Entity(tableName = "groups")
data class Group(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "enable", defaultValue="1")
    val enable: Boolean,

    @ColumnInfo(name = "icon", defaultValue="null")
    val icon: String?,

    @ColumnInfo(name = "relative_path", defaultValue="null")
    val relativePath: String?,

    @ColumnInfo(name = "transaction_type", defaultValue="0")
    val type: TransactionType,

    ) : ApplyTimeEntity()
