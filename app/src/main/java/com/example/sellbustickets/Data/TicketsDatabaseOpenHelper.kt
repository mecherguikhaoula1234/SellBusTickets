package com.example.sellbustickets.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.sellbustickets.Data.Constantes.Companion.TICKETS_DB_NAME
import com.example.sellbustickets.Data.Constantes.Companion.TICKET_TABLE_NAME
import com.example.sellbustickets.Data.Constantes.Companion.TRANSACTION_TABLE_NAME
import org.jetbrains.anko.db.*

class TicketsDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, TICKETS_DB_NAME, null, 7) {
    companion object {
        private var instance: TicketsDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): TicketsDatabaseOpenHelper {
            if (instance == null) {
                instance = TicketsDatabaseOpenHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(database: SQLiteDatabase) {
        database.createTable(TICKET_TABLE_NAME, true,
            "idTicket" to INTEGER + NOT_NULL + PRIMARY_KEY + AUTOINCREMENT,
            "typeTicket" to TEXT ,
            "priceTicket" to REAL,
            "numberTicket" to INTEGER)

        database.createTable(TRANSACTION_TABLE_NAME, true,
            "id" to INTEGER + NOT_NULL + PRIMARY_KEY + AUTOINCREMENT,
                    "idTransaction" to TEXT,
                    "amountTransaction" to INTEGER )
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion == 6 && newVersion==7) {                    
            database.dropTable(TICKET_TABLE_NAME, ifExists = true)   
            database.createTable(TICKET_TABLE_NAME,                          
                                 true,                             
                                 "idTicket" to INTEGER + NOT_NULL + PRIMARY_KEY + AUTOINCREMENT,                             
                                 "typeTicket" to TEXT ,                                      
                                 "priceTicket" to REAL,                                        
                                 "numberTicket" to INTEGER)
     
            database.dropTable(TRANSACTION_TABLE_NAME, ifExists = true)
            database.createTable(TRANSACTION_TABLE_NAME, true,                                    
                                 "id" to INTEGER + NOT_NULL + PRIMARY_KEY + AUTOINCREMENT,                                              
                                 "idTransaction" to TEXT,                                                 
                                 "amountTransaction" to INTEGER )             
        }
    }
}
