package org.bemi.wanikanisrsapp.ui.userToken

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import org.bemi.wanikanisrsapp.ui.main.DashboardActivity
import org.bemi.wanikanisrsapp.R
import org.bemi.wanikanisrsapp.data.TokenStore
import org.bemi.wanikanisrsapp.databinding.FragmentEnterTokenBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EnterTokenFragment : Fragment() {

    private var _binding: FragmentEnterTokenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEnterTokenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSetToken.setOnClickListener {
            var userInput = binding.textInputUserToken.editText?.text
            if (userInput.isNullOrEmpty()) {
                Snackbar.make(requireActivity().findViewById(R.id.nav_host_fragment_content_user_token), "No Token entered", Snackbar.LENGTH_LONG).show()
            } else {
                TokenStore.token = userInput.toString()
                requireActivity().run {
                    Snackbar.make(view, "User Token updated", Snackbar.LENGTH_LONG).show()
                    val intent = Intent(this, DashboardActivity::class.java).putExtra("TOKEN_UPDATED", true)
                    startActivity(intent)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}