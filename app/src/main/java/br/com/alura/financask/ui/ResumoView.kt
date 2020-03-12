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

class ResumoView(private val context: Context,
                 private val view: View,
                transacoes: List<Transacao>) {
    val resumo: Resumo = Resumo(transacoes)

    fun adicionaReceita() {
        val totalReceita = resumo.receita()

        val color = ContextCompat.getColor(context, R.color.receita)

        view.resumo_card_receita.text = totalReceita.formataParaBrasileiro()
        view.resumo_card_receita.setTextColor(color)
    }

    fun adicionaDespesa() {
        val totalDespesa = resumo.despesa()

        val color = ContextCompat.getColor(context, R.color.despesa)

        view.resumo_card_despesa.text = totalDespesa.formataParaBrasileiro()
        view.resumo_card_despesa.setTextColor(color)
    }

    fun adicionaTotal() {
        val total = resumo.total()
        val color: Int

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            color = ContextCompat.getColor(context, R.color.despesa)
        } else {
            color = ContextCompat.getColor(context, R.color.receita)

        }

        view.resumo_card_total.text = total.formataParaBrasileiro()
        view.resumo_card_total.setTextColor(color)
    }
}