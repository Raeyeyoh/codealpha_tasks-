package com.example.myapplicationq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainpart()
        }
    }
}
    @Composable
    fun genNewQuote(whatitdoes: () -> Unit) {
        Button(onClick = whatitdoes) {
            Text(text = "click for a new quote")
        }
    }

    @Composable
    fun display_quotes(quote: String) {
        Text(text = ("$quote"))

    }


 @Preview(showBackground = true)
 @Composable
 fun mainpart() {
     val context = LocalContext.current
     val quotes = context.resources.getStringArray(R.array.quotes)
     var currentQuote by remember { mutableStateOf(quotes.random()) }

     Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(24.dp),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
         display_quotes(currentQuote)

         Spacer(modifier = Modifier.height(24.dp))

         genNewQuote {
             currentQuote = quotes.random()
         }
     }
 }

