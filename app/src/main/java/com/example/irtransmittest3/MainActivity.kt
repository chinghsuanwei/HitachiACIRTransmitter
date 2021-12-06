package com.example.irtransmittest3

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.ConsumerIrManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var m_cosumerIrManager: ConsumerIrManager
    lateinit var m_alarmManager: AlarmManager
    val LOCAL_NOTIFICATION: String = "test"
    var m_receiver: AlarmBroadcastReceiver = AlarmBroadcastReceiver()
    lateinit var countdown_timer: CountDownTimer
    lateinit var countdown_timer2: CountDownTimer
    lateinit var countdown_timer3: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        m_cosumerIrManager = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager
        m_alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        registerReceiver(m_receiver, IntentFilter(LOCAL_NOTIFICATION))

        val calendar1: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            if (get(Calendar.HOUR_OF_DAY) !in 0..3)
            {
                set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH)+1)
            }
            set(Calendar.HOUR_OF_DAY, 2)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val calendar2: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            if (get(Calendar.HOUR_OF_DAY) !in 0..3)
            {
                set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH)+1)
            }
            set(Calendar.HOUR_OF_DAY, 5)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val calendar3: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            if (get(Calendar.HOUR_OF_DAY) !in 0..3)
            {
                set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH)+1)
            }
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val alarmIntent = Intent(this, AlarmBroadcastReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

//        m_alarmManager?.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            alarmIntent
//        )

//        m_alarmManager?.set(
//            AlarmManager.ELAPSED_REALTIME_WAKEUP,
//            SystemClock.elapsedRealtime() + 5 * 1000,
//            alarmIntent
//        )

        m_cosumerIrManager.transmit(38000, Constants.TURN_ON)

        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
        countdown_timer = object : CountDownTimer(calendar1.timeInMillis - System.currentTimeMillis(), 10000000) {
            override fun onFinish() {
                //Toast.makeText(this@MainActivity, "Check it out", Toast.LENGTH_SHORT).show()
                for (i in 0..2) {
                    m_cosumerIrManager.transmit(38000, Constants.AC_25_MED_NO_POWER)
                    Thread.sleep(2000L)
                }
            }

            override fun onTick(p0: Long) {
            }
        }
        countdown_timer.start()

        countdown_timer2 = object : CountDownTimer(calendar2.timeInMillis - System.currentTimeMillis(), 10000000) {
            override fun onFinish() {
                //Toast.makeText(this@MainActivity, "Check it out", Toast.LENGTH_SHORT).show()
                for (i in 0..2) {
                    m_cosumerIrManager.transmit(38000, Constants.AC_26_MED_NO_POWER)
                    Thread.sleep(5000L)
                }
            }

            override fun onTick(p0: Long) {
            }
        }
        countdown_timer2.start()

        countdown_timer3 = object : CountDownTimer(calendar3.timeInMillis - System.currentTimeMillis(), 10000000) {
            override fun onFinish() {
                //Toast.makeText(this@MainActivity, "Check it out", Toast.LENGTH_SHORT).show()
                for (i in 0..2) {
                    m_cosumerIrManager.transmit(38000, Constants.AC_26_MED_NO_POWER)
                    Thread.sleep(5000L)
                }
            }

            override fun onTick(p0: Long) {
            }
        }
        countdown_timer3.start()

        val textView1: TextView = findViewById<TextView>(R.id.countdown1) as TextView
        textView1.text = (calendar1.timeInMillis - System.currentTimeMillis()).toString()

        val textView2: TextView = findViewById<TextView>(R.id.countdown2) as TextView
        textView2.text = (calendar2.timeInMillis - System.currentTimeMillis()).toString()

        val textView3: TextView = findViewById<TextView>(R.id.countdown3) as TextView
        textView3.text = (calendar3.timeInMillis - System.currentTimeMillis()).toString()
    }

    fun transmitbuttonOnClick(view: View)
    {
        m_cosumerIrManager.transmit(38000, Constants.TURN_ON)
    }

    public class AlarmBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context!!, "Check it out", Toast.LENGTH_SHORT).show()
        }
    }
}