package com.example.sellbustickets.Data.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sellbustickets.Data.Models.Ticket
import com.example.sellbustickets.R

class ListTicketsAdapter(var context: Context, var listTickets: ArrayList<Ticket>): BaseAdapter() {
    private class ViewHolder(rowTicket: View?) {
        var typeTicket: TextView? = null
        var priceTicket: TextView? = null
        var numberTicket: TextView? = null

        init {
            typeTicket = rowTicket?.findViewById(R.id.typeTicket_textView)
            priceTicket = rowTicket?.findViewById(R.id.priceTicket_textview)
            numberTicket = rowTicket?.findViewById(R.id.numberTicket_textview)
        }

    }

    override fun getCount(): Int {
        return listTickets.size
    }

    override fun getItem(position: Int): Ticket {
        return listTickets[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).idTicket
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = inflater.inflate(R.layout.item_ticket, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var ticket = listTickets[position]

        viewHolder.typeTicket!!.text = ticket.typeTicket
        viewHolder.priceTicket!!.text = "Price : ${ticket.priceTicket.toString()} €"
        viewHolder.numberTicket!!.text = "Number : ${ticket.numberTicket.toString()}"

        return view as View
    }
}