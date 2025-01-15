package ro.pub.cs.systems.eim.practicaltest02v1

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest02MainActivity : AppCompatActivity() {

    private lateinit var autoCompleteInput: AutoCompleteTextView
    private lateinit var searchButton: Button
    private lateinit var autoCompleteResults: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test02v1_main)

        // Inițializare componente
        autoCompleteInput = findViewById(R.id.autoCompleteInput)
        searchButton = findViewById(R.id.searchButton)
        autoCompleteResults = findViewById(R.id.autoCompleteResults)

        // Setează sugestii pentru AutoCompleteTextView
        val suggestions = listOf("Prefix1", "Prefix2", "Prefix3", "Prefix4")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestions)
        autoCompleteInput.setAdapter(adapter)

        // Logica butonului de căutare
        searchButton.setOnClickListener {
            val input = autoCompleteInput.text.toString()
            if (input.isNotEmpty()) {
                autoCompleteResults.text = "Rezultate pentru '$input':\n- Variantă1\n- Variantă2"
            } else {
                autoCompleteResults.text = "Introduceți un prefix valid!"
            }
        }
    }
}
