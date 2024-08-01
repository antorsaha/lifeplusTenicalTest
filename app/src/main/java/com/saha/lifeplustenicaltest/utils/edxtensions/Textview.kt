package com.saha.lifeplustenicaltest.utils.edxtensions

import android.text.Html
import android.widget.TextView

fun TextView.loadHtml(htmlText: String) {
    this.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
}