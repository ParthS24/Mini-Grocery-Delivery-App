package com.minigrocery.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.minigrocery.app.MiniGroceryApplication
import com.minigrocery.app.databinding.FragmentProfileBinding
import com.minigrocery.app.ui.auth.LoginActivity
import com.minigrocery.app.utils.launchWhenStarted
import com.minigrocery.app.utils.showToast
import kotlinx.coroutines.launch

/**
 * Profile Fragment
 */
class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(
            (requireActivity().application as MiniGroceryApplication).userRepository
        )
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupObservers()
        loadUserData()
    }
    
    private fun setupUI() {
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnEditProfile.setOnClickListener {
            showToast("Edit profile feature coming soon")
        }
        
        binding.btnMyOrders.setOnClickListener {
            showToast("My orders feature coming soon")
        }
        
        binding.btnMyAddresses.setOnClickListener {
            showToast("My addresses feature coming soon")
        }
        
        binding.btnPaymentMethods.setOnClickListener {
            showToast("Payment methods feature coming soon")
        }
        
        binding.btnNotifications.setOnClickListener {
            showToast("Notifications feature coming soon")
        }
        
        binding.btnHelpSupport.setOnClickListener {
            showToast("Help & support feature coming soon")
        }
        
        binding.btnAbout.setOnClickListener {
            showToast("About feature coming soon")
        }
        
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }
    
    private fun setupObservers() {
        launchWhenStarted {
            viewModel.userProfile.collect { profile ->
                updateUI(profile)
            }
        }
    }
    
    private fun loadUserData() {
        viewModel.loadUserProfile()
    }
    
    private fun updateUI(profile: UserProfile) {
        binding.apply {
            tvUserName.text = profile.name ?: "Guest User"
            tvUserPhone.text = profile.phone ?: ""
            tvUserEmail.text = profile.email ?: "Not provided"
            
            if (profile.name.isNullOrBlank()) {
                tvInitials.text = "GU"
            } else {
                val names = profile.name.split(" ")
                val initials = names.map { it.first().uppercase() }.joinToString("")
                tvInitials.text = initials.take(2)
            }
        }
    }
    
    private fun logout() {
        lifecycleScope.launch {
            viewModel.logout()
            showToast("Logged out successfully")
            
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
