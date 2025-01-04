package com.mahmoud.compdfexample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.compdfkit.core.annotation.CPDFAnnotation
import com.compdfkit.core.document.CPDFDocument
import com.compdfkit.core.document.CPDFDocument.PDFDocumentError
import com.compdfkit.core.edit.CPDFEditConfig
import com.compdfkit.ui.reader.CPDFReaderView
import com.mahmoud.compdfexample.util.FileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editConfig = CPDFEditConfig.Builder()
            .build()

        val readerView = findViewById<CPDFReaderView>(R.id.readerview)
        readerView.editManager.updateEditConfig(editConfig)
        val document = CPDFDocument(this)
        scope.launch {
            async {
                //val fileName = "kotlin_basic.pdf"
                val fileName = "Mcv.pdf"
                //copyPdfFromAssetsToCache(fileName)

                val file = FileUtils.getAssetsTempFile(
                    this@MainActivity,
                    fileName
                )//File(cacheDir, fileName)
               // val filePath = file.absolutePath

                // Open document.
                var error = document.open(file)
                if (error == PDFDocumentError.PDFDocumentErrorPassword) {
                    error = document.open(file, "password")
                }

                if (error == PDFDocumentError.PDFDocumentErrorSuccess) {
                    mainThreadHandler.post {
                        //document.insertBlankPage(1, 595F, 842F)
//                        val newDocument = CPDFDocument.createDocument(this@MainActivity)
//                        for (i in 0 until document.pageCount) {
//                            newDocument.importPages(document, intArrayOf(i), 0)
//                        }

                        document.pageAtIndex(0)
                        readerView.pdfDocument = document
                        readerView.viewMode = CPDFReaderView.ViewMode.VIEW

                    }
                } else {
                    Log.e("MainActivity", "Failed to open PDF: $error")
                }
            }.await()
        }

//        Thread {
//
//        }.start()
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