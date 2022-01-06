package com.example.sellbustickets.Data.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sellbustickets.Data.Models.Transaction
import com.example.sellbustickets.R

class ListTransactionsAdapter(var context: Context, var listTransactions: ArrayList<Transaction>): BaseAdapter() {
    private class ViewHolder(rowTransaction: View?) {
        var idTransaction: TextView? = null
        var amountTransaction: TextView? = null

        init {
            idTransaction = rowTransaction?.findViewById(R.id.idTransaction_textview)
            amountTransaction = rowTransaction?.findViewById(R.id.amountTransaction_textview)
        }
    }

    override fun getCount(): Int {
        return listTransactions.size
    }

    override fun getItem(position: Int): Transaction {
        return listTransactions[position]
    }

    override fun getItemId(position: Int): Long {
     return getItem(position).id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = inflater.inflate(R.layout.item_transaction, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var transaction = listTransactions[position]

        viewHolder.idTransaction!!.text = "id : ${transaction.idTransaction}"
        viewHolder.amountTransaction!!.text = "Amount : ${transaction.amountTransaction} €"

        return view as View
    }
}