package com.globus.testkmm.android

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globus.testkmm.model.Article

class ArticleAdapter (
    private val onClick: (Article) -> Unit
) : ListAdapter<Article, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.source?.id == newItem.source?.id
        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem.content == newItem.content
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.article_item, parent, false
        )
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { article ->
            with(holder.itemView){
                findViewById<TextView>(R.id.sourceId).text = article.source?.id ?: ""
                findViewById<TextView>(R.id.name).text = article.source?.name ?: ""
                findViewById<TextView>(R.id.author).text = article.author ?: ""
                findViewById<TextView>(R.id.title).text = article.title ?: ""
                findViewById<TextView>(R.id.description).text = article.description
                findViewById<TextView>(R.id.url).text = article.url ?: ""
                findViewById<TextView>(R.id.publishedAt).text = article.publishedAt ?: ""
                findViewById<TextView>(R.id.content).text = article.content ?: ""
            }

            holder.itemView.setOnClickListener {
                onClick(article)
            }
        }
    }
}