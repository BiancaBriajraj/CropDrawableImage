package com.example.cropdrawable

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val bitmap = ContextCompat.getDrawable(this, R.drawable.laugh)?.toBitmap()

            bitmap?.apply {
                originalImageView.setImageBitmap(this)
                dimensionsOrginalImageTextView.text = " Width : $width , Height:$height "


                floatingActionButton.setOnClickListener {
                    if (newWidthEditTextView.text.isEmpty() || newHeightEditText.text.isEmpty()) {
                        dimensionsCroppedImageTextView.text = "Dimensions error"
                    } else {
                        val newWidth = newWidthEditTextView.text.toString().toInt()
                        val newHeight = newHeightEditText.text.toString().toInt()
                        if (newHeight > height || newWidth > width) {
                            dimensionsCroppedImageTextView.text = "Dimensions exceed original image"
                        } else {
                            bitmap.apply {
                                cropRectangle(
                                    newWidth,
                                    newHeight
                                )?.let {
                                    croppedImageView.setImageBitmap(it)
                                    dimensionsCroppedImageTextView.text = "Width: $newWidth , height $newHeight"
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun Bitmap.cropRectangle(
        newWidth:Int = this.width,
        newHeight:Int = this.height
    ):Bitmap?{
        return try {
            Bitmap.createBitmap(
                this, // source bitmap
                0, // x coordinate of the first pixel in source
                0, // y coordinate of the first pixel in source
                newWidth, // width in pixels
                newHeight // height in pixels
            )
        }catch (e:IllegalArgumentException){
            null
        }
    }
