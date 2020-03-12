package br.com.alura.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Resumo
import br.com.alura.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(context: Context,
                 private val view: View,
                 transacoes: List<Transacao>) {
    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        val totalReceita = resumo.receita
        val color = corReceita

        with(view.resumo_card_receita) {
            text = totalReceita.formataParaBrasileiro()
            setTextColor(color)
        }
    }

    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa
        val color = corDespesa

        with(view.resumo_card_despesa) {
            text = totalDespesa.formataParaBrasileiro()
            setTextColor(color)
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total

        val color = corPor(total)

        with(view.resumo_card_total) {
            text = total.formataParaBrasileiro()
            setTextColor(color)
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        return if (valor < BigDecimal.ZERO) {
            corDespesa
        } else {
            corReceita
        }
    }
}