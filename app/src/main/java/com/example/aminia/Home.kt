package com.example.aminia
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.aminia.databinding.ActivityHomeBinding


class Home : AppCompatActivity() {
    private val sharedPrefFile = "aminia"
    private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityHomeBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)


        binding.appBarHome.fab.setOnClickListener { view ->
            val builder = AlertDialog.Builder(this)
            val sharedPreferences: SharedPreferences? = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                if (editor != null) {
                    editor.clear()
                    editor.apply()
                }
                if (builder != null) {
                    builder.setTitle("LOG OUT SUCCESS")
                    builder.setMessage("You have log out successfully")
                    builder.setPositiveButton("Okay") { dialogInterface: DialogInterface, i: Int -> }
                    builder.show()
                }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        //binding.appBarHome.homeFragment.setOnClickListener{
           // val intent = Intent(this, loanApplyFragment::class.java)
          //  startActivity(intent)
            //finish()
       // }




        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_loanApply, R.id.nav_loanStatus,R.id.nav_loanReplay, R.id.nav_profile), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}