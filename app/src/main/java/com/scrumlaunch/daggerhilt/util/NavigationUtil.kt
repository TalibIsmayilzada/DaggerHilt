package com.scrumlaunch.daggerhilt.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object NavigationUtil {

    const val HTTP = "http://"
    const val HTTPS = "https://"

    fun navigateToBrowser(context: Context, url: String) {
        if (url.startsWith(HTTP) || url.startsWith(HTTPS)) {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }
    }
}