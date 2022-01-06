package com.example.sellbustickets.IHM

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.sellbustickets.Data.DialogUtil.Companion.createNumberOfTickets
import com.example.sellbustickets.Data.DialogUtil.Companion.ticketRepository
import com.example.sellbustickets.Data.DialogUtil.Companion.ticketsOrdered
import com.example.sellbustickets.Data.Models.Ticket
import com.example.sellbustickets.Data.TicketRepository
import com.example.sellbustickets.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity

class OrderTicketsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_ticket)
        supportActionBar?.title = getString(R.string.title_sellBusTickets)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initComponent()
        setEvent()
    }

    fun initComponent() {
        singleJourneyButton = findViewById(R.id.singleJourneyTicket_Button)
        dayTicketButton = findViewById(R.id.dayTicket_button)
        weekJourneyButton = findViewById(R.id.weekTicket_button)
        singleJourneyButton.text = "${getString(R.string.single_journey_text)} \n 1,10 €"
        dayTicketButton.text = "${getString(R.string.day_ticket_text)} \n 2,50 €"
        weekJourneyButton.text = "${getString(R.string.week_ticket_text)} \n 12 €"
        ticketRepository = TicketRepository(this)
    }

    /**
     * manage the listeners of the class MainActivity
     */
    fun setEvent() {
        var ticketToOrder: Ticket
        var title = getString(R.string.number_tickets_text)

        singleJourneyButton.setOnClickListener {
            ticketToOrder = verifyTicketOrdered(getString(R.string.single_journey_text), 1.10)
            createNumberOfTickets(this, ticketToOrder, title)
        }

        dayTicketButton.setOnClickListener {
            ticketToOrder = verifyTicketOrdered(getString(R.string.day_ticket_text), 2.50)
            createNumberOfTickets(this, ticketToOrder, title)

        }

        weekJourneyButton.setOnClickListener {
            ticketToOrder = verifyTicketOrdered(getString(R.string.week_ticket_text), 12.0)
            createNumberOfTickets(this, ticketToOrder, title)
        }
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        ticketsOrdered = ticketRepository.findAll()
        if (ticketsOrdered.size > 0) {
            startActivity<ListTicketsOrderedActivity>()
            finish()
        } else {
            alert(getString(R.string.no_ordered_tickets_error)) {
                positiveButton(getString(R.string.ok_text)) {} }.show()
        }
        return true
    }

    fun verifyTicketOrdered(type: String, price:Double): Ticket {
        ticketsOrdered = ticketRepository.findAll()
        var ticketToOrder = Ticket()

        for(i in 0 until ticketsOrdered.size) {
            if (ticketsOrdered[i].typeTicket.equals(type)) {
                ticketToOrder = ticketsOrdered[i]
                break
            }
        }
        if (ticketToOrder.typeTicket.isNullOrEmpty()) {
            ticketToOrder.typeTicket = type
            ticketToOrder.priceTicket = price
        }

        return ticketToOrder
    }

    //---------------------------------------------------------------------
    // Members
    //---------------------------------------------------------------------

    lateinit var singleJourneyButton: Button
    lateinit var dayTicketButton: Button
    lateinit var weekJourneyButton: Button
}