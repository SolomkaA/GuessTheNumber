package com.example.guessthenumber

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    
    private var isFront = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val sharedPref = applicationContext.getSharedPreferences("STORED_COINS", Context.MODE_PRIVATE)

        val sharedPrefEditor = sharedPref.edit()

        val scale = applicationContext.resources.displayMetrics.density

        val front = findViewById<TextView>(R.id.card_front)
        val back = findViewById<TextView>(R.id.card_back)

        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale

        val frontAnimation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        val backAnimation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        val enterNumber = findViewById<EditText>(R.id.enterNumber)
        var coinsVal: Int
        val coinsTextView = findViewById<TextView>(R.id.coinsValue)

        if (!sharedPref.contains("coins")){
            coinsVal = 0
            sharedPrefEditor.putInt("coins", coinsVal).apply()
        }
        else{
            coinsVal = sharedPref.getInt("coins", 0)
        }
        coinsTextView.text = coinsVal.toString()


        val exactNumber = findViewById<Button>(R.id.exactValueButton)
        exactNumber.setOnClickListener {
            if (validateNumber(enterNumber)){
                val number = generateNumber()
                setNumber(front, back, number)
                cardNumberAnimation(front, frontAnimation, back, backAnimation)
                if (number == getNumber(enterNumber)){
                    Toast.makeText(this, R.string.correctResult, Toast.LENGTH_SHORT).show()
                    coinsVal += 50
                    sharedPrefEditor.putInt("coins", coinsVal).apply()
                    coinsTextView.text = coinsVal.toString()
                }
                else{
                    Toast.makeText(this, R.string.wrongResult, Toast.LENGTH_SHORT).show()
                }
            }
        }

        val lessThenNumber = findViewById<Button>(R.id.lessThenButton)
        lessThenNumber.setOnClickListener {
            if (validateNumber(enterNumber)){
                val number = generateNumber()
                setNumber(front, back, number)
                cardNumberAnimation(front, frontAnimation, back, backAnimation)
                if (number < getNumber(enterNumber)){
                    Toast.makeText(this, R.string.correctResult, Toast.LENGTH_SHORT).show()
                    coinsVal += 5
                    sharedPrefEditor.putInt("coins", coinsVal).apply()
                    coinsTextView.text = coinsVal.toString()
                }
                else{
                    Toast.makeText(this, R.string.wrongResult, Toast.LENGTH_SHORT).show()
                }
            }
        }

        val moreThenNumber = findViewById<Button>(R.id.moreThenButton)
        moreThenNumber.setOnClickListener {
            if (validateNumber(enterNumber)){
                val number = generateNumber()
                setNumber(front, back, number)
                cardNumberAnimation(front, frontAnimation, back, backAnimation)
                if (number > getNumber(enterNumber)){
                    Toast.makeText(this, R.string.correctResult, Toast.LENGTH_SHORT).show()
                    coinsVal += 5
                    sharedPrefEditor.putInt("coins", coinsVal).apply()
                    coinsTextView.text = coinsVal.toString()
                }
                else{
                    Toast.makeText(this, R.string.wrongResult, Toast.LENGTH_SHORT).show()
                }
            }
        }

        val evenNumber = findViewById<Button>(R.id.evenButton)
        evenNumber.setOnClickListener {
            val number = generateNumber()
            setNumber(front, back, number)
            cardNumberAnimation(front, frontAnimation, back, backAnimation)
            if (number % 2 == 0){
                Toast.makeText(this, R.string.correctResult, Toast.LENGTH_SHORT).show()
                coinsVal += 10
                sharedPrefEditor.putInt("coins", coinsVal).apply()
                coinsTextView.text = coinsVal.toString()
            }
            else{
                Toast.makeText(this, R.string.wrongResult, Toast.LENGTH_SHORT).show()
            }
        }

        val oddNumber = findViewById<Button>(R.id.oddButton)
        oddNumber.setOnClickListener {
            val number = generateNumber()
            setNumber(front, back, number)
            cardNumberAnimation(front, frontAnimation, back, backAnimation)
            if (number % 2 != 0){
                Toast.makeText(this, R.string.correctResult, Toast.LENGTH_SHORT).show()
                coinsVal += 10
                sharedPrefEditor.putInt("coins", coinsVal).apply()
                coinsTextView.text = coinsVal.toString()
            }
            else{
                Toast.makeText(this, R.string.wrongResult, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun cardNumberAnimation(front: TextView,
                                    frontAnimation: AnimatorSet,
                                    back: TextView,
                                    backAnimation: AnimatorSet){
        if(isFront)
        {
            frontAnimation.setTarget(front)
            backAnimation.setTarget(back)
            frontAnimation.start()
            backAnimation.start()
            isFront = false

        }
        else
        {
            frontAnimation.setTarget(back)
            backAnimation.setTarget(front)
            backAnimation.start()
            frontAnimation.start()
            isFront = true
        }
    }

    private fun setNumber(front: TextView, back: TextView, number: Int){
        if(isFront)
        {
            back.text = number.toString()
        }
        else
        {
            front.text = number.toString()
        }
    }


}

fun generateNumber(): Int {
    return (0..99).random()
}

fun getNumber(entNum: EditText): Int{
    return entNum.text.toString().toInt()
}

fun validateNumber(entNum: EditText): Boolean{
    val enteredNum: Int = if (entNum.text.toString() == "") -1
    else {
        entNum.text.toString().toInt()
    }

    return if (enteredNum < 0 || enteredNum  > 99){
        entNum.setText("")
        entNum.setHint(R.string.wrongNum)
        entNum.setHintTextColor(Color.RED)
        false
    }
    else{
        true
    }
}


