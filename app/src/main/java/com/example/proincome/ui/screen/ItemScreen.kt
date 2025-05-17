package com.example.proincome.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proincome.data.local.AppDatabase
import com.example.proincome.ui.component.OperationStatusBanner
import com.example.proincome.ui.component.TransactionList
import com.example.proincome.viewmodels.GreetingViewModel

@Composable
fun ItemsScreen(modifier: Modifier = Modifier) {
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
    val operationStatus by viewModel.operationStatus.observeAsState("")

    Column(modifier = modifier) {  // Use the passed modifier
        OperationStatusBanner(status = operationStatus)
        TransactionList(items = items)
    }
}