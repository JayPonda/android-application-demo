package com.example.proincome

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proincome.data.local.AppDatabase
import com.example.proincome.data.local.dao.GroupDao
import com.example.proincome.data.local.entity.Group
import com.example.proincome.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("MyComposable", "Composable is being composed")

        val dao = AppDatabase.getDatabase(application).groupDao()
        val viewModel = GreetingViewModel(dao)
        viewModel.loadAllItems()


        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    // Using viewModel() here creates a single instance per composition
//                    val greetingViewModel: GreetingViewModel = viewModel()
//                    DataObject(
//                        modifier = Modifier.padding(innerPadding),
//                        greetingViewModel = greetingViewModel
//                    )
                    ItemsScreen()
                }
            }
        }
    }
}

class GreetingViewModel(private val groupDao: GroupDao) : ViewModel() {
    private val _groups = MutableLiveData<List<Group>>()
    val allGroups : LiveData<List<Group>> = _groups

    // LiveData for operation status/errors
    private val _operationStatus = MutableLiveData<String>()
    val operationStatus: LiveData<String> = _operationStatus

    init {
        loadAllItems()
    }

    fun loadAllItems(){
        viewModelScope.launch {
            try {
                _groups.value = groupDao.getAllGroups()
                _operationStatus.value = "Success"
            } catch (e: Exception) {
                _operationStatus.value = "Error loading items: ${e.message}"
                Log.e("GreetingViewModel", "Error loading items: ${e.message}")
            }
        }
    }

//    fun getItemById(id: Int) {
//        viewModelScope.launch {
//            try {
//                _selectedItem.value = groupDao.getGroupById(id)
//                _operationStatus.value = "Success"
//            } catch (e: Exception) {
//                _operationStatus.value = "Error getting item: ${e.message}"
//            }
//        }
//    }
}

//@Composable
//fun DataObject(
//    modifier: Modifier = Modifier,
//    greetingViewModel: GreetingViewModel
//) {
//    // Convert LiveData to compose State
//    val name by greetingViewModel.name.observeAsState("")
//
//    // Update the state when LiveData changes
//    LaunchedEffect(name) {
//        greetingViewModel.changeValue(name)
//    }
//
//    Greeting(
//        name = name,
//        onValueChange = { newValue ->
//            greetingViewModel.changeValue(newValue)
//        },
//        modifier = modifier
//    )
//}


//@Composable
//fun Greeting(
//    name: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(modifier = modifier.padding(16.dp)) {
//        Text(
//            text = "Hello $name! from V2",
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//
//        TextField(
//            value = name,
//            onValueChange = onValueChange,
//            label = { Text("Enter your custom text here") }
//        )
//    }
//}