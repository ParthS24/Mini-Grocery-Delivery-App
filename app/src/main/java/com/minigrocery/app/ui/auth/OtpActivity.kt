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
import com.minigrocery.app.databinding.ActivityOtpBinding
import com.minigrocery.app.MainActivity
import com.minigrocery.app.utils.Constants
import com.minigrocery.app.utils.IntentConstants
import com.minigrocery.app.utils.showToast
import kotlinx.coroutines.launch

/**
 * OTP Activity for verifying phone number
 */
class OtpActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityOtpBinding
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as MiniGroceryApplication).userRepository)
    }
    
    private var phoneNumber = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        phoneNumber = intent.getStringExtra(IntentConstants.EXTRA_USER_PHONE) ?: ""
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        // Set phone number display
        binding.tvPhoneNumber.text = "+91 $phoneNumber"
        
        // Setup OTP input fields
        setupOtpInputs()
        
        // Set click listeners
        binding.btnVerify.setOnClickListener {
            handleVerifyClick()
        }
        
        binding.btnResend.setOnClickListener {
            resendOtp()
        }
        
        binding.ivBack.setOnClickListener {
            finish()
        }
        
        // Start countdown timer
        startResendTimer()
    }
    
    private fun setupOtpInputs() {
        val otpInputs = arrayOf(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4
        )
        
        // Add text watchers for auto-focus and validation
        otpInputs.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        // Move to next field
                        if (index < otpInputs.size - 1) {
                            otpInputs[index + 1].requestFocus()
                        }
                    } else if (s?.length == 0 && index > 0) {
                        // Move to previous field on backspace
                        otpInputs[index - 1].requestFocus()
                    }
                    
                    // Enable verify button when all fields are filled
                    validateOtpInputs()
                }
                
                override fun afterTextChanged(s: Editable?) {}
            })
        }
        
        // Focus on first field
        otpInputs[0].requestFocus()
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
                    is LoginState.OtpVerified -> {
                        hideLoading()
                        navigateToHome()
                    }
                    is LoginState.Error -> {
                        hideLoading()
                        showToast(state.message)
                        clearOtpInputs()
                    }
                    else -> {}
                }
            }
        }
    }
    
    private fun validateOtpInputs(): Boolean {
        val otp1 = binding.etOtp1.text.toString()
        val otp2 = binding.etOtp2.text.toString()
        val otp3 = binding.etOtp3.text.toString()
        val otp4 = binding.etOtp4.text.toString()
        
        val isComplete = otp1.isNotEmpty() && otp2.isNotEmpty() && 
                        otp3.isNotEmpty() && otp4.isNotEmpty()
        
        binding.btnVerify.isEnabled = isComplete
        
        return isComplete
    }
    
    private fun getOtpCode(): String {
        return "${binding.etOtp1.text}${binding.etOtp2.text}${binding.etOtp3.text}${binding.etOtp4.text}"
    }
    
    private fun handleVerifyClick() {
        if (validateOtpInputs()) {
            val otp = getOtpCode()
            viewModel.verifyOtp(phoneNumber, otp)
        }
    }
    
    private fun clearOtpInputs() {
        binding.etOtp1.setText("")
        binding.etOtp2.setText("")
        binding.etOtp3.setText("")
        binding.etOtp4.setText("")
        binding.etOtp1.requestFocus()
    }
    
    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.btnVerify.isEnabled = false
        binding.btnResend.isEnabled = false
    }
    
    private fun hideLoading() {
        binding.progressBar.isVisible = false
        binding.btnVerify.isEnabled = validateOtpInputs()
        binding.btnResend.isEnabled = true
    }
    
    private fun resendOtp() {
        viewModel.sendOtp(phoneNumber)
        showToast("OTP sent successfully")
        startResendTimer()
    }
    
    private fun startResendTimer() {
        binding.btnResend.isEnabled = false
        var countdown = 30
        
        val timer = object : android.os.CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdown--
                binding.btnResend.text = "Resend OTP (${countdown}s)"
            }
            
            override fun onFinish() {
                binding.btnResend.isEnabled = true
                binding.btnResend.text = "Resend OTP"
            }
        }
        
        timer.start()
    }
    
    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
