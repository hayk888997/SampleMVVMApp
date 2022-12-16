package com.example.sampleappwithmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappwithmvvm.network.dto.NewsResponse
import com.example.utils.viewBinding
import com.sample.appwithmvvm.R
import com.sample.appwithmvvm.databinding.NewsListItemBinding

class NewsAdapter(
    val onItemClicked: ((newsItem: NewsResponse, sharedElement : View) -> Unit),
) : ListAdapter<NewsResponse, NewsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.news_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bindingItem by viewBinding(NewsListItemBinding::bind)

        fun bind(item: NewsResponse) = with(bindingItem) {
            tvTitle.text = item.webTitle
            tvSectionName.text = item.sectionName
            tvSectionName.transitionName = item.id
            itemLayout.setOnClickListener {
                onItemClicked(item, tvSectionName)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NewsResponse>() {
        override fun areItemsTheSame(oldItem: NewsResponse, newItem: NewsResponse): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: NewsResponse, newItem: NewsResponse): Boolean {
            return oldItem == newItem
        }
    }
}