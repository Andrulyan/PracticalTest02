package ro.pub.cs.systems.eim.practicaltest02v1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AutocompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("autocomplete_result")
        if (!message.isNullOrEmpty()) {
            Toast.makeText(context, "Primit: $message", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Primit: Mesaj gol sau nul", Toast.LENGTH_LONG).show()
        }
    }
}
