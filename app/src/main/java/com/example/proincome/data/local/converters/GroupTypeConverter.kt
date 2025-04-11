package com.example.proincome.data.local.converters

import androidx.room.TypeConverter
import com.example.proincome.data.local.utils.TransactionType


class GroupTypeConverter {
    @TypeConverter
    fun fromTransactionType(type: TransactionType): Int = type.value

    @TypeConverter
    fun toTransactionType(value: Int): TransactionType = TransactionType.fromInt(value)
}