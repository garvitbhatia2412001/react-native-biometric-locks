package com.biometriclocks

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.UiThreadUtil;
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import android.os.Build
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

class BiometricLocksModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  private var responsePromise: Promise? = null

  private fun showBiometricPrompt() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      UiThreadUtil.runOnUiThread(Runnable {
        kotlin.run {
          try {
            val activity = currentActivity
            if(activity == null) {
              val resultMap: WritableMap = WritableNativeMap()
              resultMap.putBoolean("result", false);
              resultMap.putString("message", "Something went wrong with activity #BLMSBP1");
              responsePromise!!.resolve(resultMap)
            }

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
              .setTitle("Biometric login")
              .setSubtitle("Log in using your biometric credential")
              .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
              .build()

            val fragmentActivity = currentActivity as FragmentActivity?
            val executor: Executor = Executors.newSingleThreadExecutor()
            val biometricPrompt = BiometricPrompt(fragmentActivity!!, executor,
              object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,errString: CharSequence) {
                  super.onAuthenticationError(errorCode, errString)
                  val resultMap: WritableMap = WritableNativeMap()
                  resultMap.putBoolean("result", false);
                  resultMap.putString("message", "Authentication Error");
                  responsePromise!!.resolve(resultMap);
                }

                override fun onAuthenticationSucceeded(
                  result: BiometricPrompt.AuthenticationResult) {
                  super.onAuthenticationSucceeded(result)
                  val resultMap: WritableMap = WritableNativeMap()
                  resultMap.putBoolean("result", true);
                  resultMap.putString("message", "SUCCESS");
                  responsePromise!!.resolve(resultMap);
                }

                override fun onAuthenticationFailed() {
                  super.onAuthenticationFailed()
                  val resultMap: WritableMap = WritableNativeMap()
                  resultMap.putBoolean("result", false);
                  resultMap.putString("message", "FAILED");
                  responsePromise!!.resolve(resultMap);
                }
              })

            biometricPrompt.authenticate(promptInfo)
          } catch (e: Exception) {
            val resultMap: WritableMap = WritableNativeMap()
            resultMap.putBoolean("result", false);
            resultMap.putString("message", "No biometric features available on this device.");
            responsePromise!!.resolve(resultMap);
          }
        }
      })
    }
  }

  private fun checkDeviceHasBiometric() {

    try{
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val biometricManager = BiometricManager.from(reactApplicationContext)

        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)){
          BiometricManager.BIOMETRIC_SUCCESS -> {
            showBiometricPrompt();
          }
          BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            val resultMap: WritableMap = WritableNativeMap()
            resultMap.putBoolean("result", false);
            resultMap.putString("message", "No biometric features available on this device");
            responsePromise!!.resolve(resultMap);
          }
          BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            val resultMap: WritableMap = WritableNativeMap()
            resultMap.putBoolean("result", false);
            resultMap.putString("message", "Biometric features are currently unavailable");
            responsePromise!!.resolve(resultMap);
          }
          BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            val resultMap: WritableMap = WritableNativeMap()
            resultMap.putBoolean("result", true);
            resultMap.putString("message", "Biometric features not enrolled");
            responsePromise!!.resolve(resultMap);
          }
          else->{
            val resultMap: WritableMap = WritableNativeMap()
            resultMap.putBoolean("result", false);
            resultMap.putString("message", "Something went wrong in check biometrics #BLMCDHB3");
            responsePromise!!.resolve(resultMap);
          }
        }
      }
    }catch (e: Exception) {
      val resultMap: WritableMap = WritableNativeMap()
      resultMap.putBoolean("result", false);
      resultMap.putString("message", "SDK version is not compatible with version codes #BLMCDHB2");
      responsePromise!!.resolve(resultMap);
    }
  }

  @ReactMethod
  fun openBiometricAuthenticatePrompt(promise: Promise) {
    try {
      responsePromise = promise;
      checkDeviceHasBiometric();
    } catch (e: Exception) {
      val resultMap: WritableMap = WritableNativeMap()
      resultMap.putBoolean("result", false);
      resultMap.putString("message", "Something went wrong #BLMCB1");
      promise.resolve(resultMap);
    }
  }

  companion object {
    const val NAME = "BiometricLocks"
  }
}
