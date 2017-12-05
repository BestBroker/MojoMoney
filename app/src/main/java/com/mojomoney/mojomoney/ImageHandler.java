package com.mojomoney.mojomoney;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageHandler {

    public static File getPicFile (Context context, String timeStamp) {
        File basedir = context.getFilesDir();
        File picDir = new File(basedir, "tempFiles");
        if(!picDir.exists()) {
            picDir.mkdir();
        }
        File imageFile = null;
        try {
            imageFile = File.createTempFile("JPEG_" + timeStamp, ".jpg", picDir);
        } catch (Exception e) {
            CharSequence text = "Fehler (getPicFile)";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        return imageFile;
    }

    public static Bitmap setPic(File photoFile) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		String mCurrentPhotoPath = photoFile.getAbsolutePath();
        int targetW = 250;
        int targetH = 300;

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap bitmapRotated = rotateImage(bitmap, 90);

        return bitmapRotated;
    }

    public static Bitmap rotateImage(Bitmap src, float degree) {
        try {
            // create new matrix
            Matrix matrix = new Matrix();
            // setup rotation degree
            matrix.postRotate(degree);
            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        } catch (Exception e) {
            return null;
        }
    }

    public static String saveToInternalStorage(Context context, File source, String timeStamp){

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory,timeStamp + ".png");

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist

            in = new FileInputStream(source);
            out = new FileOutputStream(mypath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;
        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        String pathString = mypath.getAbsolutePath();
        return pathString;

    }

    public static Bitmap loadImageFromStorage(String path, int px) {

        try {

           int targetW = (int) px;
           int targetH = (int) px;

		/* Get the size of the image */
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
            int scaleFactor = 1;
            if ((targetW > 0) || (targetH > 0)) {
                scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            }

		/* Set bitmap options to scale the image decode target */
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

		/* Decode the JPEG file into a Bitmap */
            Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
            Bitmap bitmapRotated = rotateImage(bitmap, 90);

            return bitmapRotated;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}



