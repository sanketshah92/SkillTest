package com.sample.skilltest.search.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.sample.skilltest.R
import com.sample.skilltest.network.NetworkHelper
import com.sample.skilltest.network.RetrofitBuilder
import com.sample.skilltest.network.Status
import com.sample.skilltest.search.model.LocalDataMapper
import com.sample.skilltest.search.viewmodel.SearchViewModel
import com.sample.skilltest.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private var searchHandler: Handler = Handler()
    private var searchRunnable: Runnable? = null
    private var searchText: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProviders.of(
                this,
                ViewModelFactory(NetworkHelper(RetrofitBuilder.apiService), requireContext())
            )
                .get(SearchViewModel::class.java)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchText = newText
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable)
                }
                searchRunnable = Runnable {
                    searchText?.let {
                        viewModel.doSearch(it, "1")
                    }
                }
                searchHandler.postDelayed(searchRunnable, 250)
                return false
            }

        })

        viewModel.searchDataObserver.observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    rvSearchResult.apply {
                        adapter = SearchAdapter(
                            viewModel.mapDataToLocal(
                                it.data?.data!!,
                                (getDeviceWidth() / 2).toInt(), (getDeviceWidth() / 2).toInt()
                            ), object : OnImageSelectionListener {
                                override fun onImageSelected(pos: Int, data: LocalDataMapper) {
                                    val action =
                                        SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                                            data
                                        )
                                    findNavController().navigate(action)
                                }
                            }
                        )
                        progress.visibility = View.GONE
                    }
                }
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                }
                else -> {
                    progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun getDeviceWidth(): Float {
        return requireContext().resources.displayMetrics.widthPixels / requireContext().resources.displayMetrics.density
    }
}