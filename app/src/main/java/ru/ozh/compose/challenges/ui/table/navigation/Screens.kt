package ru.ozh.compose.challenges.ui.table.navigation

/**
 * This class described screen navigation objects
 * @param screenName - deeplink screen representation
 * @param titleResourceId - resource to name tabbar or appbar navigation title
 */
sealed class Screen(val screenName: String, val title: String) {
    object Table: Screen("table", "")
    object Element: Screen("element", "")
//    object List: Screen("list", )
//    object Complex: Screen("complex", )
//    object Push: Screen("push", )
//    object NewMessage: Screen("new_message", )
}