package com.example.proincome.ui.component

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.proincome.data.local.entity.Group
import com.example.proincome.data.local.utils.TransactionType

// Status Banner Component
@Composable
fun OperationStatusBanner(status: String) {
    Text(
        text = status,
        modifier = Modifier.padding(horizontal = 16.dp),
        color = if (status == "Success") Color.Green else Color.Red
    )
}

// Transaction List Component
@Composable
fun TransactionList(items: List<Group>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(items) { item ->
            TransactionItem(item = item)
        }
    }
}

// Single Transaction Item Component
@Composable
fun TransactionItem(item: Group) {
    Text(
        text = "${item.icon} ${item.id} ${item.name}",
        modifier = Modifier.padding(vertical = 8.dp),
        color = if (item.type == TransactionType.EXPENSE) Color.Red else Color.Green
    )
    Log.d("TransactionItem", "Rendered item: $item")
}