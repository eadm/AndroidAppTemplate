package ru.nobird.template.view.main.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.nobird.android.view.navigation.ui.listener.BackButtonListener
import ru.nobird.android.view.navigation.ui.listener.BackNavigable
import ru.nobird.template.App
import ru.nobird.template.R
import ru.nobird.template.view.main.navigation.screen.MainScreen
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BackNavigable {

    @Inject
    internal lateinit var navigatorHolder: NavigatorHolder

    @Inject
    internal lateinit var router: Router

    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            router.newRootScreen(MainScreen())
        }
    }

    private fun injectComponent() {
        App.component()
            .mainComponentBuilder()
            .build()
            .inject(this)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.main_container) as? BackButtonListener)
            ?.onBackPressed()
            ?: router.exit()
    }

    override fun onNavigateBack() {
        router.exit()
    }
}
