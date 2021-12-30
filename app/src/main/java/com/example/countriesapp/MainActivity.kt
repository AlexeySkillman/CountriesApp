package com.example.countriesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.countriesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButtom.setOnClickListener{
            val countryName = binding.etGetNameCountry.text.toString()
            lifecycleScope.launch {
                try {
                    val countries = restCountriesApi.getCountryByName(countryName)
                    val country = countries[0]

                    binding.tvCountryName.text =  country.name
                    binding.tvCapitalName.text =  country.capital
                    binding.tvPopulationName.text =  NumberFormat.getInstance().format(country.population)
                    binding.tvAreaName.text =  NumberFormat.getInstance().format(country.area)
                    binding.tvLangName.text =  langugesTostring(country.languages)

                    loadSvg(binding.imageView, country.flag)

                    binding.content.visibility = View.VISIBLE
                    binding.preview.visibility = View.GONE
                } catch (e: Exception){
                    binding.status.text = "Страна не найдена"
                    binding.preview.visibility = View.VISIBLE
                }
            }
        }
    }

    fun langugesTostring(language: List<Language>): String {
        return language.joinToString { it.name }
    }

}