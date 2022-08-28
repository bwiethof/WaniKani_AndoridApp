package org.bemi.wanikanisrsapp.ui.profile

import android.content.Intent
import android.media.session.MediaSession
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.bemi.wanikanisrsapp.TokenStore
import org.bemi.wanikanisrsapp.authentication.UserTokenActivity
import org.bemi.wanikanisrsapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private var _token: String? = TokenStore.get()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this)[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonSetToken.visibility = GONE
        binding.buttonSetToken.setOnClickListener {
            requireActivity().run {
                val intent = Intent(this, UserTokenActivity::class.java)
                startActivity(intent)
            }
        }

        val textView: TextView = binding.textProfile
        if (_token != null) {
            textView.text = "Your user token is: ${TokenStore.get()}"
        } else {
            textView.text = "No user token available."
            binding.buttonSetToken.visibility = VISIBLE
        }

        return root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}