package com.example.quiz_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class WonActivity : AppCompatActivity() {
    private var btnShare: LinearLayout? = null
    private var circularProgressBar: CircularProgressBar? = null
    private var result: TextView? = null
    private var correct = 0
    private var wrong = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_won)

        correct = intent.getIntExtra("correct", 0)
        wrong = intent.getIntExtra("wrong", 0)

        circularProgressBar = findViewById(R.id.circularProgressBar)
        result = findViewById(R.id.result)
        btnShare = findViewById(R.id.won_btn_share)

        circularProgressBar?.progress = correct.toFloat()
        result?.text = "$correct/10"

        btnShare?.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quiz_app")
                startActivity(Intent.createChooser(shareIntent, "Choose one"))
            } catch (e: Exception) {
                // Обработка ошибки, если возникнет
            }
        }
    }
}
