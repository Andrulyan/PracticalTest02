package ro.pub.cs.systems.eim.practicaltest02v1

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class PracticalTest02MainActivity : AppCompatActivity() {

    private lateinit var autoCompleteInput: AutoCompleteTextView
    private lateinit var searchButton: Button
    private lateinit var autoCompleteResults: TextView
    private lateinit var showMapButton: Button
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test02v1_main)

        // Inițializare componente UI
        autoCompleteInput = findViewById(R.id.autoCompleteInput)
        searchButton = findViewById(R.id.searchButton)
        autoCompleteResults = findViewById(R.id.autoCompleteResults)
        showMapButton = findViewById(R.id.showMapButton)

        // Logica butonului de căutare
        searchButton.setOnClickListener {
            val query = autoCompleteInput.text.toString().trim()
            if (query.isNotEmpty()) {
                fetchAutocompleteSuggestions(query)
            } else {
                autoCompleteResults.text = "Introduceți un cuvânt pentru căutare!"
            }
        }

        // Buton pentru deschiderea activității cu harta
        showMapButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAutocompleteSuggestions(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val suggestions = getAutocompleteSuggestions(query)
            withContext(Dispatchers.Main) {
                if (suggestions.isNotEmpty()) {
                    // Afișează sugestiile în AutoCompleteTextView
                    val adapter = ArrayAdapter(this@PracticalTest02MainActivity, android.R.layout.simple_dropdown_item_1line, suggestions)
                    autoCompleteInput.setAdapter(adapter)
                    autoCompleteInput.showDropDown()

                    // Afișează sugestiile în TextView
                    autoCompleteResults.text = suggestions.joinToString("\n")
                } else {
                    autoCompleteResults.text = "Nu s-au găsit sugestii."
                }
            }
        }
    }

    private fun getAutocompleteSuggestions(query: String): List<String> {
        val url = "https://www.google.com/complete/search?client=firefox&q=$query"
        val request = Request.Builder().url(url).build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val jsonResponse = JSONArray(response.body?.string())
                val suggestions = jsonResponse.getJSONArray(1)
                List(suggestions.length()) { index -> suggestions.getString(index) }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
