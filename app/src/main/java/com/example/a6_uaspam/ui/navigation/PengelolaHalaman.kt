package com.example.a6_uaspam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a6_uaspam.ui.view.ViewAwal
import com.example.a6_uaspam.ui.view.acara.DetailScreenAcr
import com.example.a6_uaspam.ui.view.acara.EntryScreenAcara
import com.example.a6_uaspam.ui.view.acara.HomeScreenAcara
import com.example.a6_uaspam.ui.view.acara.UpdateScreenAcr
import com.example.a6_uaspam.ui.view.klien.DetailScreenKln
import com.example.a6_uaspam.ui.view.klien.EntryScreenKlien
import com.example.a6_uaspam.ui.view.klien.HomeScreenKlien
import com.example.a6_uaspam.ui.view.klien.UpdateScreenKln
import com.example.a6_uaspam.ui.view.lokasi.DetailScreenLks
import com.example.a6_uaspam.ui.view.lokasi.EntryScreenLokasi
import com.example.a6_uaspam.ui.view.lokasi.HomeScreenLokasi
import com.example.a6_uaspam.ui.view.lokasi.UpdateScreenLks
import com.example.a6_uaspam.ui.view.vendor.DetailScreenVnd
import com.example.a6_uaspam.ui.view.vendor.EntryScreenVendor
import com.example.a6_uaspam.ui.view.vendor.HomeScreenVendor
import com.example.a6_uaspam.ui.view.vendor.UpdateScreenVnd

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = Modifier
    ) {
        composable (
            DestinasiSplash.route
        ) {
            ViewAwal (
                onMulaiButton = {
                    navController.navigate(DestinasiHomeAcr.route)
                }
            )
        }
        composable(
            DestinasiHomeAcr.route
        ) {
            HomeScreenAcara(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertAcr.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailAcr.route}/$id")
                },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateAcr.route}/$id")
                },
                navigateToHome = {
                    navController.navigate(DestinasiHomeAcr.route)
                },
                onManajemenLokasiClick = {
                    navController.navigate(DestinasiHomeLks.route)
                },
                onManajemenKlienClick = {
                    navController.navigate(DestinasiHomeKln.route)
                },
                onManajemenVendorClick = {
                    navController.navigate(DestinasiHomeVnd.route)
                }
            )
        }

        composable(
            DestinasiHomeLks.route
        ) {
            HomeScreenLokasi(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertLks.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailLks.route}/$id")
                },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateLks.route}/$id")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeAcr.route)
                },
                onNavigateHome = {
                    navController.navigate(DestinasiHomeAcr.route)
                }
            )
        }

        composable(
            DestinasiHomeKln.route
        ) {
            HomeScreenKlien(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertKln.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailKln.route}/$id")
                },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateKln.route}/$id")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeAcr.route)
                },
                onNavigateHome = {
                    navController.navigate(DestinasiHomeAcr.route)
                }
            )
        }

        composable(
            DestinasiHomeVnd.route
        ) {
            HomeScreenVendor(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsertVnd.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailVnd.route}/$id")
                },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateVnd.route}/$id")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeAcr.route)
                },
                onNavigateHome = {
                    navController.navigate(DestinasiHomeAcr.route)
                }
            )
        }

        // Insert Halaman
        composable(
            DestinasiInsertAcr.route
        ) {
            EntryScreenAcara(
                navigateBack = {
                    navController.navigate(DestinasiHomeAcr.route) {
                        popUpTo(DestinasiHomeAcr.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            DestinasiInsertKln.route
        ) {
            EntryScreenKlien(
                navigateBack = {
                    navController.navigate(DestinasiHomeKln.route) {
                        popUpTo(DestinasiHomeKln.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            DestinasiInsertLks.route
        ) {
            EntryScreenLokasi(
                navigateBack = {
                    navController.navigate(DestinasiHomeLks.route) {
                        popUpTo(DestinasiHomeLks.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            DestinasiInsertVnd.route
        ) {
            EntryScreenVendor(
                navigateBack = {
                    navController.navigate(DestinasiHomeVnd.route) {
                        popUpTo(DestinasiHomeVnd.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Halaman Detail
        composable(
            DestinasiDetailAcr.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailAcr.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(DestinasiDetailAcr.ID)
            id?.let {
                DetailScreenAcr(
                    navigateBack = {
                        navController.navigate(DestinasiHomeAcr.route) {
                            popUpTo(DestinasiHomeAcr.route) {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }

        composable(
            DestinasiDetailKln.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKln.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(DestinasiDetailKln.ID)
            id?.let {
                DetailScreenKln(
                    navigateBack = {
                        navController.navigate(DestinasiHomeKln.route) {
                            popUpTo(DestinasiHomeKln.route) {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }

        composable(
            DestinasiDetailLks.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailLks.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(DestinasiDetailLks.ID)
            id?.let {
                DetailScreenLks(
                    navigateBack = {
                        navController.navigate(DestinasiHomeLks.route) {
                            popUpTo(DestinasiHomeLks.route) {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }

        composable (
            DestinasiDetailVnd.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailVnd.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(DestinasiDetailVnd.ID)
            id?.let {
                DetailScreenVnd(
                    navigateBack = {
                        navController.navigate(DestinasiHomeVnd.route) {
                            popUpTo(DestinasiHomeVnd.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        //Halaman Update
        composable(
            DestinasiUpdateAcr.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateAcr.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(DestinasiUpdateAcr.ID)
            id?.let { id ->
                UpdateScreenAcr(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        composable(
            DestinasiUpdateKln.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateKln.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(DestinasiUpdateKln.ID)
            id?.let { id ->
                UpdateScreenKln(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        composable(
            DestinasiUpdateLks.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateLks.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(DestinasiUpdateLks.ID)
            id?.let { id ->
                UpdateScreenLks(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        composable(
            DestinasiUpdateVnd.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateVnd.ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(DestinasiUpdateVnd.ID)
            id?.let { id ->
                UpdateScreenVnd(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}