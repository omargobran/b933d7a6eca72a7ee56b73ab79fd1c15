package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.R
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.ActivityMainBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.ActivitySpaceshipBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.spaceship.SpaceshipFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpaceshipActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpaceshipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpaceshipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, SpaceshipFragment.newInstance())
                .commitNow()
        }
    }
}