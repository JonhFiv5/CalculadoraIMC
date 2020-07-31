package dev.jonhfive.calculadoraimc

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        skbMainPeso.max = 20000
        skbMainAltura.max = 300

        skbMainPeso.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                txvMainPeso.text = (p1 / 100.0).toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        skbMainAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                txvMainAltura.text = (p1 / 100.0).toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        btnMainCalcular.setOnClickListener {

            val altura = txvMainAltura.text.toString().toFloat()
            val peso = txvMainPeso.text.toString().toFloat()
            if ((altura > 0) && peso > 0) {

                val imc = peso / (altura.pow(2))
                val eIdoso = ckbMainIdoso.isChecked

                var classificacao = ""
                if (eIdoso){
                    if (imc < 22) {
                        classificacao = "Baixo peso"
                    } else if (imc < 27){
                        classificacao = "Adequado ou eutrófico"
                    } else{
                        classificacao = "Sobrepeso"
                    }
                } else{
                    if (imc < 18.5) {
                        classificacao = "Baixo peso"
                    } else if (imc <= 24.9){
                        classificacao = "Peso normal"
                    } else if (imc <= 29.9){
                        classificacao = "Excesso de peso"
                    }  else if (imc <= 34.9){
                        classificacao = "Obesidade classe 1"
                    } else if (imc <= 39.9){
                        classificacao = "Obesidade classe 2"
                    } else{
                        classificacao = "Obesidade classe 3"
                    }
                }
                val resumo = ("IMC = %.2f\n$classificacao".format(imc))

                val mIntent = Intent(this, ResultActivity::class.java)
                mIntent.putExtra("INTENT_RESUMO", resumo)
                startActivity(mIntent)
                finishAffinity()
            } else{
                // Criando mensagem de alerta
                val alert = AlertDialog.Builder(this@MainActivity)

                // Definir o título
                alert.setTitle("Dados inválidos")

                // Definir o corpo da mensagem
                alert.setMessage("Por favor, insira valores válidos e tente novamente")

                // Definir o botão Positivo
                alert.setPositiveButton("Sim") { dialog, wich ->
                }

                // Exibir a caixa de diálogo
                alert.show()
            }
        }
    }
}