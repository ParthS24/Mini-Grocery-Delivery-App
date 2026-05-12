package com.minigrocery.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.minigrocery.app.MiniGroceryApplication
import com.minigrocery.app.databinding.ActivityLoginBinding
import com.minigrocery.app.MainActivity
import com.minigrocery.app.utils.isValidPhoneNumber
import com.minigrocery.app.utils.showToast
import kotlinx.coroutines.launch

/**
 * Login Activity for phone number authentication
 */
class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as MiniGroceryApplication).userRepository)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        // Set country code
        binding.etCountryCode.setText("+91")
        
        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(s: Editable?, start: Int, before: Int, count: Int) {
                validatePhoneNumber()
            }
        })
        
        binding.btnContinue.setOnClickListener {
            handleContinueClick()
        }
        
        binding.btnGuest.setOnClickListener {
            handleGuestClick()
        }
    }
    
    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Idle -> {
                        hideLoading()
                    }
                    is LoginState.Loading -> {
                        showLoading()
                    }
                    is LoginState.Success -> {
                        hideLoading()
                        navigateToOtp(state.phoneNumber)
                    }
                    is LoginState.Error -> {
                        hideLoading()
                        showToast(state.message)
                    }
                    is LoginState.OtpVerified -> {
                        hideLoading()
                        navigateToHome()
                    }
                }
            }
        }
    }
    
    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val isValid = phoneNumber.isValidPhoneNumber()
        
        binding.btnContinue.isEnabled = isValid
        binding.tilPhoneNumber.error = if (isValid) null else com.minigrocery.app.utils.Constants.ERROR_INVALID_PHONE
        
        return isValid
    }
    
    private fun handleContinueClick() {
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        
        if (validatePhoneNumber()) {
            viewModel.sendOtp(phoneNumber)
        } else {
            showToast(com.minigrocery.app.utils.Constants.ERROR_INVALID_PHONE)
        }
    }
    
    private fun handleGuestClick() {
        navigateToHome()
    }
    
    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.btnContinue.isEnabled = false
        binding.btnGuest.isEnabled = false
    }
    
    private fun hideLoading() {
        binding.progressBar.isVisible = false
        binding.btnContinue.isEnabled = true
        binding.btnGuest.isEnabled = true
    }
    
    private fun navigateToOtp(phoneNumber: String) {
        val intent = Intent(this, OtpActivity::class.java)
        intent.putExtra(com.minigrocery.app.utils.Constants.EXTRA_USER_PHONE, phoneNumber)
        startActivity(intent)
    }
    
    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
