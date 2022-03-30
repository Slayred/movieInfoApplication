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

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(resources.getText(R.string.exit))
            alertDialog.setMessage(resources.getText(R.string.exit_question))
            alertDialog.setPositiveButton(resources.getText(R.string.yes)) { _, _ ->
                super.onBackPressed()
            }
            alertDialog.setNegativeButton(resources.getText(R.string.no)) { _, _ -> }
            alertDialog.show()
        } else {
            supportFragmentManager.popBackStack()
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }

}

