package com.example.sampleappwithmvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.sampleappwithmvvm.viewmodel.NewsSharedViewModel
import com.example.utils.navigate
import com.sample.appwithmvvm.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by sharedViewModel<NewsSharedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.stateFlow.collect {
                when (it) {
                    is NewsSharedViewModel.NewsListLoadState.Success -> {
                        navigate(SplashFragmentDirections.actionSplashFragmentToNewsListFragment())
                    }
                    is NewsSharedViewModel.NewsListLoadState.Error -> {
                        Toast.makeText(requireContext(), it.ex.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }
                    else -> {}
                }
            }
        }
    }
}