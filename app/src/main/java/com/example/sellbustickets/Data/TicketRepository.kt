package com.example.sellbustickets.Data

import android.content.Context
import com.example.sellbustickets.Data.Constantes.Companion.TICKET_TABLE_NAME
import com.example.sellbustickets.Data.Models.Ticket
import database
import org.jetbrains.anko.db.*

class TicketRepository (val context: Context) {

    fun findAll() : ArrayList<Ticket> = context.database.use {
        val tickets = ArrayList<Ticket>()

        select(TICKET_TABLE_NAME, "idTicket", "typeTicket", "priceTicket", "numberTicket")
            .parseList(object: MapRowParser<List<Ticket>> {
                override fun parseRow(columns: Map<String, Any?>): List<Ticket> {
                    val idTicket = columns.getValue("idTicket")
                    val typeTicket = columns.getValue("typeTicket")
                    val priceTicket = columns.getValue("priceTicket")
                    val numberTicket = columns.getValue("numberTicket")

                    val ticket = Ticket(idTicket.toString().toLong(), typeTicket.toString(), priceTicket.toString().toDouble(), numberTicket.toString().toInt())

                    tickets.add(ticket)

                    return tickets
                }
            })

        tickets
    }

    fun createTicket(ticket: Ticket) = context.database.use {
        insert(TICKET_TABLE_NAME,
                "typeTicket" to ticket.typeTicket,
                "priceTicket" to ticket.priceTicket,
                "numberTicket" to ticket.numberTicket)
    }

    /**
     * méthode qui permet de mettre à jour une ligne de la table de la base de donnée locale
     */
    fun updateTicket(ticket: Ticket, numberTicket: Int) = context.database.use {
       update(TICKET_TABLE_NAME, "numberTicket" to numberTicket).whereArgs("idTicket = {idTicket}", "idTicket" to ticket.idTicket).exec()
    }

    /**
     * supprimer toute la table
     */
    fun deleteAll() = context.database.use {
        delete(TICKET_TABLE_NAME)
    }
}