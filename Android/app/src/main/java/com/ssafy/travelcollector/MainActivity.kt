package com.ssafy.travelcollector

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.travelcollector.config.BaseActivity
import com.ssafy.travelcollector.databinding.ActivityMainBinding
import com.ssafy.travelcollector.viewModel.DetailStateEnum
import com.ssafy.travelcollector.viewModel.MainActivityViewModel
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val gpsListener = LocationListener { p0 ->
        mainActivityViewModel.setCurLocation(p0.latitude, p0.longitude)
//        binding.textLat.text = p0.latitude.toString()
//        binding.textLng.text = p0.longitude.toString()
    }

    @SuppressLint("MissingPermission")
    private fun getProviders(){
        val listProviders = locationManager.allProviders as MutableList<String>
        val isEnable = BooleanArray(4)
        for (provider in listProviders) {
            when ( provider ) {
                LocationManager.GPS_PROVIDER -> {
                    isEnable[0] = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1f, gpsListener)
                }
                LocationManager.NETWORK_PROVIDER -> {
                    isEnable[1] = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1f, gpsListener)
                }
                LocationManager.PASSIVE_PROVIDER -> {
                    isEnable[2] = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)
                    locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,1000,1f, gpsListener)
                }
            }
        }
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        navController = (supportFragmentManager.findFragmentById(binding.mainFrameLayout.id) as NavHostFragment).navController
        setSupportActionBar(binding.toolbar)

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
                    mainActivityViewModel.removeDetailState(DetailStateEnum.AddToTravel)
                    navController.navigate(R.id.heritageListFragment)
                    true
                }
                R.id.navigation_page_3->{
                    navController.navigate(R.id.travelListFragment)
                    true
                }
                R.id.navigation_page_4->{
                    true
                }
                else->false
            }
        }

        //다른 기능 개발 후에 주석 풀기
//        getProviders()

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
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        //다른  개발 후에 다시 주석 풀기
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, gpsListener)
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1f, gpsListener)
//        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 1f, gpsListener)
//
//        lifecycleScope.launch {
//            mainActivityViewModel.curLocation.collect{
//
//            }
//        }

    }

    override fun onPause() {
        super.onPause()
        //다른  개발 후에 다시 주석 풀기
//        locationManager.removeUpdates(gpsListener)
    }

}