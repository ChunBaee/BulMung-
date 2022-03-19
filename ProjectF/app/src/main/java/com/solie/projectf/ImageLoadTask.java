package com.solie.projectf;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ImageLoadTask extends AsyncTask< Void, Void, Bitmap> {
    private String urlStr;
    private ImageView imageView;

    private static HashMap<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();

    public ImageLoadTask(String url, ImageView imageView) {
        this.urlStr = url;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = null;
        URL url = null;
        try {
            url = new URL(urlStr);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;


    }
}
