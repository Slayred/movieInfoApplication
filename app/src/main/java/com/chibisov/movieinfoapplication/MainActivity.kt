package com.chibisov.movieinfoapplication

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chibisov.movieinfoapplication.adapter.MovieAdapter
import com.chibisov.movieinfoapplication.data.Repository
import com.chibisov.movieinfoapplication.data.models.Movie
import com.chibisov.movieinfoapplication.domain.BaseInteractor

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var inviteBtn: Button
    private lateinit var recyclerView: RecyclerView
    private var checkedStatus = intArrayOf(0, 0, 0, 0)
    private val repository = Repository()
    private val baseInteractor = BaseInteractor(repository)
    private val communication = Communication(repository)
    private val listMovie = communication.getData()

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val r = activityResult.data!!.getParcelableExtra<Movie>(Const.MOVIE)
//                listMovie.find {
//                    it.id == r?.id
//                }?.favorites = r?.favorites!!
//                Log.d(Const.TAG, "Movie ${r.name} is favorite: ${r.favorites}")
//                Log.d(
//                    Const.TAG,
//                    "Comment added: ${activityResult.data!!.getStringExtra(Const.COMMENT)}"
//                )
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inviteBtn = findViewById(R.id.inviteBtn)
        recyclerView = findViewById(R.id.movieRV)
        val adapter = MovieAdapter(MovieType.Common)
        recyclerView.adapter = adapter
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else recyclerView.layoutManager = GridLayoutManager(this, 2)

        adapter.show(listMovie)

        inviteBtn.setOnClickListener(this)

    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Exit")
        alertDialog.setMessage("Are you sure to exit?")
        alertDialog.setPositiveButton("YES"){
                _, _ -> super.onBackPressed()
        }
        alertDialog.setNegativeButton("NO"){_, _ ->}
        alertDialog.show()
    }

    private fun share() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_text))
        startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
    }


    private fun launchActivity(movie: Movie, view: TextView) {
        val intent = Intent(this, MovieInfo::class.java)
        intent.putExtra(Const.MOVIE, movie)
        changeColor(view, 1)
        activityResultLauncher.launch(intent)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray(Const.CHECKED, checkedStatus)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        checkedStatus = savedInstanceState.getIntArray(Const.CHECKED)!!
        //setColor()
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun changeColor(view: TextView, checked: Int) {
        when (checked) {
            1 -> view.setTextColor(ContextCompat.getColor(this, R.color.red))
            0 -> view.setTextColor(ContextCompat.getColor(this, R.color.black))
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            inviteBtn.id -> {
                share()
            }
        }
    }
}

