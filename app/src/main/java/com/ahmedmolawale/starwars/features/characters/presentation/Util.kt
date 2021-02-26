package com.ahmedmolawale.starwars.features.characters.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmedmolawale.starwars.R
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Note: Data being returned that is expected to be numbers can sometimes be a string of characters
 **/
fun convertCMToInches(centimeters: String): String {
    return try {
        (
            BigDecimal(centimeters.toDouble() * 0.393701).setScale(
                3,
                RoundingMode.HALF_EVEN
            )
            ).toString()
    } catch (e: NumberFormatException) {
        "unknown"
    }
}

fun populationValue(population: String): String {
    return try {
        population.toLong().toString()
    } catch (e: NumberFormatException) {
        "unknown"
    }
}

fun extractInitials(name: String): String {
    if (name.trim().isBlank()) return ""
    val s = name.trim().split(" ")
    if (s.isNotEmpty()) {
        return if (s.size == 1) {
            s[0][0].toString().toUpperCase()
        } else {
            s[0][0].toString().plus(s[1][0].toString()).toUpperCase()
        }
    }
    return ""
}

@SuppressLint("UseCompatLoadingForDrawables")
fun RecyclerView.initRecyclerViewWithLineDecoration(context: Context) {
    val linearLayoutManager = LinearLayoutManager(context)
    val itemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation).apply {
        setDrawable(context.getDrawable(R.drawable.line)!!)
    }
    layoutManager = linearLayoutManager
    addItemDecoration(itemDecoration)
}
