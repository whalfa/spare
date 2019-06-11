package com.example.carspareparts.pendingorder

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.carspareparts.R
import kotlinx.android.synthetic.main.activity_spare_part_products.*
import kotlinx.android.synthetic.main.pending_fragment.*

class PendingFragment : Fragment() {

    companion object {
        fun newInstance() = PendingFragment()
    }

    private lateinit var viewModel: PendingViewModel
    private lateinit var ordersAdapter: OrdersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pending_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PendingViewModel::class.java)
        viewModel.getPendingOrders()
        pendingOrdersRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getListOrdersLiveData().observe(this, Observer {
            if (it!=null){
                ordersAdapter=OrdersAdapter(it,{true})
                pendingOrdersRecyclerView.adapter=ordersAdapter
            }

        })
    }

}