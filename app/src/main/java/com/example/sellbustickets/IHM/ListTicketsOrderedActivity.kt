package com.example.sellbustickets.IHM

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.sellbustickets.Data.Adapters.ListTicketsAdapter
import com.example.sellbustickets.Data.DialogUtil
import com.example.sellbustickets.Data.DialogUtil.Companion.listTicketsAdapter
import com.example.sellbustickets.Data.DialogUtil.Companion.ticketRepository
import com.example.sellbustickets.Data.DialogUtil.Companion.ticketsOrdered
import com.example.sellbustickets.Data.Models.Ticket
import com.example.sellbustickets.Data.Models.Transaction
import com.example.sellbustickets.Data.TicketRepository
import com.example.sellbustickets.Data.TransactionsRepository
import com.example.sellbustickets.R
import kotlinx.android.synthetic.main.activity_list_tickets_ordered.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.json.JSONArray
import org.json.JSONException
import kotlin.math.roundToInt

class ListTicketsOrderedActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tickets_ordered)
        supportActionBar?.title = getString(R.string.list_tickets)
        initComponent()
        setEnvent()
    }

    /**
     * initialize the components
     */
    fun initComponent() {
        ticketRepository = TicketRepository(this)
        ticketsOrdered =  ticketRepository.findAll()
        listTicketsAdapter = ListTicketsAdapter(this, ticketsOrdered)
        transactionsRepository = TransactionsRepository(this)
        listTickets_listView.adapter = listTicketsAdapter
        totalOfPayment = calculateTotalPayment(ticketsOrdered).roundToInt()
        total_payment_textview.text = "${getString(R.string.total_payment)} ${totalOfPayment} €"
    }

    /**
     * manage the listeneres of the class ListTicketsOrderedActivity
     */
    fun setEnvent() {
        continueToOrder_textView.setOnClickListener{
            finish()
            startActivity<OrderTicketsActivity>()
        }

        listTickets_listView.onItemClickListener =
            AdapterView.OnItemClickListener { p0, _, p2, _ ->
                var itemTicket = p0.getItemAtPosition(p2) as Ticket
                var i: Int

                 i = DialogUtil.createNumberOfTickets(this, itemTicket, getString(R.string.modify_number_ticket_text))
                if (i ==1) {
                    listTicketsAdapter.notifyDataSetChanged()
                }
            }
        configureBottomView()
    }

    /**
     * Configure BottomNavigationView Listener
     */
    private fun configureBottomView() {
        bottom_navigation_payer_tickets.setOnNavigationItemSelectedListener { item -> addActionBottomViewClick(item.itemId) }
    }

    /**
     * add an action of click on the differents menus of the BottomNavigationView
     */
    private fun addActionBottomViewClick(item: Int): Boolean {
        when (item) {
            R.id.pay_tickets_button -> {
                alert (getString(R.string.confirm_payment)) {
                    positiveButton(getString(R.string.yes_text)) {
                        startPaymentOnYavinPay()
                    }
                    negativeButton(getString(R.string.no_text)) {}
                }.show()
            }

            R.id.cancel_payment_button -> {
                alert (getString(R.string.cancel_payment)) {
                    positiveButton(getString(R.string.yes_text)) {
                        returnPrincipalMenu()
                    }
                    negativeButton(getString(R.string.no_text)) {}
                }.show()
            }
        }
        return true
    }

    fun calculateTotalPayment(ticketsOrdered: ArrayList<Ticket>): Double {
        var totalPayment = 0.0

        for (i in 0 until ticketsOrdered.size) {
            totalPayment += ((ticketsOrdered[i].numberTicket)?.times(ticketsOrdered[i].priceTicket!!)!!)
        }

        return totalPayment
    }

    private fun startPaymentOnYavinPay() {
        val intent = Intent()
        intent.component = ComponentName("com.yavin.macewindu", "com.yavin.macewindu.PaymentActivity")
        intent.putExtra("vendorToken", "gXMXCrdFLYQq9PF8qdLy4vHaYF0G6pdhZjUhRVo6TkibNrBbHa")
        intent.putExtra("amount", "${totalOfPayment}")
        intent.putExtra("currency", "EUR")
        intent.putExtra("transactionType", "Debit")
        intent.putExtra("reference", "12354")
        intent.putExtra("client", "{\"phone\":\"27944014\",\"email\":\"mecherguikhaoula1234@gmail.com\"}")
        val jArray = JSONArray("[\"hello printer\", \"this is a\", \"    wonderful\", \"        TICKET\"]")
        val receiptTicket = ArrayList<String>()
        for (i in 0 until jArray.length()) {
            try {
                receiptTicket.add(jArray.getString(i))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        intent.putExtra("receiptTicket", receiptTicket)
        startActivityForResult(intent, 1111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == 1111) {
            // handle post payment response from Yavin PAY
            val bundle: Bundle = intent!!.extras!!
            var transactionCreated = Transaction()

            Log.d("clientTicket", bundle["clientTicket"].toString())
            Log.d("status", bundle["status"].toString())
            Log.d("signatureRequired", bundle["signatureRequired"].toString())
            Log.d("transactionId", bundle["transactionId"].toString())
            transactionCreated.idTransaction = bundle["transactionId"].toString()
            transactionCreated.amountTransaction = totalOfPayment
            transactionsRepository.createTransaction(transactionCreated)
            returnPrincipalMenu()
        }
    }

    fun returnPrincipalMenu() {
        finish()
        ticketRepository.deleteAll()
        startActivity<MainMenuActivity>()
    }

    //---------------------------------------------------------------------
    // Members
    //---------------------------------------------------------------------

    lateinit var transactionsRepository: TransactionsRepository
    var          totalOfPayment: Int = 0
}