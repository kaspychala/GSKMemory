package com.gsk.memory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var cards: List<LinearLayout>
    private lateinit var cardsEmojis: List<TextView>
    private var isTwoCardsSet: Boolean = false
    private var isAllCardsSet: Boolean = false
    private var cardEmojiHolder: String? = null
    private var cardHolder: LinearLayout? = null
    private var emojis = mutableListOf(
        "📚",
        "💌",
        "🔒",
        "❤️",
        "⛔️",
        "♣️",
        "🛩",
        "🎮",
        "🚗",
        "⛱",
        "🏝",
        "⛰",
        "📷",
        "💣",
        "🔑",
        "🧸",
        "🎈"
    )


    override fun onClick(view: View?) {
        if (view is LinearLayout) {
            if (cardEmojiHolder is String && cardHolder is LinearLayout) {
                if (cardEmojiHolder == (view.children.first() as TextView).text.toString()) {
                    view.background = null
                    view.children.first().visibility = View.VISIBLE
                    tvScore.text = (tvScore.text.toString().toInt() + 1).toString() //Adding score when same cards appears.
                    view.isClickable = false
                    cardHolder?.let { it.isClickable = false }
                    cardHolder = null
                    cardEmojiHolder = null
                } else {
                    view.background = null
                    view.children.first().visibility = View.VISIBLE
                    Handler().postDelayed({
                        view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                        view.children.first().visibility = View.INVISIBLE
                        view.isClickable = true
                        cardHolder?.let {
                            it.setBackgroundColor(
                                ContextCompat.getColor(
                                    applicationContext,
                                    R.color.colorPrimary
                                )
                            )
                        }
                        cardHolder?.let { it.children.first().visibility = View.INVISIBLE }
                        cardHolder?.let { it.isClickable = true }
                        cardHolder = null
                        cardEmojiHolder = null
                    }, 500)
                }
            } else {
                if (view.background == null && view.children.first().visibility == View.VISIBLE) {
                    view.isClickable = true
                    view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                    view.children.first().visibility = View.INVISIBLE
                } else {
                    view.background = null
                    view.children.first().visibility = View.VISIBLE
                    view.isClickable = false
                    cardEmojiHolder = (view.children.first() as TextView).text.toString()
                    cardHolder = view
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prepareCards()
    }

    private fun prepareCards() {
        cards = listOf(
            cardOne,
            cardTwo,
            cardThree,
            cardFour,
            cardFive,
            cardSix,
            cardSeven,
            cardEight,
            cardNine,
            cardTen,
            cardEleven,
            cardTwelve
        )
        for (card in cards) {
            card.setOnClickListener(this)
        }

        cardsEmojis = listOf(
            cardEmojiOne,
            cardEmojiTwo,
            cardEmojiThree,
            cardEmojiFour,
            cardEmojiFive,
            cardEmojiSix,
            cardEmojiSeven,
            cardEmojiEight,
            cardEmojiNine,
            cardEmojiTen,
            cardEmojiEleven,
            cardEmojiTwelve
        )
        while (!isAllCardsSet) {
            if (cardsEmojis.all { it.text.isNotEmpty() }) {
                isAllCardsSet = true
            } else {
                isTwoCardsSet = false
                while (!isTwoCardsSet) {
                    var cardsEmojisIndex = Random.nextInt(0, cardsEmojis.size)
                    val emojiIndex = Random.nextInt(0, emojis.size)
                    if (cardsEmojis[cardsEmojisIndex].text.isEmpty()) {
                        cardsEmojis[cardsEmojisIndex].text = emojis[emojiIndex]
                        while (!isTwoCardsSet) {
                            cardsEmojisIndex = Random.nextInt(0, cardsEmojis.size)
                            if (cardsEmojis[cardsEmojisIndex].text.isEmpty()) {
                                cardsEmojis[cardsEmojisIndex].text = emojis[emojiIndex]
                                emojis.removeAt(emojiIndex)
                                isTwoCardsSet = true
                            }
                        }
                    }
                }
            }
        }
    }
}
