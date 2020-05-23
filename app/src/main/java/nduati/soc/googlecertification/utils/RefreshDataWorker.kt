package nduati.soc.googlecertification.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class RefreshDataWorker(appContext : Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    val TAG : String = "RefreshDataWorker"

    companion object{
        const val WORK_NAME = "nduati.soc.googlecertification.utils.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Work request for sync is run")
        return Result.success()
    }

}