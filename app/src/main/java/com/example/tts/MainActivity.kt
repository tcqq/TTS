package com.example.tts

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),
    TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech

    private val speechInput =
        registerForActivityResult(SpeechInputResultContract()) { result ->
            if (result != null) {
                val query = result[0]
                edit_text.setText(query)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textToSpeech = TextToSpeech(this, this)

        play.setOnClickListener {
            speak()
        }
        speak.setOnClickListener {
            speechInput.launch("Try saying something")
        }
    }

    public override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.JAPAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                showToast("This Language is not supported")
            } else {
                speak()
            }
        } else {
            showToast("Initialization Failed!")
        }
    }

    private fun speak() {
        val text = edit_text.text.toString()
        if (text.isNotBlank()) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            showToast("Please enter text")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}