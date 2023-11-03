const exec = require("cordova/exec");

exports.onDeviceTextRecognizer = function (image, success, error) {
  exec(success, error, "MLKit", "onDeviceTextRecognizer", [image]);
};

exports.barcodeDetector = function (image, success, error) {
  exec(success, error, "MLKit", "barcodeDetector", [image]);
};

exports.imageLabeler = function (image, success, error) {
  exec(success, error, "MLkit", "imageLabeler", [image]);
};
