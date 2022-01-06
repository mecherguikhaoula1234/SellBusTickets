package com.example.sellbustickets.IHM

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sellbustickets.Data.Models.Transaction
import com.example.sellbustickets.Data.TransactionsRepository
import com.example.sellbustickets.R
import kotlinx.android.synthetic.main.activity_main_menu.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity

class MainMenuActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        supportActionBar?.setTitle(getString(R.string.welcome_app))
        initComponent()
        setEvent()
    }

    /**
     * initialize the components
     */
    fun initComponent() {
        transactionsRepository = TransactionsRepository(this)
        listTransactions = transactionsRepository.findAll()
    }

    /**
     * manage the listeners of the class MainMenuActivity
     */
    fun setEvent() {
        history_transactions_materialbutton.setOnClickListener {
            if (listTransactions.size > 0) {
                startActivity<ListTransactionsActivity>()
            } else {
                alert(getString(R.string.no_transaction_text)) {
                    positiveButton(getString(R.string.ok_text)) {}
                }.show()
            }
        }

        order_ticket_materialbutton.setOnClickListener {
            startActivity<OrderTicketsActivity>()
        }
    }

    //---------------------------------------------------------------------
    // Members
    //---------------------------------------------------------------------

    lateinit var transactionsRepository: TransactionsRepository
    lateinit var listTransactions: ArrayList<Transaction>
}