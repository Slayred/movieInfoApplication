package com.chibisov.movieinfoapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

//    private lateinit var movieFirstName: TextView
//    private lateinit var movieFirstBtn: Button
//    private lateinit var movieSecondName: TextView
//    private lateinit var movieSecondBtn: Button
//    private lateinit var movieThirdName: TextView
//    private lateinit var movieThirdBtn: Button
//    private lateinit var movieFourthName: TextView
//    private lateinit var movieFourthBtn: Button
    private lateinit var inviteBtn: Button
    private var checkedStatus = intArrayOf(0, 0, 0, 0)
    private val listMovie = Repository.movieList

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val r = activityResult.data!!.getParcelableExtra<Movie>(Const.MOVIE)
                listMovie.find {
                    it.id == r?.id
                }?.favorites = r?.favorites!!
                Log.d(Const.TAG, "Movie ${r.name} is favorite: ${r.favorites}")
                Log.d(
                    Const.TAG,
                    "Comment added: ${activityResult.data!!.getStringExtra(Const.COMMENT)}"
                )
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        movieFirstName = findViewById(R.id.movieNameFrist)
//        movieFirstBtn = findViewById(R.id.movieBtnFirst)
//        movieSecondName = findViewById(R.id.movieNameSecond)
//        movieSecondBtn = findViewById(R.id.movieBtnSecond)
//        movieThirdName = findViewById(R.id.movieNameThird)
//        movieThirdBtn = findViewById(R.id.movieBtnThird)
//        movieFourthName = findViewById(R.id.movieNameFourth)
//        movieFourthBtn = findViewById(R.id.movieBtnFourth)
        inviteBtn = findViewById(R.id.inviteBtn)

//        setFilms(listMovie)

//        movieFirstBtn.setOnClickListener(this)
//        movieSecondBtn.setOnClickListener(this)
//        movieThirdBtn.setOnClickListener(this)
//        movieFourthBtn.setOnClickListener(this)
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

//    private fun setColor() {
//        changeColor(movieFirstName, checkedStatus[0])
//        changeColor(movieSecondName, checkedStatus[1])
//        changeColor(movieThirdName, checkedStatus[2])
//        changeColor(movieFourthName, checkedStatus[3])
//    }

    private fun launchActivity(movie: Movie, view: TextView) {
        val intent = Intent(this, MovieInfo::class.java)
        intent.putExtra(Const.MOVIE, movie)
        changeColor(view, 1)
        activityResultLauncher.launch(intent)
    }

//    private fun setFilms(movieList: List<Movie>) {
//        movieFirstName.text = movieList[0].name
//        movieSecondName.text = movieList[1].name
//        movieThirdName.text = movieList[2].name
//        movieFourthName.text = movieList[3].name
//
//    }


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
//            movieFirstBtn.id -> {
//                launchActivity(listMovie[0], movieFirstName)
//                checkedStatus[0] = 1
//            }
//            movieSecondBtn.id -> {
//                launchActivity(listMovie[1], movieSecondName)
//                checkedStatus[1] = 1
//            }
//            movieThirdBtn.id -> {
//                launchActivity(listMovie[2], movieThirdName)
//                checkedStatus[2] = 1
//            }
//            movieFourthBtn.id -> {
//                launchActivity(listMovie[3], movieFourthName)
//                checkedStatus[3] = 1
//            }
            inviteBtn.id -> {
                share()
            }
        }
    }
}

