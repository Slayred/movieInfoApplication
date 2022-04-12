package com.chibisov.movieinfoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.chibisov.movieinfoapplication.presentaion.HomeFragment

class MainActivity : AppCompatActivity() {

//    private val activityResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
//            if (activityResult.resultCode == RESULT_OK) {
//                val r = activityResult.data!!.getParcelableExtra<UiMovie>(Const.MOVIE)
//                if (r != null) {
//                    Log.d(Const.TAG, "Movie ${r.name} is favorite: ${r.status}")
//                }
//                Log.d(
//                    Const.TAG,
//                    "Comment added: ${activityResult.data!!.getStringExtra(Const.COMMENT)}")
//            }
//        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TAG", "MainActivity onCreate ${javaClass.simpleName}")
        replaceFragment(HomeFragment())
    }

//

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_fragment_container, fragment)
        transaction.commit()
    }

}

