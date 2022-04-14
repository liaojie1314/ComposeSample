package com.example.composesample.ui.components


import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.composesample.compositionLocal.LocalUserViewModel
import com.example.composesample.viewmodel.UserViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.example.composesample.ui.navigation.Destinations
import com.example.composesample.ui.screens.ArticleDetailScreen
import com.example.composesample.ui.screens.LoginScreen
import com.example.composesample.ui.screens.MainFrame
import com.example.composesample.ui.screens.VideoDetailScreen


/**
 * 导航控制器
 *
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHostApp() {

    val navController = rememberAnimatedNavController()
    ProvideWindowInsets {

        CompositionLocalProvider(LocalUserViewModel provides UserViewModel(LocalContext.current)) {

            val userViewModel = LocalUserViewModel.current

            AnimatedNavHost(
                navController = navController,
                startDestination = Destinations.HomeFrame.route
            ) {

                //首页大框架
                composable(
                    Destinations.HomeFrame.route,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left
                        )
                    },
                ) {
                    MainFrame(onNavigateToArticle = {
                        Log.i("===", "onNavigateToArticle")
                        navController.navigate(Destinations.ArticleDetail.route)
                    }, onNavigateToVideo = {
                        navController.navigate(Destinations.VideoDetail.route)
                    }, onNavigateToStudyHistory = {
                        if (userViewModel.logged) {
                            //已登录
                        } else {
                            //未登录
                            navController.navigate(Destinations.Login.route)
                        }
                    })
                }

                //文章详情页
                composable(
                    Destinations.ArticleDetail.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    },
                ) {
                    ArticleDetailScreen(onBack = {
                        navController.popBackStack()
                    })
                }

                //视频详情页
                composable(
                    Destinations.VideoDetail.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    },
                ) {
                    VideoDetailScreen(onBack = {
                        navController.popBackStack()
                    })
                }

                composable(
                    Destinations.Login.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
                    },
                ) {
                    LoginScreen {
                        navController.popBackStack()
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun NavHostAppPreview() {
    NavHostApp()
}

