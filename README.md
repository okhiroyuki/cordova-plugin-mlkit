# cordova-plugin-mlkit

Cordova plugin for MLKit

## Installation

Run:

```shell
cordova plugin add @red-mobile@cordova-plugin-mlkit
```

## Usage

## Text recognition

```js
//FILE_URI: File URI or Base64 Format
FirebaseVisionPlugin.onDeviceTextRecognizer(FILE_URI,
    (text) => {
        console.log(text);
    },
    (error) => {
        console.error(error);;
    })
})
```

## Barcode detector

```js
//FILE_URI: File URI or Base64 Format
FirebaseVisionPlugin.barcodeDetector(FILE_URI,
    (json) => {
        console.log(json);
    },
    (error) => {
        console.error(error);;
    })
})
```

## Image Labeler

```js
//FILE_URI: File URI or Base64 Format
FirebaseVisionPlugin.imageLabeler(FILE_URI,
    (json) => {
        console.log(json);
    },
    (error) => {
        console.error(error);;
    })
})
```

## Support

|                             | Android | iOS |
| --------------------------- | ------- | --- |
| Text recognition            | X       |     |
| Face detection              |         |     |
| Barcode scanning            | X       |     |
| Image labeling              | X       |     |
| Object detection & tracking |         |     |
| Landmark recognition        |         |     |
