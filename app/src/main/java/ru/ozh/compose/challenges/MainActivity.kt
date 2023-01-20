package ru.ozh.compose.challenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import ru.ozh.compose.challenges.ui.table.navigation.Screen
import ru.ozh.compose.challenges.ui.table.screens.ElementScreen
import ru.ozh.compose.challenges.ui.table.screens.TableScreen
import ru.ozh.compose.challenges.ui.theme.ComposeChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GridContent()
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun GridContent() {


        ComposeChallengesTheme {
            Surface(color = MaterialTheme.colors.background) {
                val navController = rememberAnimatedNavController()

                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screen.Table.screenName
                ) {
                    composable(Screen.Table.screenName) {
                        TableScreen(
                            navController
//                            createExternalRouter { screen, params ->
//                                navController.navigate(screen, params)
//                            }
                        )
                    }
                    composable(Screen.Element.screenName + "/{el}",
                        enterTransition = {
                            when (initialState.destination.route) {
                                Screen.Table.screenName ->
                                    slideInVertically(initialOffsetY = {
                                        it
                                    })
                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                Screen.Table.screenName ->
                                    slideOutVertically(targetOffsetY = {
                                        it
                                    })
                                else -> null
                            }
                        },
                        popEnterTransition = {
                            when (initialState.destination.route) {
                                Screen.Table.screenName ->
                                    slideInVertically(initialOffsetY = {
                                        it
                                    })
                                else -> null
                            }
                        },
                        popExitTransition = {
                            when (targetState.destination.route) {
                                Screen.Table.screenName ->
                                    slideOutVertically(targetOffsetY = {
                                        it
                                    })
                                else -> null
                            }
                        }) {
                        AnimatedVisibility(visible = true, enter = slideInVertically {
                            it
                        }) {
                            ElementScreen(it.arguments?.getString("el"))
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun PresentModal(content: @Composable () -> Unit) {
    AnimatedVisibility(visible = true) {
        content
    }
}