//BT:00:11:62:19:51:37

package com.example.restaurantapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.starmicronics.stario.StarIOPort
import com.starmicronics.starioextension.ICommandBuilder
import com.starmicronics.starioextension.StarIoExt
import android.graphics.Typeface

object StarPrinterHelper {

    // ⭐ Add helper here
    private fun textToBitmap(text: String): Bitmap {
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 28f
            isAntiAlias = true
            typeface = Typeface.MONOSPACE
        }

        val maxWidth = 554  // TSP100III printable width

        // ✅ Proper font metrics (no guessing)
        val fontMetrics = paint.fontMetrics
        val lineHeight = (fontMetrics.bottom - fontMetrics.top + fontMetrics.leading).toInt()

        val wrappedLines = mutableListOf<String>()

        // ✅ Safe word-based wrapping (no text loss)
        val originalLines = text.split("\n")

        for (line in originalLines) {
            if (line.isBlank()) {
                wrappedLines.add("") // preserve empty lines
                continue
            }

            val words = line.split(" ")
            var currentLine = ""

            for (word in words) {
                val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"

                if (paint.measureText(testLine) <= maxWidth) {
                    currentLine = testLine
                } else {
                    if (currentLine.isNotEmpty()) {
                        wrappedLines.add(currentLine)
                    }
                    currentLine = word
                }
            }

            if (currentLine.isNotEmpty()) {
                wrappedLines.add(currentLine)
            }
        }

        // ✅ Extra padding to prevent clipping
        val height = lineHeight * wrappedLines.size + (lineHeight * 1)

        val bitmap = Bitmap.createBitmap(maxWidth, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        var y = -fontMetrics.top  // correct baseline start

        for (line in wrappedLines) {
            canvas.drawText(line, 0f, y, paint)
            y += lineHeight
        }

        return bitmap
    }

    fun print(context: Context, text: String) {
        var port: StarIOPort? = null

        try {
            val portName = "BT:00:11:62:19:51:37"
            val portSettings = ""

            port = StarIOPort.getPort(portName, portSettings, 10000, context)

            // ⭐ START CHECKED BLOCK (CRITICAL)
            val status = port.beginCheckedBlock()

            if (status.offline) {
                throw Exception("Printer is offline")
            }

            val builder = StarIoExt.createCommandBuilder(StarIoExt.Emulation.StarGraphic)

            builder.beginDocument()

            val bitmap = textToBitmap(text)

            builder.appendBitmap(bitmap, false)

            // ⭐ FORCE FEED
            builder.appendUnitFeed(80)

            builder.appendCutPaper(ICommandBuilder.CutPaperAction.PartialCutWithFeed)

            builder.endDocument()

            val commands = builder.commands

            port.writePort(commands, 0, commands.size)

            // ⭐ END CHECKED BLOCK (THIS FLUSHES PRINT)
            port.endCheckedBlock()

        } catch (e: Exception) {
            throw Exception("Star print failed: ${e.message}")
        } finally {
            port?.let { StarIOPort.releasePort(it) }
        }
    }
}
