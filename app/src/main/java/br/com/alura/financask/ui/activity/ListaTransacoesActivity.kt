package br.com.alura.financask.ui.activity

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Tipo
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)

        configuraLista(transacoes)

        lista_transacoes_adiciona_receita.setOnClickListener {
            val viewCriada = LayoutInflater.from(this)
                    .inflate(R.layout.form_transacao,
                            window.decorView as ViewGroup,
                            false)
            val hoje = Calendar.getInstance()
            val ano = 2020
            val mes = 3
            val dia = 12

            viewCriada.form_transacao_data.setText(hoje.formataParaBrasileiro())
            viewCriada.form_transacao_data.setOnClickListener {
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()

                    dataSelecionada.set(ano, mes, dia)
                    viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())
                }, ano, mes, dia).show()
            }

            val adapter = ArrayAdapter.createFromResource(this,
                    R.array.categorias_de_receita,
                    android.R.layout.simple_spinner_dropdown_item)
            viewCriada.form_transacao_categoria.adapter = adapter

            AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setView(viewCriada)
                    .setPositiveButton("Adicionar", null)
                    .setNegativeButton("Cancelar", null)
                    .show()
        }
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val resumoView = ResumoView(this, window.decorView, transacoes)

        resumoView.atualiza()
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(Transacao(
                tipo = Tipo.DESPESA,
                categoria = "almoço de final de semana",
                data = Calendar.getInstance(),
                valor = BigDecimal(20.5)),
                Transacao(valor = BigDecimal(100.0),
                        tipo = Tipo.RECEITA,
                        categoria = "Economia"),
                Transacao(valor = BigDecimal(300.0),
                        tipo = Tipo.DESPESA),
                Transacao(valor = BigDecimal(500.0),
                        categoria = "Prêmio",
                        tipo = Tipo.RECEITA))
    }

}