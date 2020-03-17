package br.com.alura.financask.ui.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {
    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraLista()

        lista_transacoes_adiciona_receita.setOnClickListener {
            handleViewListenerReceita()
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            handleViewListenerDespesa()
        }
    }

    private fun handleViewListenerDespesa() {
        val viewCriada = configuraModal() ?: return

        AlertDialog.Builder(this)
                .setView(viewCriada)
                .setTitle(R.string.adiciona_despesa)
                .setPositiveButton("Adicionar") { _, _ ->
                    adicionaTransacaoDespesa(viewCriada)
                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun handleViewListenerReceita() {
        val viewCriada = configuraModal() ?: return

        AlertDialog.Builder(this)
                .setTitle(R.string.adiciona_receita)
                .setView(viewCriada)
                .setPositiveButton("Adicionar") { _, _ ->
                    adicionaTransacaoRececita(viewCriada)
                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun configuraModal(): View? {
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
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                val dataSelecionada = Calendar.getInstance()

                dataSelecionada.set(ano, mes, dia)
                viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())
            }, ano, mes, dia).show()
        }

        val adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias_de_receita,
                android.R.layout.simple_spinner_dropdown_item)
        viewCriada.form_transacao_categoria.adapter = adapter

        return viewCriada
    }

    private fun adicionaTransacaoRececita(viewCriada: View) {
        val transacao = criaTransacao(viewCriada, Tipo.RECEITA)

        atualizaTransacoes(transacao)
        lista_transacoes_adiciona_menu.close(true)
    }

    private fun adicionaTransacaoDespesa(viewCriada: View) {
        val transacao = criaTransacao(viewCriada, Tipo.DESPESA)

        atualizaTransacoes(transacao)
        lista_transacoes_adiciona_menu.close(true)
    }

    @SuppressLint("SimpleDateFormat")
    private fun criaTransacao(viewCriada: View, tipo: Tipo): Transacao {
        val valorEmTexto = viewCriada.form_transacao_valor.text.toString()
        val dataEmTexto = viewCriada.form_transacao_data.text.toString()
        val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()

        val valor = try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(this, "Falha na conversão de valor", Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }

        val dataConvertida = SimpleDateFormat("dd/MM/yyyy").parse(dataEmTexto)
        val data = Calendar.getInstance()
        data.time = dataConvertida

        return Transacao(tipo = tipo,
                valor = valor,
                data = data,
                categoria = categoriaEmTexto)
    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)

        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, window.decorView, transacoes)

        resumoView.atualiza()
    }

    private fun configuraLista() {
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