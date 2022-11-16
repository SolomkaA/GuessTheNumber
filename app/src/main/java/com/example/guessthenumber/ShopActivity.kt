package com.example.guessthenumber

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val sharedPref = applicationContext.getSharedPreferences("STORED_COINS", Context.MODE_PRIVATE)
        val shThemes = applicationContext.getSharedPreferences("THEMES", Context.MODE_PRIVATE)

        val sharedPrefEditor = sharedPref.edit()
        val shThemesEditor = shThemes.edit()

        val recyclerView = findViewById<RecyclerView>(R.id.shopThemes)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val coinsTextView = findViewById<TextView>(R.id.shopCoinsValue)

        var coins = sharedPref.getInt("coins", 0)

        coinsTextView.text = coins.toString()

        var listOfThemes =  ArrayList<ThemesList>()

        listOfThemes.add(ThemesList("Yellow/Blue", 300, false, shThemes.getInt("0", 0)))
        listOfThemes.add(ThemesList("Black/Red", 200, false, shThemes.getInt("1", 0)))
        listOfThemes.add(ThemesList("Black/White", 400, false, shThemes.getInt("2", 0)))


        val adapter = ThemesAdapter(listOfThemes, this)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object: ThemesAdapter.onItemClickListener{

            override fun onItemClick(
                position: Int,
                themeName: TextView,
                themePrice: TextView,
                checkBox: CheckBox
            ) {
                if (listOfThemes[position].price > coins && listOfThemes[position].state == 0){
                    Toast.makeText(this@ShopActivity, "Not Enough Coins!", Toast.LENGTH_SHORT).show()
                }
                if (listOfThemes[position].price <= coins && listOfThemes[position].state == 0){
                    listOfThemes[position].state = 1
                    coins -= listOfThemes[position].price
                    sharedPrefEditor.putInt("coins", coins).apply()
                    shThemesEditor.putInt("$position", 1).apply()
                    coinsTextView.text = coins.toString()
                    adapter.notifyDataSetChanged()
                }
                if (listOfThemes[position].state != 0){
                    if(listOfThemes[position].state == 1){
                        listOfThemes[position].checkBox = true
                        listOfThemes[position].state = 2
                        shThemesEditor.putInt("$position", 2).apply()
                        adapter.notifyDataSetChanged()
                    }
                    else{
                        listOfThemes[position].checkBox = false
                        listOfThemes[position].state = 1
                        shThemesEditor.putInt("$position", 1).apply()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}