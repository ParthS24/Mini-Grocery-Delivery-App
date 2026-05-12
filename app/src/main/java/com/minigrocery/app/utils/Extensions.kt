package com.minigrocery.app.utils

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat

/**
 * Extension functions for common operations
 */

// View Extensions
fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

// ImageView Extensions
fun ImageView.loadImage(url: String?, placeholder: Int = android.R.drawable.ic_menu_gallery) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeholder)
        .error(placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(this)
}

// Fragment Extensions
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

// Activity Extensions
fun android.app.Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

fun Fragment.launchWhenStarted(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        block()
    }
}

// LifecycleOwner Extensions
fun LifecycleOwner.launchWhenStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        block()
    }
}

// String Extensions
fun String.formatCurrency(): String {
    return "₹${DecimalFormat("#,##0.00").format(this.toDoubleOrNull() ?: 0.0)}"
}

fun String.isValidPhoneNumber(): Boolean {
    return this.matches(Regex(Constants.PHONE_PATTERN))
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isNotNullOrEmpty(): Boolean {
    return this.isNotEmpty() && this.isNotBlank()
}

// Double Extensions
fun Double.formatCurrency(): String {
    return "₹${DecimalFormat("#,##0.00").format(this)}"
}

// Int Extensions
fun Int.formatCurrency(): String {
    return "₹${DecimalFormat("#,##0").format(this)}"
}
