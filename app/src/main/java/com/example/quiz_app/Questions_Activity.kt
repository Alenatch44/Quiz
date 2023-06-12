package com.example.quiz_app

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.*

class Questions_Activity : AppCompatActivity() {
    var countDownTimer: CountDownTimer? = null
    var progressBar: ProgressBar? = null
    private val totalTimeInMillis: Long = 15000
    var allQuestionList: List<Modelclass?>? = null
    var modelclass: Modelclass? = null
    var index = 0
    var card_question: TextView? = null
    var optionOne: TextView? = null
    var optionTwo: TextView? = null
    var optionThree: TextView? = null
    var cardOne: CardView? = null
    var cardTwo: CardView? = null
    var cardThree: CardView? = null
    var correctCount = 0
    var wrongCount = 0
    var nextBtn: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        Hook()
        allQuestionList = MainActivity.Companion.list
        allQuestionList?.let { Collections.shuffle(it) }
        modelclass = MainActivity.Companion.list.get(index)
        cardOne!!.setBackgroundColor(resources.getColor(R.color.white))
        cardTwo!!.setBackgroundColor(resources.getColor(R.color.white))
        cardThree!!.setBackgroundColor(resources.getColor(R.color.white))
        nextBtn!!.isClickable = false
        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = millisUntilFinished.toInt()
                progressBar!!.progress = progress
            }

            override fun onFinish() {
                val dialog = Dialog(this@Questions_Activity)
                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.setContentView(R.layout.time_out_dialog)
                dialog.findViewById<View>(R.id.btn_tryagain).setOnClickListener {
                    val intent = Intent(this@Questions_Activity, MainActivity::class.java)
                    startActivity(intent)
                }
                dialog.show()
            }
        }
        (countDownTimer as CountDownTimer).start()
        setAllData()
    }

    private fun setAllData() {
        if (modelclass != null) {
            card_question?.text = modelclass!!.question
            optionOne?.text = modelclass!!.one
            optionTwo?.text = modelclass!!.two
            optionThree?.text = modelclass!!.three
            countDownTimer?.cancel()
            countDownTimer?.start()
        }
    }


    private fun Hook() {
        progressBar = findViewById(R.id.progressBar)
        progressBar?.max = totalTimeInMillis.toInt()

        card_question = findViewById(R.id.card_quastion)
        Log.d("TAG", "Question: " + modelclass?.question)

        optionOne = findViewById(R.id.card_optional_one)
        optionTwo = findViewById(R.id.card_optional_two)
        optionThree = findViewById(R.id.card_optional_three)

        cardOne = findViewById(R.id.card_one)
        cardTwo = findViewById(R.id.card_two)
        cardThree = findViewById(R.id.card_three)

        nextBtn = findViewById(R.id.nextBtn)
    }


    fun Correct(cardView: CardView?) {
        cardView!!.setBackgroundColor(resources.getColor(R.color.correct_answer))
        nextBtn!!.setOnClickListener {
            correctCount++
            index++
            modelclass = MainActivity.Companion.list.get(index)
            resetColor()
            setAllData()
            enableButton()
        }
    }

    fun Wrong(cardView: CardView?) {
        cardView!!.setBackgroundColor(resources.getColor(R.color.uncorrect_answer))
        nextBtn!!.setOnClickListener {
            wrongCount++
            if (index < MainActivity.Companion.list.size - 1) {
                index++
                modelclass = MainActivity.Companion.list.get(index)
                resetColor()
                setAllData()
                enableButton()
            } else {
                GameWon()
            }
        }
    }

    private fun GameWon() {
        val intent = Intent(this@Questions_Activity, WonActivity::class.java)
        intent.putExtra("correct", correctCount)
        intent.putExtra("wrong", wrongCount)
        startActivity(intent)
    }

    fun enableButton() {
        cardOne!!.isClickable = true
        cardTwo!!.isClickable = true
        cardThree!!.isClickable = true
    }

    fun disableButton() {
        cardOne!!.isClickable = false
        cardTwo!!.isClickable = false
        cardThree!!.isClickable = false
    }

    fun resetColor() {
        cardOne!!.setBackgroundColor(resources.getColor(R.color.white))
        cardTwo!!.setBackgroundColor(resources.getColor(R.color.white))
        cardThree!!.setBackgroundColor(resources.getColor(R.color.white))
    }

    fun OptionOneClick(view: View?) {
        disableButton()
        nextBtn!!.isClickable = true
        if (modelclass?.one == modelclass?.ans) {
            cardOne!!.setCardBackgroundColor(resources.getColor(R.color.correct_answer))
            if (index < MainActivity.Companion.list.size - 1) {
                Correct(cardOne)
            } else {
                GameWon()
            }
        } else {
            Wrong(cardOne)
        }
    }


    fun OptionTwoClick(view: View?) {
        disableButton()
        nextBtn!!.isClickable = true
        if (modelclass?.two == modelclass?.ans) {
            cardTwo!!.setCardBackgroundColor(resources.getColor(R.color.correct_answer))
            if (index < MainActivity.Companion.list.size - 1) {
                Correct(cardTwo)
            } else {
                GameWon()
            }
        } else {
            Wrong(cardTwo)
        }
    }

    fun OptionThreeClick(view: View?) {
        disableButton()
        nextBtn!!.isClickable = true
        if (modelclass?.three == modelclass?.ans) {
            cardThree!!.setCardBackgroundColor(resources.getColor(R.color.correct_answer))
            if (index < MainActivity.Companion.list.size - 1) {
                Correct(cardThree)
            } else {
                GameWon()
            }
        } else {
            Wrong(cardThree)
        }
    }
}