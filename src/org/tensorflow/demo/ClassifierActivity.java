/*
 * Copyright 2016 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Trace;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.tensorflow.demo.OverlayView.DrawCallback;
import org.tensorflow.demo.env.BorderedText;
import org.tensorflow.demo.env.ImageUtils;
import org.tensorflow.demo.env.Logger;
import org.tensorflow.demo.R;

//import com.DefaultCompany.unitycam.UnityPlayerActivity;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;

import io.fabric.sdk.android.Fabric;

public class ClassifierActivity extends CameraActivity implements OnImageAvailableListener {
  private static final Logger LOGGER = new Logger();

  // These are the settings for the original v1 Inception model. If you want to
  // use a model that's been produced from the TensorFlow for Poets codelab,
  // you'll need to set IMAGE_SIZE = 299, IMAGE_MEAN = 128, IMAGE_STD = 128,
  // INPUT_NAME = "Mul", and OUTPUT_NAME = "final_result".
  // You'll also need to update the MODEL_FILE and LABEL_FILE paths to point to
  // the ones you produced.
  //
  // To use v3 Inception model, strip the DecodeJpeg Op from your retrained
  // model first:
  //
  // python strip_unused.py \
  // --input_graph=<retrained-pb-file> \
  // --output_graph=<your-stripped-pb-file> \
  // --input_node_names="Mul" \
  // --output_node_names="final_result" \
  // --input_binary=true

  /* Inception V3
  private static final int INPUT_SIZE = 299;
  private static final int IMAGE_MEAN = 128;
  private static final float IMAGE_STD = 128.0f;
  private static final String INPUT_NAME = "Mul:0";
  private static final String OUTPUT_NAME = "final_result";
  */
//---------------------------

    //--------------------------------
  private static final int INPUT_SIZE = 224;
  private static final int IMAGE_MEAN = 128;
  private static final float IMAGE_STD = 128.0f;
  private static final String INPUT_NAME = "input";
 // private static final String OUTPUT_NAME = "MobilenetV1/Predictions/Softmax";
 private static final String OUTPUT_NAME = "final_result";
  private static final String MODEL_FILE = "file:///android_asset/graph.pb";
  private static final String LABEL_FILE = "file:///android_asset/labels.txt";

  private static final boolean SAVE_PREVIEW_BITMAP = false;

  private static final boolean MAINTAIN_ASPECT = true;
// default DESIRED_PREVIEW_SIZE was width 640 and height 480
    // full screen but magnified was size width 1280 and height 680
    // full screen and every thing work fine with width 1000 and height 715
  private static final Size DESIRED_PREVIEW_SIZE = new Size(1280, 720);

  private Classifier classifier;

  private Integer sensorOrientation;

  private int previewWidth = 0;
  private int previewHeight = 0;
  private byte[][] yuvBytes;
  private int[] rgbBytes = null;
  private Bitmap rgbFrameBitmap = null;
  private Bitmap croppedBitmap = null;

  private Bitmap cropCopyBitmap;

  private boolean computing = false;

  private Matrix frameToCropTransform;
  private Matrix cropToFrameTransform;
//1/3 comments
 //  private ResultsView resultsView;

  private BorderedText borderedText;

  private long lastProcessingTimeMs;

  @Override
  protected int getLayoutId() {
    return R.layout.camera_connection_fragment;
  }

  @Override
  protected Size getDesiredPreviewFrameSize() {
    return DESIRED_PREVIEW_SIZE;
  }

  private static final float TEXT_SIZE_DIP = 10;

  @Override
  public void onPreviewSizeChosen(final Size size, final int rotation) {
      Fabric.with(this, new Crashlytics());
    final float textSizePx =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
    borderedText = new BorderedText(textSizePx);
    borderedText.setTypeface(Typeface.MONOSPACE);

    classifier =
        TensorFlowImageClassifier.create(
            getAssets(),
            MODEL_FILE,
            LABEL_FILE,
            INPUT_SIZE,
            IMAGE_MEAN,
            IMAGE_STD,
            INPUT_NAME,
            OUTPUT_NAME);
    //2/3 comment
  //  resultsView = (ResultsView) findViewById(R.id.results);
    previewWidth = size.getWidth();
    previewHeight = size.getHeight();

    final Display display = getWindowManager().getDefaultDisplay();
    final int screenOrientation = display.getRotation();

    LOGGER.i("Sensor orientation: %d, Screen orientation: %d", rotation, screenOrientation);

    sensorOrientation = rotation + screenOrientation;

    LOGGER.i("Initializing at size %dx%d", previewWidth, previewHeight);
    rgbBytes = new int[previewWidth * previewHeight];
    rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Config.ARGB_8888);
    croppedBitmap = Bitmap.createBitmap(INPUT_SIZE, INPUT_SIZE, Config.ARGB_8888);

    frameToCropTransform =
        ImageUtils.getTransformationMatrix(
            previewWidth, previewHeight,
            INPUT_SIZE, INPUT_SIZE,
            sensorOrientation, MAINTAIN_ASPECT);

    cropToFrameTransform = new Matrix();
    frameToCropTransform.invert(cropToFrameTransform);

    yuvBytes = new byte[3][];

    addCallback(
        new DrawCallback() {
          @Override
          public void drawCallback(final Canvas canvas) {
            renderDebug(canvas);
          }
        });
  }

  @Override
  public void onImageAvailable(final ImageReader reader) {
    Image image = null;

    try {
      image = reader.acquireLatestImage();

      if (image == null) {
        return;
      }

      if (computing) {
        image.close();
        return;
      }
      computing = true;

      Trace.beginSection("imageAvailable");

      final Plane[] planes = image.getPlanes();
      fillBytes(planes, yuvBytes);

      final int yRowStride = planes[0].getRowStride();
      final int uvRowStride = planes[1].getRowStride();
      final int uvPixelStride = planes[1].getPixelStride();
      ImageUtils.convertYUV420ToARGB8888(
          yuvBytes[0],
          yuvBytes[1],
          yuvBytes[2],
          previewWidth,
          previewHeight,
          yRowStride,
          uvRowStride,
          uvPixelStride,
          rgbBytes);

      image.close();
    } catch (final Exception e) {
      if (image != null) {
        image.close();
      }
      LOGGER.e(e, "Exception!");
      Trace.endSection();
      return;
    }

    rgbFrameBitmap.setPixels(rgbBytes, 0, previewWidth, 0, 0, previewWidth, previewHeight);
    final Canvas canvas = new Canvas(croppedBitmap);
    canvas.drawBitmap(rgbFrameBitmap, frameToCropTransform, null);

    // For examining the actual TF input.
    if (SAVE_PREVIEW_BITMAP) {
      ImageUtils.saveBitmap(croppedBitmap);
    }

    runInBackground(
        new Runnable() {
          @Override
          public void run() {
            final long startTime = SystemClock.uptimeMillis();
            final List<Classifier.Recognition> results = classifier.recognizeImage(croppedBitmap);
            lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;

            cropCopyBitmap = Bitmap.createBitmap(croppedBitmap);
            //3/3 comments
         //resultsView.setResults(results);
          //this my code
              //------------------------------------------------------------------------------------------------------------

              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      boolean seen = false;
                      Integer seenInt = 0;
                      RelativeLayout cameraConnectionRelativeLayout = (RelativeLayout) findViewById(R.id.cameraConnectionRelativeLayout);
                      ImageView upperImageView;
                      upperImageView = (ImageView) findViewById(R.id.upperImageView) ;
                      Iterator resultsListIterator = results.iterator();
                     // Button unityTravelButton = (Button) findViewById(R.id.unityTravelButton);
                      ImageView infoImageView = (ImageView)findViewById(R.id.infoImageButton);
                      ImageView aRImageView = (ImageView)findViewById(R.id.aRImageButton);
                    //  ImageView arrowImageView = (ImageView) findViewById(R.id.arrowImageView);
                      if(resultsListIterator.hasNext())
                      {

                          Classifier.Recognition firstItemInTheResultList = (Classifier.Recognition) resultsListIterator.next();
                          final String firstItemName = firstItemInTheResultList.getTitle();
                          double firstItemAcc = firstItemInTheResultList.getConfidence();
                          if((!firstItemName.equals("chairs") && !firstItemName.equals("floors")&& !firstItemName.equals("humans")&&
                          !firstItemName.equals("tables")&& !firstItemName.equals("walls") && !firstItemName.equals("cups")&& !firstItemName.equals("mouses"))
                                  && firstItemAcc >= 0.9)
                              {
                                  switch (firstItemName)
                                  {
                                      case "tutankhamun":
                                          upperImageView.setImageResource(R.drawable.tutankamoun);
                                          break;
                                      case "nefertiti":
                                          upperImageView.setImageResource(R.drawable.nefdark);
                                          break;
                                      case "secondnefertiti":
                                          upperImageView.setImageResource(R.drawable.nefdark);
                                          break;
                                      case "sphinx":
                                          upperImageView.setImageResource(R.drawable.sphinixdark);
                                          break;
                                      case "ramsis":
                                          upperImageView.setImageResource(R.drawable.ramsisd);
                                          break;
                                      case "ikhnaton":
                                          upperImageView.setImageResource(R.drawable.ikhnatondark);

                                          break;
                                      case "hatshepsut":
                                          upperImageView.setImageResource(R.drawable.hatshepsutdark);
                                          break;
                                      default:
                                          break;
                                  }

                              upperImageView.setVisibility(View.VISIBLE);
                              //unityTravelButton.setText("Swipe to Travel Back in Time");
                             // unityTravelButton.setVisibility(View.VISIBLE);
                                  infoImageView.setVisibility(View.VISIBLE);
                                  aRImageView.setVisibility(View.VISIBLE);
                                  infoImageView.setImageResource(R.drawable.icon);
                                  aRImageView.setImageResource(R.drawable.ar);
                             //* arrowImageView.setImageResource(R.drawable.gooldold);
                           //   arrowImageView.setVisibility(View.VISIBLE);
                              cameraConnectionRelativeLayout.setOnTouchListener(new OnSwipeTouchListener(ClassifierActivity.this)
                              {
                                  public void onSwipeRight() {

                                    //  Intent i = new Intent(ClassifierActivity.this, UnityPlayerActivity.class);

                                      Intent infoIntent = new Intent(ClassifierActivity.this, Info.class);
                                      infoIntent.putExtra("Monument_name", firstItemName);

                                      startActivity(infoIntent);

                                  }
                                  public void onSwipeLeft()
                                  {
                                      startActivity(new Intent("com.yahya.PharohsTaking.nef"));
                                  }
                              });
                              infoImageView.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      Intent infoIntent = new Intent(ClassifierActivity.this, Info.class);
                                      infoIntent.putExtra("Monument_name", firstItemName);

                                      startActivity(infoIntent);
                                  }
                              });
                                  aRImageView.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          startActivity(new Intent("com.yahya.PharohsTaking.nef"));
                                      }
                                  });


                             /* unityTravelButton.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      Intent infoIntent = new Intent(ClassifierActivity.this, Info.class);
                                      infoIntent.putExtra("Monument_name", firstItemName);

                                      startActivity(infoIntent);
                                  }
                              });*/

                             if(seenInt != 1) {

                                  TranslateAnimation animation = new TranslateAnimation(0.0f, 500.0f,
                                          300.0f, 300.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                                  animation.setDuration(500);  // animation duration
                                  animation.setRepeatCount(5);// animation repeat count

                                  //  animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
                                  //animation.setFillAfter(true);
                                //  arrowImageView.startAnimation(animation);

                              }

                              seenInt = 1;

                          }
                          else
                          {
                              //unityTravelButton.setVisibility(View.GONE);
                              upperImageView.setVisibility(View.GONE);
                              //arrowImageView.setVisibility(View.GONE);
                           //   arrowImageView.clearAnimation();
                              infoImageView.setVisibility(View.GONE);
                              aRImageView.setVisibility(View.GONE);
                              seen = false;
                              seenInt=-1;

                          }
                      }
                      else
                      {
                         // unityTravelButton.setVisibility(View.GONE);
                          upperImageView.setVisibility(View.GONE);
                          //arrowImageView.setVisibility(View.GONE);
                          infoImageView.setVisibility(View.GONE);
                              aRImageView.setVisibility(View.GONE);
                          seen = false;
                          seenInt = -1;


                      }


                  }
              });


              //------------------------------------------------------------------------------------------------------------
            requestRender();
            computing = false;
          }
        });

    Trace.endSection();
  }

  @Override
  public void onSetDebug(boolean debug) {
    classifier.enableStatLogging(debug);
  }

  private void renderDebug(final Canvas canvas) {
    if (!isDebug()) {
      return;
    }
    final Bitmap copy = cropCopyBitmap;
    if (copy != null) {
      final Matrix matrix = new Matrix();
      final float scaleFactor = 2;
      matrix.postScale(scaleFactor, scaleFactor);
      matrix.postTranslate(
          canvas.getWidth() - copy.getWidth() * scaleFactor,
          canvas.getHeight() - copy.getHeight() * scaleFactor);
      canvas.drawBitmap(copy, matrix, new Paint());

      final Vector<String> lines = new Vector<String>();
      if (classifier != null) {
        String statString = classifier.getStatString();
        String[] statLines = statString.split("\n");
        for (String line : statLines) {
          lines.add(line);
        }
      }

      lines.add("Frame: " + previewWidth + "x" + previewHeight);
      lines.add("Crop: " + copy.getWidth() + "x" + copy.getHeight());
      lines.add("View: " + canvas.getWidth() + "x" + canvas.getHeight());
      lines.add("Rotation: " + sensorOrientation);
      lines.add("Inference time: " + lastProcessingTimeMs + "ms");

      borderedText.drawLines(canvas, 10, canvas.getHeight() - 10, lines);
    }
  }
  //my code starts here
   // ----------------------------------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed()
    {

        Intent backIntent = new Intent(ClassifierActivity.this, MainMenu.class);
        startActivity(backIntent);
    }

}

