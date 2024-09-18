package com.arfomax.onmed.presentation.ui.activity

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.arfomax.onmed.R
import com.arfomax.onmed.domain.regionsAndDistricts.model.RegionsAndDistrictsModel
import com.arfomax.onmed.presentation.ui.activity.viewModels.RegionsAndDistrictsViewModel
import com.arfomax.onmed.presentation.utils.Constants
import com.arfomax.onmed.presentation.utils.PageState
import com.arfomax.onmed.presentation.utils.RuntimeCache
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val getRegionsViewModel by viewModels<RegionsAndDistrictsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, findViewById(R.id.main)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemGestures())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            window.setFlags(WindowManager.LayoutParams.FLAGS_CHANGED, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        RuntimeCache.userId = Hawk.get(Constants.USER_ID) ?: 0
        RuntimeCache.userVerified = Hawk.get(Constants.USER_VERIFIED) ?: false
        RuntimeCache.userName = Hawk.get(Constants.USER_NAME) ?: ""
        RuntimeCache.userPhoneNumber = Hawk.get(Constants.PHONE_NUMBER) ?: ""
        RuntimeCache.userRegionName = Hawk.get(Constants.REGION_NAME) ?: ""
        RuntimeCache.userRegionId = Hawk.get(Constants.REGION_ID) ?: 1
        RuntimeCache.userDistrictId = Hawk.get(Constants.DISTRICT_ID) ?: 1

        getRegionsResponse()
    }

    private fun getRegionsResponse() {

        if (RuntimeCache.regionsAndDistrictsList.isEmpty()) getRegionsViewModel.getRegionsAndDistricts()
        getRegionsViewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state->
                if (state is PageState.IsSuccess)
                    RuntimeCache.regionsAndDistrictsList = state.data as ArrayList<RegionsAndDistrictsModel>
            }.launchIn(lifecycleScope)
    }
}