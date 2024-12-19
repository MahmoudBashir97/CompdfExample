package com.mahmoud.compdfexample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.compdfkit.core.document.CPDFDocument
import com.compdfkit.core.document.CPDFDocument.PDFDocumentError
import com.compdfkit.core.edit.CPDFEditConfig
import com.compdfkit.ui.reader.CPDFReaderView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      CPDFEditConfig.Builder()
            // Set any necessary properties
            .build()

        val readerView = findViewById<CPDFReaderView>(R.id.readerview)
        val document = CPDFDocument(this)

        Thread {
            val fileName = "Mahmoud-bashir-cv-N-24.pdf"
            copyPdfFromAssetsToCache(fileName)

            val file = File(cacheDir, fileName)
            val filePath = file.absolutePath

            // Open document.
            var error = document.open(filePath)
            if (error == PDFDocumentError.PDFDocumentErrorPassword) {
                error = document.open(filePath, "password")
            }

            if (error == PDFDocumentError.PDFDocumentErrorSuccess) {
                mainThreadHandler.post {
                    readerView.pdfDocument = document
                }
            } else {
                Log.e("MainActivity", "Failed to open PDF: $error")
            }
        }.start()
    }

    private fun copyPdfFromAssetsToCache(fileName: String) {
        try {
            val inputStream = assets.open(fileName)
            val outputFile = File(cacheDir, fileName)
            val outputStream = FileOutputStream(outputFile)

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while ((inputStream.read(buffer).also { bytesRead = it }) != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            inputStream.close()
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            Log.e("MainActivity", "Error copying PDF from assets", e)
        }
    }
}