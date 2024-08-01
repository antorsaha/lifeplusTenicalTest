package com.saha.lifeplustenicaltest.view.components.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.saha.lifeplustenicaltest.R
import com.saha.lifeplustenicaltest.data.model.ResponseSearchItem
import com.saha.lifeplustenicaltest.databinding.ItemSearchResultBinding
import com.saha.lifeplustenicaltest.utils.edxtensions.loadImage

class SearchResultAdapter(
    private val context: Context,
    private var data: MutableList<ResponseSearchItem> = mutableListOf(),
    private val onItemClick: (ResponseSearchItem, Int) -> Unit
): RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binder = itemView

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(item: ResponseSearchItem, position: Int) {

            binder.tvItemTitle.text = item.show?.name
            binder.tvRating.text = item.show?.rating?.average.toString()

            item.show?.image?.medium?.let {
                binder.ivItemImage.loadImage(it, R.drawable.image_placeholder_icon)
            }

            binder.root.setOnClickListener {
                onItemClick.invoke(item, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    fun setData(data: MutableList<ResponseSearchItem>){
        this.data = data
        notifyDataSetChanged()
    }
}