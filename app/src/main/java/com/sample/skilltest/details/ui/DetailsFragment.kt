package com.sample.skilltest.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.sample.skilltest.R
import com.sample.skilltest.details.viewmodel.DetailsViewModel
import com.sample.skilltest.network.NetworkHelper
import com.sample.skilltest.network.RetrofitBuilder
import com.sample.skilltest.network.Status
import com.sample.skilltest.search.model.LocalDataMapper
import com.sample.skilltest.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private lateinit var viewModel: DetailsViewModel
    private lateinit var localDataMapper: LocalDataMapper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(
                this,
                ViewModelFactory(NetworkHelper(RetrofitBuilder.apiService), requireContext())
            ).get(DetailsViewModel::class.java)

        arguments?.let {
            val args = DetailsFragmentArgs.fromBundle(it)
            localDataMapper = args.imageData
            Glide.with(requireContext())
                .load(localDataMapper.imageUrl)
                .override(imgDetails.width, imgDetails.height)
                .into(imgDetails)

            viewModel.getComments(localDataMapper.id)
        }


        btnAddComment.setOnClickListener {
            viewModel.addComments(edtCommment.text.toString(), localDataMapper.id)
        }
        viewModel.comments.observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.size!! > 1) {
                        rvComments.apply {
                            adapter = CommentsAdapter(it.data)
                        }
                    }
                }
            }
        })

        imgClose.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}