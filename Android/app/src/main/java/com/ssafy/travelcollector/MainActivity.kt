package com.ssafy.travelcollector

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.travelcollector.config.BaseActivity
import com.ssafy.travelcollector.config.geofence.GeofenceBroadcastReceiver
import com.ssafy.travelcollector.config.geofence.GeofenceManager
import com.ssafy.travelcollector.databinding.ActivityMainBinding
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController

    private lateinit var sideImg: CircleImageView
    private lateinit var sideName: TextView
    private lateinit var sideLogout: Button

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initGeofence()
    }

    private fun initGeofence(){
        GeofenceManager.geofenceCallback = object : GeofenceManager.GeofenceCallback{
            override fun onEnter(id: String) {
                showToast("onEnter $id")
                mainActivityViewModel.addGameEnableHeritage(id.toInt())
            }

            override fun onDwell(id: String) {
                showToast("onDwell $id")
                mainActivityViewModel.addVisitedHeritage(id.toInt()){
                    achievementViewModel.loadAchievement()
                }
            }

            override fun onExit(id: String) {
                showToast("onExit $id")
                mainActivityViewModel.removeGameEnableHeritage(id.toInt())
            }
        }

        lifecycleScope.launch {
            mainActivityViewModel.geofenceList.collect{
                Log.d(TAG, "initGeofence: $it")
                if(it.isNotEmpty())
                    GeofenceManager.addGeofences(it)
            }
        }
    }

    private fun initView(){



        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        navController = (supportFragmentManager.findFragmentById(binding.mainFrameLayout.id) as NavHostFragment).navController
        setSupportActionBar(binding.toolbar)

        binding.hamburgerView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_store->{
                    navController.navigate(R.id.storeFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    false
                }
                else->{
                    false
                }
            }
        }

        val header = binding.hamburgerView.getHeaderView(0)

        sideImg = header.findViewById(R.id.side_user_profile_img)
        sideLogout = header.findViewById(R.id.side_logout)
        sideName = header.findViewById(R.id.side_user_name)
        val sideWelcome = header.findViewById<TextView>(R.id.side_tv_welcome)

        lifecycleScope.launch {
            accountViewModel.user.collect{
                sideName.text = it.userName
                if(it.memberEmail.isNotEmpty()){
                    Glide.with(applicationContext)
                        .load(it.profileUrl)
                        .into(sideImg)
                    sideLogout.visibility = View.VISIBLE
                    sideWelcome.text = "님 안녕하세요"
                }else{
                    sideLogout.visibility = View.GONE
                    sideWelcome.text = "로그인이 필요합니다"
                }

            }
        }

        if(supportActionBar!=null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.menu_hamburger)
            supportActionBar!!.title = "abc"
            supportActionBar!!.setHomeButtonEnabled(true)
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_page_1->{
                    navController.navigate(R.id.mainFragment)
                    true
                }
                R.id.navigation_page_2->{
                    navController.navigate(R.id.heritageListFragment)
                    mainActivityViewModel.removeDetailState(DetailStateEnum.AddToTravel)
                    true
                }
                R.id.navigation_page_3->{
                    navController.navigate(R.id.travelListFragment)
                    true
                }
                R.id.navigation_page_4->{
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else->false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_action, menu)
        return true
    }

    fun setNavigationBarStatus(status: Boolean){
        if(!status) {
            binding.bottomNavigation.visibility = View.GONE
        } else {
            binding.bottomNavigation.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}