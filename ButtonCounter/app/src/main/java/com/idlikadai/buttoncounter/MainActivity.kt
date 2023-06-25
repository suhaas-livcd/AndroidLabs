package com.idlikadai.buttoncounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textCounter = findViewById<TextView>(R.id.textCounter);
        val buttonCounter = findViewById<Button>(R.id.buttonCounter);
        var counter = 0
        buttonCounter.setOnClickListener {
            counter+=1
            textCounter.text = counter.toString()
        }

        // Reset the count value
        buttonCounter.setOnLongClickListener {
            Toast.makeText(this, "Counter reset",Toast.LENGTH_SHORT).show();
            counter = 0;
            textCounter.text = 0.toString();
            true
        }
    }
}