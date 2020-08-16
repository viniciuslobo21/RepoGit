package com.lobo.repogit.core.extension

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.Normalizer
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

private const val PHONE_NUMBER_PATTERN = "^\\([1-9]{2}\\) 9 [0-9]{4} [0-9]{4}"

fun String.validatePhoneNumber(): Boolean {
    return this.matches(PHONE_NUMBER_PATTERN.toRegex())
}

fun String?.removeNull(): String = this ?: ""

fun Double.toBrCurrency(): String {
    val nf = NumberFormat.getInstance(Locale.GERMANY)
    nf.minimumFractionDigits = 2
    return "R$ ${nf.format(this)}"
}

fun Int.toBrCurrencyWithoutDecimal(): String {
    val nf = NumberFormat.getInstance(Locale.GERMANY)
    return "R$ ${nf.format(this)}"
}

fun String.removeBrCurrency(): String {
    return this.replace("R$ ", "")
}

@SuppressLint("SimpleDateFormat")
fun String.toDateUSAFormat(): String {
    val pattern = "yyyy/mm/dd"
    val format = SimpleDateFormat(pattern)
    return format.format(this)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun String.removeAccentuation(): String {
    val regexUnaccent = "[^\\p{ASCII}]".toRegex()
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return regexUnaccent.replace(temp, "")
}

fun String.removeSpecialCharacters(): String {
    val re = Regex("[^A-Za-z ]")
    return re.replace(this, " ")
}

fun String.CPFMask(): String {
    return this.substring(0, 3) + "." + this.substring(3, 6) + "." + this.substring(
        6,
        9
    ) + "-" + this.substring(9, 11)
}

fun Double.toPercentage(decimalPlaces: Int): String {
    val insuranceFormat =
        when {
            decimalPlaces == 1 -> DecimalFormat("##.#")
            decimalPlaces >= 2 -> DecimalFormat("##.##")
            else -> DecimalFormat("##")
        }

    return insuranceFormat.format(this).toString().plus("%")
        .replace(".", ",")
}

fun String.unmask(): String {
    return this.replace("[\\W]*".toRegex(), "")
}

fun String?.cutDecimals(): Double? {
    return this?.toBigDecimal()?.setScale(2, RoundingMode.DOWN)?.toDouble()
}

fun <T> T.toDeferred() = GlobalScope.async { this@toDeferred }

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

