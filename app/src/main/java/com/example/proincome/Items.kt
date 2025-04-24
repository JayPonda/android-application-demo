@file:Suppress("UNCHECKED_CAST")

package com.example.proincome
// ItemsScreen.kt
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proincome.data.local.AppDatabase
import com.example.proincome.data.local.utils.TransactionType

@Composable
fun ItemsScreen() {
    val context = LocalContext.current
    val viewModel: GreetingViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = AppDatabase.getDatabase(context).groupDao()
                return GreetingViewModel(dao) as T
            }
        }
    )

    val items by viewModel.allGroups.observeAsState(emptyList())
    val operationStatus: String by viewModel.operationStatus.observeAsState("")

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = operationStatus,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = if(operationStatus == "Success") Color.Green else Color.Red
        )

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(items) { item ->
                Text(
                    text = item.icon + " " + item.id + " " + item.name,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = if (item.type == TransactionType.EXPENSE) Color.Red else Color.Green
                )
                Log.d("ItemsScreen", "Item-log here: $item")
            }
        }
    }
}