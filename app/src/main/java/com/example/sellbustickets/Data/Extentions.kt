import android.content.Context
import com.example.sellbustickets.Data.TicketsDatabaseOpenHelper

/**
 * Extension de l'application context pour ajouter une réference pour le database open helper
 */
val Context.database: TicketsDatabaseOpenHelper
    get() = TicketsDatabaseOpenHelper.getInstance(applicationContext)