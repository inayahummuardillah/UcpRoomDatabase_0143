package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.Dosen.DestinasiInsertDosen
import com.example.ucp2.ui.view.Dosen.DetailDosenView
import com.example.ucp2.ui.view.Dosen.HomeDosenView
import com.example.ucp2.ui.view.Dosen.InsertDosenView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeDosen.route
    ) {
        composable(
            route = DestinasiHomeDosen.route
        ) {
            HomeDosenView(
                onDetailClick = {nidn ->
                    navController.navigate("${DestinasiDetailDosen.route}/$nidn")
                    println("PengelolaHalaman: nidn = $nidn")
                },
                onAddDsn = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                modifier = modifier
            )
        }

        composable (
            route =DestinasiInsertDosen.route
        ) {
            InsertDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable (
            DestinasiDetailDosen.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailDosen.NIDN) {
                    type = NavType.StringType
                }
            )
        ){
            val nidn = it.arguments?.getString(DestinasiDetailDosen.NIDN)

            nidn?.let { nidn ->
                DetailDosenView(
                    onBack = {
                        navController.popBackStack()
                    },
                    /*onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$nim")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }*/
                )
            }
        }
    }
}