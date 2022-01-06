package com.example.sellbustickets.Data

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sellbustickets.Data.Adapters.ListTicketsAdapter
import com.example.sellbustickets.Data.Models.Ticket
import com.example.sellbustickets.IHM.ListTicketsOrderedActivity
import com.example.sellbustickets.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class DialogUtil: AppCompatActivity() {
    companion object {
        fun createNumberOfTickets(context: Context, ticket: Ticket, title: String): Int {
            var customAlertDialog = MaterialAlertDialogBuilder(context)
            var mAlertDialog: AlertDialog
            var customAlertDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_number_ticket, null, false)
            var numberTickets = customAlertDialogView.findViewById<TextInputEditText>(R.id.numbetTickets_textInputEditText)
            var titleDialog = customAlertDialogView.findViewById<TextView>(R.id.title_dialog_textview)
            var i = 0

            titleDialog.text = title
            customAlertDialog.setPositiveButton(context.getString(R.string.valider_text), null)
            customAlertDialog.setNegativeButton(context.getString(R.string.annuler_text), null)
            customAlertDialog.setView(customAlertDialogView)
            customAlertDialog.setCancelable(false)
            mAlertDialog = customAlertDialog.create()
            if (context is ListTicketsOrderedActivity) {
                numberTickets.setText(ticket.numberTicket.toString())
            }
            numberTickets.requestFocus()
            mAlertDialog.setOnShowListener {
                var validerButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                var annulerButton = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

                validerButton.setOnClickListener {
                    if (numberTickets.text.toString().isNullOrEmpty()) {
                        numberTickets.error = context.getString(R.string.error_text)
                    } else {
                        var numberTickets = numberTickets.text.toString().toInt()

                        if (ticket.numberTicket == null) {
                            ticket.numberTicket = numberTickets
                            ticketRepository.createTicket(ticket)
                        } else {
                            ticketRepository.updateTicket(ticket, numberTickets)
                            ticketsOrdered = ticketRepository.findAll()
                            i = 1
                        }
                        mAlertDialog.dismiss()
                    }
                }
                annulerButton.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
            mAlertDialog.show()
            return i
        }

        //---------------------------------------------------------------------
        // Members
        //---------------------------------------------------------------------

        lateinit var ticketsOrdered: ArrayList<Ticket>
        lateinit var ticketRepository: TicketRepository
        lateinit var listTicketsAdapter: ListTicketsAdapter
    }
}