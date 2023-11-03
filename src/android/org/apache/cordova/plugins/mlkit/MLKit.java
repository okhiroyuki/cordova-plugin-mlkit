package org.apache.cordova.plugins.mlkit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;

/**
 * This class echoes a string called from JavaScript.
 */
public class MLKit extends CordovaPlugin {

    protected static Context applicationContext = null;

  @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
      Activity cordovaActivity = this.cordova.getActivity();
        applicationContext = cordovaActivity.getApplicationContext();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      switch (action) {
        case "onDeviceTextRecognizer": {
          String message = args.getString(0);
          this.onDeviceTextRecognizer(message, callbackContext);
          return true;
        }
        case "barcodeDetector": {
          String message = args.getString(0);
          this.barcodeDetector(message, callbackContext);
          return true;
        }
        case "imageLabeler": {
          String message = args.getString(0);
          this.imageLabeler(message, callbackContext);
          return true;
        }
      }
        return false;
    }

    private void onDeviceTextRecognizer(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            try {
                InputImage image = getImage(message);
                TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                recognizer.process(image)
                        .addOnSuccessListener(firebaseVisionText -> {
                            try {
                                JSONObject text = MLKitUtils.parseText(image, firebaseVisionText);
                                callbackContext.success(text);
                            } catch (Exception e) {
                                callbackContext.error(e.getLocalizedMessage());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callbackContext.error(e.getLocalizedMessage());
                            }
                        });
            } catch (Exception e) {
                callbackContext.error(e.getLocalizedMessage());
            }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void barcodeDetector(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            try {
                InputImage image = getImage(message);
                BarcodeScanner detector = BarcodeScanning.getClient();
                detector.process(image)
                        .addOnSuccessListener(firebaseVisionBarcodes -> {
                            try {
                                JSONArray barcodes = MLKitUtils.parseBarcodes(image, firebaseVisionBarcodes);
                                callbackContext.success(barcodes);
                            } catch (Exception e) {
                                callbackContext.error(e.getLocalizedMessage());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callbackContext.error(e.getLocalizedMessage());
                            }
                        });
            } catch (Exception e) {
                callbackContext.error(e.getLocalizedMessage());
            }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void imageLabeler(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            try {
                InputImage image = getImage(message);
                ImageLabeler detector = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
                detector.process(image)
                        .addOnSuccessListener(imageLabels -> {
                            try {
                                JSONArray imageLabels1 = MLKitUtils.parseImageLabels(imageLabels);
                                callbackContext.success(imageLabels1);
                            } catch (Exception e) {
                                callbackContext.error(e.getLocalizedMessage());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callbackContext.error(e.getLocalizedMessage());
                            }
                        });
            } catch (Exception e) {
                callbackContext.error(e.getLocalizedMessage());
            }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private InputImage getImage(String message) throws IOException {
        if (message.contains("data:")) {
            message = message
                    .replace("data:image/png;base64,", "")
                    .replace("data:image/jpeg;base64,", "");
            byte[] decodedString = Base64.decode(message, Base64.DEFAULT);
            Bitmap bitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
          return InputImage.fromBitmap(bitMap, 0);
        } else {
            Uri uri = Uri.parse(message);
          return InputImage.fromFilePath(applicationContext, uri);
        }
    }
}
