package com.example.lepaking.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lepaking.Constants
import com.example.lepaking.LepakingApplication
import com.example.lepaking.R
import com.example.lepaking.SessionData
import com.example.lepaking.databinding.ActivityMainBinding
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.view.fragment.AboutDialogFragment
import com.example.lepaking.view.fragment.OrderFragment
import com.example.lepaking.workmanager.WorkManagerScheduler
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class MainActivity : AppCompatActivity(){

    @Inject
    lateinit var sessionData: SessionData
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var orderDetailDao: OrderDetailDao

    init {
        LepakingApplication.dataComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()

        val count = orderDetailDao.getPendingOrderCount()
        binding.bottomNavigation.getOrCreateBadge(R.id.navigation_home).isVisible = count > 0
        binding.bottomNavigation.getOrCreateBadge(R.id.navigation_home).number = count

    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("ss", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
//            val token = task.result
            sessionData.firebaseInstanceId = task.result!!

        })

        initializeBottomNavigation()

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction().replace(R.id.content, OrderFragment()).commit()
//        }


       // startWorkManager1()
       // startWorkManager2()
       // startWorkManager3()
    }

    private fun initializeBottomNavigation(){

        supportFragmentManager.beginTransaction().replace(R.id.content, OrderFragment.newInstance(Constants.NEW_ORDER)).commit()
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, OrderFragment.newInstance(Constants.NEW_ORDER)).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_complete -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, OrderFragment.newInstance(Constants.COMPLETE_ORDER)).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.content, AboutDialogFragment()).commit()
                    // Respond to navigation item 2 click
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
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