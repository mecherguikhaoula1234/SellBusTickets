package com.example.sellbustickets.Data

import android.content.Context
import com.example.sellbustickets.Data.Models.Transaction
import database
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TransactionsRepository(val context: Context) {

    fun findAll() : ArrayList<Transaction> = context.database.use {
        val transactions = ArrayList<Transaction>()

        select(Constantes.TRANSACTION_TABLE_NAME, "id", "idTransaction", "amountTransaction")
            .parseList(object: MapRowParser<List<Transaction>> {
                override fun parseRow(columns: Map<String, Any?>): List<Transaction> {
                    val id = columns.getValue("id")
                    val idTransaction = columns.getValue("idTransaction")
                    val amountTransaction = columns.getValue("amountTransaction")

                    val transaction = Transaction(id.toString().toLong(), idTransaction.toString(), amountTransaction.toString().toInt())

                    transactions.add(transaction)

                    return transactions
                }
            })
        transactions
    }

    fun createTransaction(transaction: Transaction) = context.database.use {
            insert(Constantes.TRANSACTION_TABLE_NAME,
                "idTransaction" to transaction.idTransaction,
                   "amountTransaction" to transaction.amountTransaction)
    }
}
