package com.haman.dearme.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.haman.dearme.ui.components.container.CustomBottomSheet
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.route.*
import com.haman.dearme.ui.screen.history.HistoryNavigation
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.ui.theme.DearMeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.values.all { it }) {
                // all permission is granted.
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied.
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DearMeTheme { DearMeApp(this, requestPermissionLauncher) } }
        MobileAds.initialize(this) {}
    }

    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DearMeApp(activity: MainActivity, permissionLauncher: ActivityResultLauncher<Array<String>>) {
    val navController = rememberNavController()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        allScreens.find { currentDestination?.route?.startsWith(it.route) ?: false }
            ?: HistoryNavigation

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = null) {
        when {
            shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                println("hello world")
                coroutineScope.launch { bottomSheetState.show() }
            }
            else -> {
                permissionLauncher.launch(MainActivity.REQUIRED_PERMISSIONS)
            }
        }
    }

    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(modifier = Modifier.padding(24.dp)) {
                Sub1(text = "갤러리 접근 권한 요청")
                Sub2(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = "이 앱에서 사용되는 모든 이미지는 사용자의 핸드폰 내에서 가져와 사용하게 됩니다. " +
                            "권한을 주지 않더라도 앱을 사용할 수 있지만, 이미지를 사용하는 부분에서 제한이 있을 수 있습니다. " +
                            "\n(* 사진을 다른 곳에 저장하거나 사용하지 않습니다.)",
                    align = TextAlign.Start,
                    color = if (isSystemInDarkTheme()) ColorPalette.LIGHT_GREY else ColorPalette.DARK_GREY
                )
                Button(
                    onClick = {
                        permissionLauncher.launch(MainActivity.REQUIRED_PERMISSIONS)
                        coroutineScope.launch { bottomSheetState.hide() }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ColorPalette.PRIMARY
                    )
                ) {
                    Sub1(text = "권한 요청하기", color = ColorPalette.White)
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                BottomTabRow(
                    allScreens = bottomTabScreen,
                    onTabSelected = { navController.push(it.route) },
                    currentScreen = currentScreen
                )
            }
        ) {
            RouteHost(
                navController = navController,
                startDestination = HistoryNavigation.route,
                modifier = Modifier.padding(it)
            )
        }
    }
}