package com.tsato.util

import maxPhotoSizeInKb
import org.khronos.webgl.Uint8Array
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
import web.buffer.atob
import web.html.Image

class ValidationException(message: String): Exception(message)

fun validatePhoto(img: Image) {
//    if (naturalWidth != naturalHeight) {
//        throw ValidationException("The photo must be square. The width and height of your photo are not equal.")
//    }
//    if (maxNaturalSize < img.naturalWidth || maxNaturalSize < img.naturalHeight) {
//        throw ValidationException("The photo size has to be 1024 x 1024 px or smaller.")
//    }
    if (maxPhotoSizeInKb < getImageSizeInKb(img.src)) {
        throw ValidationException("The photo has to be smaller than ${maxPhotoSizeInKb / 1000}MB")
    }
}

fun getImageSizeInKb(imageUrl: String): Double {
    val blob = dataURItoBlob(imageUrl)
    return blob.size.toDouble() / 1024.0
}

fun dataURItoBlob(dataURI: String): Blob {
    val byteString = atob(dataURI.split(",")[1])
    val contentType = dataURI.split(",")[0].split(":")[1].split(";")[0]

    val ia = Uint8Array(byteString.length)
    for (i in byteString.indices) {
        val code = byteString[i].code.toShort()
        ia.set(Uint8Array(arrayOf(code.toByte())), i)
    }

    val blobPropertyBag = BlobPropertyBag(type = contentType)
    return Blob(arrayOf(ia.buffer), blobPropertyBag)
}
