package com.udacity.shoestore.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.login_fragment, container, false
        )
        binding.controller = this
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        return binding.root
    }

    fun login() {
        if (loginViewModel.login(binding.loginEdittext.text.toString(), binding.passwordEdittext.text.toString())) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
        } else {
            showLoginError()
        }
    }

    fun register() {
        login()
    }

    private fun showLoginError() {
        binding.loginEdittext.error = "Login and password length should be at least 5 characters!"
    }

}