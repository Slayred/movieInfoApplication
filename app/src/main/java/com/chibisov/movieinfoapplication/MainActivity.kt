package com.chibisov.movieinfoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.presentation.*

class MainActivity : AppCompatActivity(), MainRouter {

    private var rootFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TAG", "MainActivity onCreate ${javaClass.simpleName}")
        navigateToHomeFragment()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_fragment_container, fragment)
        transaction.commit()
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        } else {
            rootFragment?.let { fragment ->
                if (!fragment.onBackPressed()) super.onBackPressed()
            }
        }
    }

    override fun navigateToHomeFragment() {
        rootFragment = Screens.Home.Container().value.also {
            homeFragment -> supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment_container, homeFragment, homeFragment::class.java.simpleName)
//            .addToBackStack(homeFragment::class.java.simpleName)
            .commit()
        }
    }

    override fun navigateToDetails(movie: UiMovie) {
        Screens.MovieInfoFragment(movie).value.also {
            fragment -> supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment, fragment::class.java.simpleName)
            .setCustomAnimations(
                R.anim.side_in,
                R.anim.side_in,
                R.anim.side_in,
                R.anim.side_in
            )
            .addToBackStack(null)
//            .add(R.id.main_fragment_container, fragment, fragment::class.java.simpleName)
            .commitAllowingStateLoss()
        }
    }
}

