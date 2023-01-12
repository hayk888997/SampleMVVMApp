package com.example.sampleappwithmvvm.view

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sampleappwithmvvm.viewmodel.NewsSharedViewModel
import com.example.utils.viewBinding
import com.sample.appwithmvvm.R
import com.sample.appwithmvvm.databinding.FragmentNewsDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit

class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private val viewModel by sharedViewModel<NewsSharedViewModel>()
    private val binding by viewBinding(FragmentNewsDetailsBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(TRANSITION_DELAY, TimeUnit.MILLISECONDS)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNewsTitle.text = viewModel.chosenNews?.sectionName
        binding.tvNewsTitle.transitionName = viewModel.chosenNews?.id

        viewModel.chosenNews?.webUrl?.let { binding.wvNews.loadUrl(it) }
        CoroutineScope(Dispatchers.Main).launch {
            delay(WEB_VIEW_VISIBILITY_DELAY)
            binding.wvNews.visibility = View.VISIBLE
        }
    }

    companion object {
        const val TRANSITION_DELAY = 50L
        const val WEB_VIEW_VISIBILITY_DELAY = 500L
    }

}