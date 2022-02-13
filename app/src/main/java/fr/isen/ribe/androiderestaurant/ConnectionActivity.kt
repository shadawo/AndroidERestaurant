package fr.isen.ribe.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.isen.ribe.androiderestaurant.databinding.ActivityConnectionBinding


class ConnectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val buttonLogin = binding.buttonLogin
        val buttonRegister = binding.buttonRegister
        val fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, LoginFragment()).commit()

        buttonLogin.setOnClickListener {
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, LoginFragment()).commit()
            buttonLogin.isVisible=false
            buttonRegister.isVisible=false
        }
        buttonRegister.setOnClickListener {
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, RegisterFragment()).commit()
            buttonLogin.isVisible=false
            buttonRegister.isVisible=false
        }



    }

}