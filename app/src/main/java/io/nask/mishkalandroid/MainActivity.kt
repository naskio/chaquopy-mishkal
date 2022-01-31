package io.nask.mishkalandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// python-start
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
// python-end

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val oTextView = findViewById(R.id.original) as TextView
        val pTextView = findViewById(R.id.processed) as TextView
        val text = "مرحبا بالعالم"
        oTextView.text = text
        pTextView.text = text

        // python-start
        try {
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }
            val py = Python.getInstance()
            val module = py.getModule("mishkal_module") // filename inside folder main/python/
            val processed_text =
                module.callAttr("vocalize", text) // function name inside mishkal_module.py
                    .toJava(String::class.java)
            pTextView.text = processed_text
        } catch (e: PyException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            Log.d("PYTHON", e.message.toString())
            pTextView.text = e.message.toString()
        }
        // python-end

    }
}