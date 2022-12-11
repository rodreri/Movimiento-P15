package mx.erick.movimiento

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.erick.movimiento.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private val gravedad = SensorManager.STANDARD_GRAVITY
    private var x = 0.0
    private var y = 0.0
    private var z = 0.0
    private var a = 0.0
    private var amax = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        val process = thread {
            while(true) {
                Thread.sleep(100)
                runOnUiThread(CambioValores())
            }
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        x = event!!.values[0].toDouble()
        y = event!!.values[1].toDouble()
        z = event!!.values[2].toDouble()
        a = Math.sqrt(x * x + y * y + z * z).toDouble()
        if (a > amax) {
            amax = a
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
    inner class CambioValores : Runnable {
        override fun run() {
            binding.textViewAx.text = x.toString()
            binding.textViewAy.text = y.toString()
            binding.textViewAz.text = z.toString()
            binding.textViewAmaxima.text=amax.toString()
            binding.textViewAmodulo.text=a.toString()
            binding.textViewGravedad.text=gravedad.toString()
        }
    }
}