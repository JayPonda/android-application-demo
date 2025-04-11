package com.example.proincome.data.local.utils

import androidx.room.ColumnInfo

interface CreateUpdateEntity {
    val createdAt: Long
    var updatedAt: Long

    fun updateActionTime() : CreateUpdateEntity {
        updatedAt = System.currentTimeMillis()
        return this
    }
}

interface SoftDeleteEntity {
    var deletedAt: Long?

    fun delete(): SoftDeleteEntity {
        deletedAt = System.currentTimeMillis()
        return this
    }

    fun restore(): SoftDeleteEntity {
        deletedAt = null
        return this
    }
}

open class ApplyTimeEntity : CreateUpdateEntity, SoftDeleteEntity {
    @ColumnInfo(name = "created_at")
    override var createdAt: Long = System.currentTimeMillis()

    @ColumnInfo(name = "updated_at")
    override var updatedAt: Long = System.currentTimeMillis()

    @ColumnInfo(name = "deleted_at")
    override var deletedAt: Long? = null;

    override fun updateActionTime(): CreateUpdateEntity {
        if(deletedAt == null)
            return super.updateActionTime()
        return this
    }

    override fun restore(): SoftDeleteEntity {
        super.restore()
        super.updateActionTime()
        return this
    }
}

enum class TransactionType(val value: Int) {
    INCOME(0),
    EXPENSE(1);

    companion object {
        fun fromInt(value: Int) = entries.first { it.value == value }
    }
}