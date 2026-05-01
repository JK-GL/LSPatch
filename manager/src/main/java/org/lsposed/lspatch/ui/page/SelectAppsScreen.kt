package org.lsposed.lspatch.ui.page

import android.content.pm.ApplicationInfo
import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.parcelize.Parcelize
import org.lsposed.lspatch.R
import org.lsposed.lspatch.ui.component.AppItem
import org.lsposed.lspatch.ui.component.SearchAppBar
import org.lsposed.lspatch.ui.theme.AppleAccent
import org.lsposed.lspatch.ui.theme.AppleBackground
import org.lsposed.lspatch.ui.theme.AppleDesign
import org.lsposed.lspatch.ui.theme.AppleText
import org.lsposed.lspatch.ui.viewmodel.SelectAppsViewModel
import org.lsposed.lspatch.util.LSPPackageManager
import org.lsposed.lspatch.util.LSPPackageManager.AppInfo

@Parcelize
sealed class SelectAppsResult : Parcelable {
    data class SingleApp(val selected: AppInfo) : SelectAppsResult()
    data class MultipleApps(val selected: List<AppInfo>) : SelectAppsResult()
}

@Destination
@Composable
fun SelectAppsScreen(
    navigator: ResultBackNavigator<SelectAppsResult>,
    multiSelect: Boolean,
    initialSelected: ArrayList<String>?,
) {
    val viewModel = viewModel<SelectAppsViewModel>()
    var searchPackage by remember { mutableStateOf("") }

    val filter: (AppInfo) -> Boolean = {
        val packageLowerCase = searchPackage.toLowerCase(Locale.current)
        val contains = it.label.toLowerCase(Locale.current).contains(packageLowerCase) ||
            it.app.packageName.contains(packageLowerCase)
        if (multiSelect) contains && it.isXposedModule
        else contains && it.app.flags and ApplicationInfo.FLAG_SYSTEM == 0
    }

    LaunchedEffect(Unit) {
        viewModel.filterAppList(false, filter)
        initialSelected?.let { list ->
            val selectedSet = list.toSet()
            viewModel.multiSelected.addAll(
                LSPPackageManager.appList.filter { selectedSet.contains(it.app.packageName) }
            )
        }
    }

    BackHandler { navigator.navigateBack() }

    Scaffold(
        containerColor = AppleBackground,
        topBar = {
            SearchAppBar(
                title = { Text(stringResource(R.string.screen_select_apps), color = AppleText) },
                searchText = searchPackage,
                onSearchTextChange = {
                    searchPackage = it
                    viewModel.filterAppList(false, filter)
                },
                onClearClick = {
                    searchPackage = ""
                    viewModel.filterAppList(false, filter)
                },
                onBackClick = { navigator.navigateBack() }
            )
        },
        floatingActionButton = {
            if (multiSelect) {
                Box(Modifier.padding(bottom = AppleDesign.NavBarBottomMargin)) {
                    FloatingActionButton(
                        containerColor = AppleAccent,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = {
                            navigator.navigateBack(SelectAppsResult.MultipleApps(viewModel.multiSelected))
                        }
                    ) {
                        Icon(Icons.Outlined.Done, stringResource(R.string.add))
                    }
                }
            }
        }
    ) { innerPadding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.isRefreshing),
            onRefresh = { viewModel.filterAppList(true, filter) },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (multiSelect) {
                MultiSelect()
            } else {
                SingleSelect { navigator.navigateBack(SelectAppsResult.SingleApp(it)) }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SingleSelect(onSelect: (AppInfo) -> Unit) {
    val viewModel = viewModel<SelectAppsViewModel>()
    LazyColumn(
        contentPadding = PaddingValues(horizontal = AppleDesign.PagePadding, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(AppleDesign.ItemSpacing)
    ) {
        items(
            items = viewModel.filteredList,
            key = { it.app.packageName }
        ) { app ->
            AppItem(
                modifier = Modifier
                    .animateItem(spring(stiffness = Spring.StiffnessLow))
                    .clickable { onSelect(app) },
                icon = LSPPackageManager.getIcon(app),
                label = app.label,
                packageName = app.app.packageName
            )
        }
        item { Spacer(Modifier.height(AppleDesign.NavBarBottomMargin + 16.dp)) }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MultiSelect() {
    val viewModel = viewModel<SelectAppsViewModel>()
    LazyColumn(
        contentPadding = PaddingValues(horizontal = AppleDesign.PagePadding, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(AppleDesign.ItemSpacing)
    ) {
        items(
            items = viewModel.filteredList,
            key = { it.app.packageName }
        ) { app ->
            val checked = viewModel.multiSelected.contains(app)
            AppItem(
                modifier = Modifier
                    .animateItem(spring(stiffness = Spring.StiffnessLow))
                    .clickable {
                        if (checked) viewModel.multiSelected.remove(app)
                        else viewModel.multiSelected.add(app)
                    },
                icon = LSPPackageManager.getIcon(app),
                label = app.label,
                packageName = app.app.packageName,
                checked = checked
            )
        }
        item { Spacer(Modifier.height(AppleDesign.NavBarBottomMargin + 16.dp)) }
    }
}
