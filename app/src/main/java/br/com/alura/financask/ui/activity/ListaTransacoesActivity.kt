package br.com.alura.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.alura.financask.R
import br.com.alura.financask.model.Tipo
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)

        configuraLista(transacoes)
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val resumoView = ResumoView(this, window.decorView, transacoes)

        resumoView.adicionaReceita()
        resumoView.adicionaDespesa()
        resumoView.adicionaTotal()
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
                Transacao(valor = BigDecimal(700.0),
                        tipo = Tipo.DESPESA),
                Transacao(valor = BigDecimal(500.0),
                        categoria = "Prêmio",
                        tipo = Tipo.RECEITA))
    }

}