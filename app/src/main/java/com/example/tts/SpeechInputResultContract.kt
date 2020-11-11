package com.example.tts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContract

/**
 * @author Perry Lance
 * @since 2020-11-09 Created
 */
class SpeechInputResultContract : ActivityResultContract<String, ArrayList<String>?>() {

    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_PROMPT, input)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ArrayList<String>? {
        val data = intent?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null
    }
}