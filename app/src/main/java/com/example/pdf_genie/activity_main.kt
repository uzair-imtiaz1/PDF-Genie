package com.example.pdf_genie

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor

class MainActivity : AppCompatActivity() {

    private lateinit var extractedTV: TextView
    private lateinit var extractBtn: Button

    // Uri for the selected PDF file
    private var selectedPdfUri: Uri? = null

    // Activity result launcher for opening the document picker
    private val openDocumentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    // Set the selected PDF Uri
                    selectedPdfUri = uri

                    // Perform extraction on the selected PDF
                    extractData()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        extractedTV = findViewById(R.id.idTVPDF)
        extractBtn = findViewById(R.id.idBtnExtract)

        extractBtn.setOnClickListener {
            // Launch the document picker
            openDocumentPicker()
        }
    }

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf" // Restrict to PDF files
        }
        openDocumentLauncher.launch(intent)
    }

    private fun extractData() {
        try {
            var extractedText = ""

            // Check if a PDF is selected
            selectedPdfUri?.let { uri ->
                // Open a PdfReader using the selected PDF Uri
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    val pdfReader = PdfReader(inputStream)

                    val n = pdfReader.numberOfPages

                    for (i in 0 until n) {
                        extractedText =
                            """
                            $extractedText${
                                PdfTextExtractor.getTextFromPage(pdfReader, i + 1).trim { it <= ' ' }
                            }
                            """.trimIndent()
                    }

                    extractedTV.text = extractedText
                    pdfReader.close()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
