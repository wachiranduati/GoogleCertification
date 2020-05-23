package nduati.soc.googlecertification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import java.util.*

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "bound service", Toast.LENGTH_LONG).show()
    }
}
