package com.example.lepaking.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lepaking.Constants
import com.example.lepaking.R
import com.example.lepaking.view.fragment.OrderDetailFragment
import com.example.lepaking.view.fragment.OrderFragment
import com.example.lepaking.workmanager.WorkManagerScheduler

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.content, OrderFragment()).commit()
        }
       // startWorkManager1()
       // startWorkManager2()
       // startWorkManager3()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun showFragment(fragment: Fragment) {


        val tag = fragment::class.java.simpleName
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment, tag)


       // transaction.setCustomAnimations(R.anim.transition_animation_fade_in, R.anim.transition_animation_fade_out, R.anim.transition_animation_fade_in, R.anim.transition_animation_fade_out)

        if(supportFragmentManager.fragments.size > 0) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
    }

    private fun startWorkManager1() {
        Log.d("MyWorker", "started scheduler1")
        WorkManagerScheduler.refreshPeriodicWork1(this)
    }

    private fun startWorkManager2() {
        Log.d("MyWorker", "started scheduler2")
        WorkManagerScheduler.refreshPeriodicWork2(this)
    }

    private fun startWorkManager3() {
        Log.d("MyWorker", "started scheduler3")
        WorkManagerScheduler.refreshPeriodicWork3(this)
    }
}