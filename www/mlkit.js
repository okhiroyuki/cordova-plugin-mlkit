const exec = require("cordova/exec");

exports.onDeviceTextRecognizer = function (image, success, error) {
  exec(success, error, "FirebaseVisionPlugin", "onDeviceTextRecognizer", [
    image,
  ]);
};

exports.barcodeDetector = function (image, success, error) {
  exec(success, error, "FirebaseVisionPlugin", "barcodeDetector", [image]);
};

exports.imageLabeler = function (image, success, error) {
  exec(success, error, "FirebaseVisionPlugin", "imageLabeler", [image]);
};
