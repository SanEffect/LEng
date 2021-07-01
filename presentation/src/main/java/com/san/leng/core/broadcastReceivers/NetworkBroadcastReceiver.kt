package com.san.leng.core.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.san.leng.core.utils.NetworkUtil

class NetworkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (!NetworkUtil.isNetworkAvailable(context))
            Toast.makeText(context, "Internet connection is not available", Toast.LENGTH_LONG)
                .show()
    }
}
