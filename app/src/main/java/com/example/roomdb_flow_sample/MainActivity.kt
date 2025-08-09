package com.example.roomdb_flow_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roomdb_flow_sample.ui.theme.RoomDbFlowsampleTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<XViewModel> {
        XViewModelFactory((application as MyApp).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDbFlowsampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  Column(modifier = Modifier.padding(innerPadding)){
                      XListScreen(viewModel)
                  }
                }
            }
        }
    }
}

@Composable
fun XListScreen(viewModel: XViewModel) {
    val items by viewModel.xs.collectAsState(initial = emptyList())
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items) { x ->
                Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = x.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Details: ${x.details.size} items", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Button(onClick = {
            // Example insert
            viewModel.insert(
                X(name = "New item ${System.currentTimeMillis()}",
                    details = listOf(Y("a", 1), Y("b", 2)))
            )
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Add random X")
        }
    }
}