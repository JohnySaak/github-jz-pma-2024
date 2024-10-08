package com.example.objednvky

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.objednvky.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.orderButton.setOnClickListener {

            val selectedBurgers = mutableListOf<String>()
            if (binding.cheeseBurger.isChecked) selectedBurgers.add("Cheese-burger")
            if (binding.baconBurger.isChecked) selectedBurgers.add("Bacon-burger")
            if (binding.chiliBurger.isChecked) selectedBurgers.add("Chili-burger")


            val selectedDoneness = when (binding.donenessGroup.checkedRadioButtonId) {
                binding.medium.id -> "Medium"
                binding.rare.id -> "Rare"
                binding.wellDone.id -> "Well Done"
                else -> ""
            }


            val orderSummary = if (selectedBurgers.isNotEmpty() && selectedDoneness.isNotEmpty()) {
                "You ordered: ${selectedBurgers.joinToString(", ")} cooked $selectedDoneness."
            } else {
                "Please select a burger and doneness level."
            }


            binding.orderSummary.text = orderSummary


            val burgerImageResource = when {
                binding.cheeseBurger.isChecked -> R.drawable.cheeseburger_image
                binding.baconBurger.isChecked -> R.drawable.baconburger_image
                binding.chiliBurger.isChecked -> R.drawable.chiliburger_image
                else -> R.drawable.default_burger_image
            }

            // Set the burger image
            binding.burgerImage.setImageResource(burgerImageResource)
        }
    }
}
