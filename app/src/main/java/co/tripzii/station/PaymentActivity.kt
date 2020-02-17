package co.tripzii.station

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    private var bitmap: Bitmap? = null

    private var qrCodeImageView: ImageView? = null

    private var pickupLocationTextInput: TextInputEditText? = null

    private val progressBar = ProgressBarActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        supportActionBar?.title = "QR payment"
        qrCodeImageView = findViewById(R.id.qrCodeImageView)
        val intent = Intent()
        pickupLocationTextInput?.setText(intent.getStringExtra("pickupLocation"))
        bitmap = textToImageEncode(pickupLocationTextInput.toString())
        qrCodeImageView!!.setImageBitmap(bitmap)
        val path = saveImage(bitmap) // give read write permission
        Toast.makeText(this, "QRCode saved to -> $path", Toast.LENGTH_SHORT).show()
        conFirmButton.setOnClickListener {
            progressBar.show(this, "Confirming your trip...")
            Handler().postDelayed({}, 2000)
        }
    }

    private fun saveImage(myBitmap: Bitmap?): String {
        val bytes = ByteArrayOutputStream()
        myBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY) // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dir", "" + wallpaperDirectory.mkdirs())
            wallpaperDirectory.mkdirs()
        }
        try {
            val f = File(wallpaperDirectory, Calendar.getInstance()
                .timeInMillis.toString() + ".jpg")
            f.createNewFile() // give read write permission
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.path),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "" + f.absolutePath)
            return f.absolutePath
        } catch (e1: IOException) { e1.printStackTrace() }
        return ""
    }

    @Throws(WriterException::class)
    private fun textToImageEncode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                QrCodeWidth, QrCodeWidth, null)
        } catch (Illumination: IllegalArgumentException) {
            return null
        }
        val bitMatrixWidth = bitMatrix.getWidth()
        val bitMatrixHeight = bitMatrix.getHeight()
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.black)
                else
                    resources.getColor(R.color.white)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }

    companion object {
        var QrCodeWidth = 500
        private var IMAGE_DIRECTORY = "/QrCodeDocument"
    }
}
