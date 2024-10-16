import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.R

class CatalogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        // Referência para os layouts dos relógios
        val layoutWatch1 = findViewById<LinearLayout>(R.id.layoutWatch1)
        val layoutWatch2 = findViewById<LinearLayout>(R.id.layoutWatch2)
        val layoutWatch3 = findViewById<LinearLayout>(R.id.layoutWatch3)
        val layoutWatch4 = findViewById<LinearLayout>(R.id.layoutWatch4)
        val layoutWatch5 = findViewById<LinearLayout>(R.id.layoutWatch5)
        val layoutWatch6 = findViewById<LinearLayout>(R.id.layoutWatch6)

        // Configuração dos listeners para navegação
        layoutWatch1.setOnClickListener {
            openWatchDetails("Relógio Dourado")
        }
        layoutWatch2.setOnClickListener {
            openWatchDetails("Relógio Prata")
        }
        layoutWatch3.setOnClickListener {
            openWatchDetails("Relógio Dourado e Azul")
        }
        layoutWatch4.setOnClickListener {
            openWatchDetails("Relógio Dourado e Branco")
        }
        layoutWatch5.setOnClickListener {
            openWatchDetails("Relógio Prata e Verde")
        }
        layoutWatch6.setOnClickListener {
            openWatchDetails("SmartWatch Preto")
        }
    }

    // Função para abrir a tela de detalhes do relógio
    private fun openWatchDetails(watchName: String) {
        val intent = Intent(this, WatchDetailsActivity::class.java)
        intent.putExtra("WATCH_NAME", watchName) // Passando o nome do relógio para a próxima atividade
        startActivity(intent)
    }
}
