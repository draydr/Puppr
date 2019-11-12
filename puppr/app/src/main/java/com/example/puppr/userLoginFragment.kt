package com.example.puppr


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.puppr.databinding.FragmentSignupOptionsBinding
import com.example.puppr.databinding.FragmentUserLoginBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class userLoginFragment : Fragment() {
    private lateinit var binding: FragmentUserLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentUserLoginBinding>(
            inflater,
            R.layout.fragment_signup_options, container, false
        )
        auth = FirebaseAuth.getInstance()
        return binding.root
    }


}
