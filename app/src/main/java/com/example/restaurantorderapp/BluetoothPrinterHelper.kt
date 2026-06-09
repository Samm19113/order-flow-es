package com.example.restaurantapp

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.io.OutputStream
import java.util.*

object BluetoothPrinterHelper {

    fun print(context: Context, text: String) {

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            ?: throw Exception("Bluetooth not supported")

        if (!bluetoothAdapter.isEnabled) {
            throw Exception("Bluetooth is OFF")
        }

        // ✅ Permission check (fixes your error)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw Exception("Bluetooth permission not granted")
            }
        }

        val printerName = "YourPrinterName" // ← change this

        val device: BluetoothDevice = bluetoothAdapter.bondedDevices
            .firstOrNull { it.name == printerName }
            ?: throw Exception("Printer not paired")

        val uuid: UUID = device.uuids?.get(0)?.uuid
            ?: UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        val socket = device.createRfcommSocketToServiceRecord(uuid)
        socket.connect()

        val output: OutputStream = socket.outputStream

        output.write(text.toByteArray())
        output.flush()

        output.close()
        socket.close()
    }
}