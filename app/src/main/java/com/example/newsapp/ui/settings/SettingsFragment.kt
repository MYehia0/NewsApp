package com.example.newsapp.ui.settings

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.settingsSpinner.adapter = adapter
        }

        binding.settingsSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.e("", parent?.getItemAtPosition(position).toString())
                val selectedLanguage = parent?.getItemAtPosition(position).toString()
                onLanguageClickListener?.let { it.onLanguageClick(selectedLanguage) }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        if (binding.textLanguage.text.equals("Language")) {
            binding.settingsSpinner.setSelection(0)
        } else if (binding.textLanguage.text.equals("اللغة")) {
            binding.settingsSpinner.setSelection(1)
        }
    }

    var onLanguageClickListener: OnLanguageClickListener? = null

    interface OnLanguageClickListener {
        fun onLanguageClick(language: String)
    }


}

