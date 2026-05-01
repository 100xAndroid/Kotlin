package cz.stokratandroid.video3

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Range
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    private var textureView: TextureView? = null
    private var kamera: CameraDevice? = null
    private var velikostObrazu: Size? = null
    private var captureRequestBuilder: CaptureRequest.Builder? = null
    private var cameraSession: CameraCaptureSession? = null
    private var backgroundHandler: Handler? = null
    private var handlerThread: HandlerThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textureView = findViewById<View>(R.id.textureView) as TextureView
    }

    var textureListener: TextureView.SurfaceTextureListener = object : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            spustitKameru()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        }
    }

    private fun spustitKameru() {

        // test pristupu ke kamere
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Aplikace nemá  přístup ke kameře", Toast.LENGTH_LONG).show()
            return
        }
        val manager = getSystemService(CAMERA_SERVICE) as CameraManager
        try {
            // zjistit ID hlavni kamery
            val kameraId = manager.cameraIdList[0]
            val characteristics = manager.getCameraCharacteristics(kameraId)
            val vlastnostiKamery =
                characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

            // zjistit rozliseni kamery
            velikostObrazu = vlastnostiKamery!!.getOutputSizes(SurfaceTexture::class.java)[0]

            // spusteni kamery
            manager.openCamera(kameraId, stateCallback, null)

        } catch (e: CameraAccessException) {
            e.printStackTrace()
            Toast.makeText(this, "Chyba: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    private val stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            // udalost vyvolana pri spusteni kamery
            kamera = camera
            kameraPreviewStart()
        }

        override fun onDisconnected(camera: CameraDevice) {
            kamera!!.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            kamera!!.close()
            kamera = null
            Toast.makeText(
                applicationContext,
                String.format("Chyba kamery: %i", error),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun kameraPreviewStart() {
        try {
            val texture = textureView!!.surfaceTexture
            if (texture == null) {
                Toast.makeText(this, "Chyba, texture je null", Toast.LENGTH_LONG).show()
                return
            }
            texture.setDefaultBufferSize(velikostObrazu!!.width, velikostObrazu!!.height)
            val surface = Surface(texture)
            captureRequestBuilder = kamera!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder!!.addTarget(surface)
            kamera!!.createCaptureSession(
                Arrays.asList(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        // kamera není pripravena
                        if (kamera == null) {
                            return
                        }
                        // pokud je session pripravena, zahájít preview
                        cameraSession = cameraCaptureSession
                        updatePreview()
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                        Toast.makeText(applicationContext, "Chyba konfigurace", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            Toast.makeText(this, "Chyba: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun updatePreview() {
        if (kamera == null) {
            return
        }
        captureRequestBuilder!!.set(
            CaptureRequest.CONTROL_AE_MODE,
            CaptureRequest.CONTROL_AE_MODE_ON
        )
        // -- dalsi mozna nastaveni --
        // captureRequestBuilder!!.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        // captureRequestBuilder!!.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_OFF)
        // captureRequestBuilder!!.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE_OFF)
        // captureRequestBuilder!!.set(CaptureRequest.LENS_FOCUS_DISTANCE, .2f)
        // captureRequestBuilder!!.set(CaptureRequest.SENSOR_EXPOSURE_TIME, 1000000L)
        // captureRequestBuilder!!.set(CaptureRequest.CONTROL_CAPTURE_INTENT, CaptureRequest.CONTROL_CAPTURE_INTENT_STILL_CAPTURE)
        // captureRequestBuilder!!.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, CaptureRequest.CONTROL_AE_MODE_ON)
        // captureRequestBuilder!!.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_CANCEL)
        // captureRequestBuilder!!.set(CaptureRequest.SENSOR_SENSITIVITY, 100)
        // captureRequestBuilder!!.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, 1)

        captureRequestBuilder!!.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, Range<Int?>(15, 15))

        try {
            cameraSession!!.setRepeatingRequest(
                captureRequestBuilder!!.build(),
                null,
                backgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            Toast.makeText(this, "Chyba: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    private val range: Range<Int>?
        private get() {
            var chars: CameraCharacteristics?
            return try {
                val manager = getSystemService(CAMERA_SERVICE) as CameraManager
                chars = manager.getCameraCharacteristics(manager.cameraIdList[0])
                val ranges =
                    chars.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)!!
                var result: Range<Int>? = null
                for (range in ranges) {
                    val upper = range.upper
                    // 10 - min range upper for my needs
                    if (upper >= 10) {
                        if (result == null || upper < result.upper) {
                            result = range
                        }
                    }
                }
                if (result == null) {
                    result = ranges[0]
                }
                result
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                null
            }
        }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        if (textureView!!.isAvailable) {
            spustitKameru()
        } else {
            textureView!!.surfaceTextureListener = textureListener
        }
    }

    private fun startBackgroundThread() {
        handlerThread = HandlerThread("Camera background")
        handlerThread!!.start()
        backgroundHandler = Handler(handlerThread!!.looper)
    }

    override fun onPause() {
        stopBackgroundThread()
        super.onPause()
    }

    private fun stopBackgroundThread() {
        handlerThread!!.quitSafely()
        try {
            handlerThread!!.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        backgroundHandler = null
        handlerThread = null
    }
}