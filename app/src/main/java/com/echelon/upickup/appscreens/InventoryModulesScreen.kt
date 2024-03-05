package com.echelon.upickup.appscreens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.echelon.upickup.R
import com.echelon.upickup.components.CustomColorTitleText
import com.echelon.upickup.components.InventoryModulesBox
import com.echelon.upickup.components.InventoryModulesDropdown
import com.echelon.upickup.sharedprefs.ModulesManager
import com.echelon.upickup.sharedprefs.StudentDetailsManager
import com.echelon.upickup.viewmodel.InventoryModulesViewModel

@Composable
fun InventoryModulesScreen(viewModel: InventoryModulesViewModel) {
    val modulesState = viewModel.getYear.observeAsState(emptyList())
    val studentDetails = StudentDetailsManager.getStudentDetails()
    Log.d("InventoryModulesScreen", "show em: $modulesState")
    val isLoading: Boolean by viewModel.isLoading.observeAsState(false)


    LaunchedEffect(Unit) {
        studentDetails?.let { viewModel.fetchModulesByYr(it.program, 1) }
    }


    Surface (modifier = Modifier
        .fillMaxSize(),
        color = colorResource(id = R.color.whitee)
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center)
        {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(50.dp))
                CustomColorTitleText(
                    text =  stringResource(R.string.modules),
                    R.color.inventory_text,
                    20,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(30.dp))

                InventoryModulesBox(
                    modules = modulesState.value,
                    studentDetails = studentDetails,
                    viewModel = viewModel
                )

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Log.d("InventoryModulesScreen", "show em: $modulesState")
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun InventoryModulesScreenPreview() {
//    InventoryModulesScreen(navController = rememberNavController(), viewModel = InventoryModulesViewModel())
//}