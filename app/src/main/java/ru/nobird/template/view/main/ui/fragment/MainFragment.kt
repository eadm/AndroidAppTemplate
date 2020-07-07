package ru.nobird.template.view.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ru.nobird.template.App
import ru.nobird.template.R
import ru.nobird.template.presentation.main.MainPresenter
import ru.nobird.template.presentation.main.MainView
import javax.inject.Inject

class MainFragment : Fragment(), MainView {
    companion object {
        fun newInstance(): Fragment =
            MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()

        mainPresenter = ViewModelProviders.of(this, viewModelFactory).get(MainPresenter::class.java)
    }

    private fun injectComponent() {
        App.component()
            .mainComponentBuilder()
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onStart() {
        super.onStart()
        mainPresenter.attachView(this)
    }

    override fun onStop() {
        mainPresenter.detachView(this)
        super.onStop()
    }
}
