package com.example.lepaking.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lepaking.Constants
import com.example.lepaking.R
import com.example.lepaking.view.fragment.OrderDetailFragment


class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val orderID=intent.getStringExtra(Constants.ORDER_ID)
        val fragmentType=intent.getStringExtra(Constants.FRAGMENT_TYPE)

        showFragment(OrderDetailFragment.newInstance(orderID,fragmentType))
    }

    private fun showFragment(fragment: Fragment) {
        val tag = fragment::class.java.simpleName
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layout_content, fragment, tag)

        // transaction.setCustomAnimations(R.anim.transition_animation_fade_in, R.anim.transition_animation_fade_out, R.anim.transition_animation_fade_in, R.anim.transition_animation_fade_out)

        if(supportFragmentManager.fragments.size > 0) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
    }
}
