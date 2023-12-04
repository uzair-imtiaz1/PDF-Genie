package com.example.pdf_genie

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor

class ChatActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_chat)

        val upload_btn: ImageButton = findViewById(R.id.upload)

        upload_btn.setOnClickListener {
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
                        val intent:Intent=Intent()
                        intent.putExtra("ExtractedText", extractedText)
                        startActivity(Intent(this, MainActivity::class.java))
//                        extractedTV.text = extractedText
                        pdfReader.close()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
    }
}