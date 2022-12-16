package com.example.sampleappwithmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.sampleappwithmvvm.adapters.NewsAdapter
import com.example.sampleappwithmvvm.viewmodel.NewsSharedViewModel
import com.example.sampleappwithmvvm.network.dto.NewsResponse
import com.example.utils.viewDataBinding
import com.sample.appwithmvvm.R
import com.sample.appwithmvvm.databinding.FragmentNewsListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewsListFragment : Fragment(R.layout.fragment_news_list) {
    private val newsAdapter = NewsAdapter(::onNewsItemClicked)

    private val viewModel by sharedViewModel<NewsSharedViewModel>()
    private val binding by viewDataBinding(FragmentNewsListBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNewsList.adapter = newsAdapter
        newsAdapter.submitList(viewModel.newsList)

        postponeEnterTransition()

        binding.rvNewsList.doOnPreDraw {
            startPostponedEnterTransition()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun onNewsItemClicked(newsItem: NewsResponse, sharedElement: View) {
        val direction: NavDirections =
            NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment()
        viewModel.chosenNews = newsItem
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            val extras = FragmentNavigatorExtras(sharedElement to sharedElement.transitionName)
            findNavController().navigate(direction, extras)
        }
    }
}