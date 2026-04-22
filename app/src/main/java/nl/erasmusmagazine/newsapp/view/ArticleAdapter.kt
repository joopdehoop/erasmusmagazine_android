package nl.erasmusmagazine.newsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.erasmusmagazine.newsapp.databinding.ItemArticleBinding
import nl.erasmusmagazine.newsapp.model.Article

class ArticleAdapter(
    private val onArticleClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private val items = mutableListOf<Article>()

    fun submit(newItems: List<Article>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding, onArticleClick)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ArticleViewHolder(
        private val binding: ItemArticleBinding,
        private val onArticleClick: (Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.titleText.text = item.title
            binding.metaText.text = "${item.author} • ${item.publishedAt}"
            binding.root.setOnClickListener { onArticleClick(item) }
        }
    }
}
