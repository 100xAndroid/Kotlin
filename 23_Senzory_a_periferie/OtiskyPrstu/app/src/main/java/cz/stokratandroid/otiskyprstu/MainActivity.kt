package cz.stokratandroid.otiskyprstu

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var txtVysledek: TextView? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: PromptInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtVysledek = findViewById(R.id.txtVysledek)
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    txtVysledek!!.setText("Nepodařilo se ověřit")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    txtVysledek!!.setText("Ověřeno")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    txtVysledek!!.setText("Chyba - neověřeno")
                }
            })
        promptInfo = PromptInfo.Builder()
            .setTitle("Otisk prstu")
            .setNegativeButtonText("Zrušit")
            .setConfirmationRequired(false)
            .build()
    }

    fun testCtecky(view: View?) {

        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> Toast.makeText(
                this, "Čtečka otisku prstu je připravena.", Toast.LENGTH_LONG
            ).show()

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Toast.makeText(
                this,
                "Zařízení neobsahuje čtečku otisku prstu.",
                Toast.LENGTH_LONG
            ).show()

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Toast.makeText(
                this,
                "Zamykání pomocí otisku prstu není v Nastaveních aktivované.",
                Toast.LENGTH_LONG
            ).show()

            else -> Toast.makeText(this, "Jiná chyba.", Toast.LENGTH_LONG).show()
        }
    }

    fun overeniOtisku(view: View?) {
        biometricPrompt!!.authenticate(promptInfo!!)
    }
}