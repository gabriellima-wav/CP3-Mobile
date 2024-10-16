import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.R


class WatchDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_details)

        // Obter o nome do relógio passado pela Intent
        val watchName = intent.getStringExtra("WATCH_NAME")

        // Configurar o TextView para exibir o nome do relógio
        val textViewWatchName = findViewById<TextView>(R.id.textViewWatchName)
        textViewWatchName.text = watchName // Definindo o nome do relógio no TextView
    }
}
