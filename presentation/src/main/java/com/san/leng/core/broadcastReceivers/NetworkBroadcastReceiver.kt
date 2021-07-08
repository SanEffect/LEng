package com.san.leng.core.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.san.leng.R
import com.san.leng.core.utils.NetworkUtil

class NetworkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            if (!NetworkUtil.isNetworkAvailable(context))
                Toast.makeText(
                    context,
                    context.getString(R.string.no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
        }
    }
}
