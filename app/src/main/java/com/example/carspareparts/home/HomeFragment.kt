package com.example.carspareparts.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carspareparts.R
import com.example.carspareparts.main.MainActivity
import com.example.carspareparts.resetpassword.ResetPasswordActivity
import com.example.carspareparts.sparepartproducts.SparePartProductsFragment
import com.parse.ParseException
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.cart_details.*
import kotlinx.android.synthetic.main.home_fragment.*
import android.R.id
import android.widget.Button
import android.R.attr.action

import android.widget.ImageButton
import com.example.carspareparts.ConnectionLiveData
import com.example.carspareparts.CustomCart
import kotlinx.android.synthetic.main.content_home.*


class HomeFragment : Fragment() {

    private lateinit var sparePartTypeAdapter: SparePartTypeAdapter
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private val fragment:Fragment by lazy {
        SparePartProductsFragment.newInstance()
    }
    companion object {
        fun newInstance() = HomeFragment()


    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.carspareparts.R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeFragmentViewModel= ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)
        ConnectionLiveData.observe(this, Observer {
            if (it!=null&&it.isConnected){
                homeFragmentViewModel.getSparePartTypeList()
            }
        })
        listOfSparePartTypRecyclerView.layoutManager = LinearLayoutManager(activity)
        homeFragmentViewModel.getSparePartTypeLiveData().observe(this, Observer {
            if(it !is ParseException){
                homeProgressBar.visibility=View.GONE
            sparePartTypeAdapter = SparePartTypeAdapter(it) {
                val bundle = Bundle()
                bundle.putString("objectId", it.first)
                bundle.putString("typeName", it.second)
                fragment.arguments=bundle
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.add(R.id.fragmentPlaceholder, fragment)
                fragmentTransaction?.addToBackStack("home")
                fragmentTransaction?.commit()
                true
            }
                listOfSparePartTypRecyclerView.adapter = sparePartTypeAdapter
            }
//            else{
//                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
//            }
        })



    }

    override fun onResume() {
        super.onResume()
        activity?.toolbar ?.title= "Available Categories"

    }

}
