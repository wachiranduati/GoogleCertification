package nduati.soc.googlecertification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nduati.soc.googlecertification.models.Word
import nduati.soc.googlecertification.models.WordRepository
import nduati.soc.googlecertification.models.WordViewModel
import nduati.soc.googlecertification.utils.RefreshDataWorker
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(){
//    private val TAG: String = MyActivity::class.java.simpleName
    private val TAG  = MainActivity::class.java.simpleName
    lateinit var ToastButton : Button
    lateinit var SnackButton : Button
    lateinit var MainTextView : TextView
    private lateinit var wordViewModel: WordViewModel
    lateinit var addValBtn : Button
    lateinit var NuValEdtTxt : EditText
    lateinit var WorkManagerButton : Button
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainTextView = findViewById(R.id.textViewMain)
        addValBtn = findViewById(R.id.addButtonValue)
        NuValEdtTxt = findViewById(R.id.editTextNuVal)
        WorkManagerButton = findViewById(R.id.workmanager_button)
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer {words ->
            val itemlng : Int = words.size
            var lwd: String = "nothing"
            if(itemlng > 0){
                lwd = words[itemlng-1].word
            }
          words?.let{MainTextView.setText(lwd)}
        })

        addValBtn.setOnClickListener {
            val newWrod : String? = NuValEdtTxt.text.toString()
            newWrod?.let { it1 -> Word(it1) }?.let { it2 -> wordViewModel.insert(it2) }
            Toast.makeText(this, "Inserted", Toast.LENGTH_LONG).show()
        }

        initializeViews()
        initiateService()

    }
    fun addNums(fnum: Int, snum: Int): Int{
        return fnum + snum
    }

    private fun delayedInit(){
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork(){
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .apply {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        setRequiresDeviceIdle(true)
                    }
                }
                .build()
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
        )
    }

    private fun notifyBuana() {
        Toast.makeText(this, "toast called", Toast.LENGTH_LONG).show()
        val CHANNEL_ID : String = "1238"
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Started by a bound service"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "some name"
            val descriptionText = "description text"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(3456, builder.build())
        }
    }

    private fun initiateService() {
        var intent : Intent = Intent(this, MyIntentService::class.java)
        intent.putExtra("intent", "Nick Banana")
        startService(intent)
    }

    private fun initializeViews() {
        ToastButton = findViewById(R.id.toast_button)
        WorkManagerButton.setOnClickListener {
            val inputVal : String = NuValEdtTxt.text.toString()
            if(inputVal != ""){
//                toast(inputVal)
                delayedInit()
            }
        }

    }


}
