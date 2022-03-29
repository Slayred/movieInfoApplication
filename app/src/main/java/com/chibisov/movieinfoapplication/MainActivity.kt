package com.chibisov.movieinfoapplication

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.adapter.itemDecoration.CustomVerticalItemDecoration
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.adapter.itemDecoration.CustomHorizontalItemDecoration
import com.chibisov.movieinfoapplication.core.Const
import com.chibisov.movieinfoapplication.core.MovieType
import com.chibisov.movieinfoapplication.core.Observer
import com.chibisov.movieinfoapplication.data.Movies
import com.chibisov.movieinfoapplication.data.MoviesCacheFavorites
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.UiMovie
import com.chibisov.movieinfoapplication.domain.BaseInteractor
import com.chibisov.movieinfoapplication.domain.Communication
import com.chibisov.movieinfoapplication.ui.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var movieListFragment: BaseFragment
    private lateinit var bottomBar: BottomNavigationView


    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val r = activityResult.data!!.getParcelableExtra<UiMovie>(Const.MOVIE)
                if (r != null) {
                    Log.d(Const.TAG, "Movie ${r.name} is favorite: ${r.status}")
                }
                Log.d(
                    Const.TAG,
                    "Comment added: ${activityResult.data!!.getStringExtra(Const.COMMENT)}")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TAG", "MainActivity onCreate ${javaClass.simpleName}")
        movieListFragment = BaseFragment()
        replaceFragment(movieListFragment)
        bottomBar = findViewById(R.id.btm_nav)
        bottomBar.setOnItemSelectedListener {
            when (it.itemId){
                R.id.ic_movie_list -> replaceFragment(movieListFragment)
                R.id.ic_favorites -> replaceFragment(movieListFragment)
            }
            true
        }
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(resources.getText(R.string.exit))
        alertDialog.setMessage(resources.getText(R.string.exit_question))
        alertDialog.setPositiveButton(resources.getText(R.string.yes)) { _, _ ->
            super.onBackPressed()
        }
        alertDialog.setNegativeButton(resources.getText(R.string.no)) { _, _ -> }
        alertDialog.show()
    }



//    private fun showDetails(movie: UiMovie){
//        baseInteractor.addCheckedItem(movie)
//        val intent = Intent(this, MovieInfo::class.java)
//        intent.putExtra(Const.MOVIE, movie)
//        activityResultLauncher.launch(intent)
//
//    }
    private fun showFavorites() {
        val intent = Intent(this, FavoritesMovie::class.java)
        startActivity(intent)
    }
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}

