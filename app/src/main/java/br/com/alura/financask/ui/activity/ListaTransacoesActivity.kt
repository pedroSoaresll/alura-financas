package br.com.alura.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import br.com.alura.financask.R
import br.com.alura.financask.delegate.TransacaoDelegate
import br.com.alura.financask.model.Tipo
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import br.com.alura.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {
    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraLista()

        configuraFab()
    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)

        configuraLista()
        configuraResumo()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            criaAdicionaTransacaoDialog(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            criaAdicionaTransacaoDialog(Tipo.DESPESA)
        }
    }

    private fun criaAdicionaTransacaoDialog(tipo: Tipo) {
        AdicionaTransacaoDialog(this, window.decorView as ViewGroup).chama(
                tipo,
                object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        atualizaTransacoes(transacao)
                        lista_transacoes_adiciona_menu.close(true)
                    }
                }
        )
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, window.decorView, transacoes)

        resumoView.atualiza()
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