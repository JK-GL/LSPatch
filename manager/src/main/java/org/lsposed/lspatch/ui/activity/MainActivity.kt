package org.lsposed.lspatch.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import org.lsposed.lspatch.ui.page.BottomBarDestination
import org.lsposed.lspatch.ui.page.NavGraphs
import org.lsposed.lspatch.ui.page.appCurrentDestinationAsState
import org.lsposed.lspatch.ui.page.destinations.Destination
import org.lsposed.lspatch.ui.page.startAppDestination
import org.lsposed.lspatch.ui.theme.AppleAccent
import org.lsposed.lspatch.ui.theme.AppleBackground
import org.lsposed.lspatch.ui.theme.AppleDesign
import org.lsposed.lspatch.ui.theme.AppleSurface2
import org.lsposed.lspatch.ui.theme.AppleText
import org.lsposed.lspatch.ui.theme.AppleText2
import org.lsposed.lspatch.ui.theme.LSPTheme
import org.lsposed.lspatch.ui.util.LocalSnackbarHost

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            LSPTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                CompositionLocalProvider(LocalSnackbarHost provides snackbarHostState) {
                    Box(Modifier.fillMaxSize().background(AppleBackground)) {
                        Scaffold(
                            containerColor = AppleBackground,
                            snackbarHost = { SnackbarHost(snackbarHostState) }
                        ) { innerPadding ->
                            DestinationsNavHost(
                                modifier = Modifier.padding(innerPadding),
                                navGraph = NavGraphs.root,
                                navController = navController
                            )
                        }
                        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                            FloatingNavBar(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FloatingNavBar(navController: NavHostController) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    var topDestination by rememberSaveable { mutableStateOf(currentDestination.route) }
    LaunchedEffect(currentDestination) {
        val queue = navController.currentBackStack.value
        if (queue.size == 2) topDestination = queue[1].destination.route!!
        else if (queue.size > 2) topDestination = queue[2].destination.route!!
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppleDesign.PagePadding, vertical = 12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(AppleDesign.CornerL),
            color = AppleSurface2,
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(AppleDesign.NavBarHeight)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarDestination.values().forEach { destination ->
                    val selected = topDestination == destination.direction.route
                    IconButton(
                        onClick = {
                            navController.navigate(destination.direction.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                if (selected) destination.iconSelected else destination.iconNotSelected,
                                contentDescription = stringResource(destination.label),
                                tint = if (selected) AppleAccent else AppleText2,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = stringResource(destination.label),
                                fontSize = AppleDesign.TinySize.sp,
                                color = if (selected) AppleAccent else AppleText2,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}