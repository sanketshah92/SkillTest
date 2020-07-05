package com.sample.skilltest.search.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.skilltest.databinding.ItemSearchBinding
import com.sample.skilltest.search.model.Data
import com.sample.skilltest.search.model.Images
import com.sample.skilltest.search.model.LocalDataMapper
import java.lang.ref.WeakReference

class SearchAdapter(
    private val data: List<LocalDataMapper>,
    private val listener: OnImageSelectionListener
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    lateinit var context: WeakReference<Context>

    class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.imgSearchItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater)
        context = WeakReference(parent.context)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context.get()!!)
            .load(data[position].imageUrl)
            .override(data[position].imageWidth, data[position].imageHeight)
            .into(holder.imageView)
        holder.imageView.setOnClickListener {
            listener.onImageSelected(position, data[position])
        }
    }
}

interface OnImageSelectionListener {
    fun onImageSelected(pos: Int, data: LocalDataMapper)
}