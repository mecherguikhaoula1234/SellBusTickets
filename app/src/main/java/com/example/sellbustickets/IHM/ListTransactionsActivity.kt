package com.example.sellbustickets.IHM

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sellbustickets.Data.Adapters.ListTransactionsAdapter
import com.example.sellbustickets.Data.Models.Transaction
import com.example.sellbustickets.Data.TransactionsRepository
import com.example.sellbustickets.R
import kotlinx.android.synthetic.main.activity_list_transactions.*

class ListTransactionsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_transactions)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.list_transactions_text)
        initComponent()
    }

    /**
     * initialize the componenents
     */
    fun initComponent() {
        transactionsRepository = TransactionsRepository(this)
        listTransactions = transactionsRepository.findAll()
        listTransactionsAdapter = ListTransactionsAdapter(this, listTransactions)
        list_transaction_listview.adapter = listTransactionsAdapter
    }

    /**
     * manage the click on the backpressed button
     */
    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    //---------------------------------------------------------------------
    // Members
    //---------------------------------------------------------------------

    lateinit var listTransactions: ArrayList<Transaction>
    lateinit var transactionsRepository: TransactionsRepository
    lateinit var listTransactionsAdapter: ListTransactionsAdapter
}
