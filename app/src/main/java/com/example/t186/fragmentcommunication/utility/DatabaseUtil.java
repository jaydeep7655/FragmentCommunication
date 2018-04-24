package com.example.t186.fragmentcommunication.utility;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Android Developer on 12/11/2017.
 */

public class DatabaseUtil {
    public static void copyAssets(Activity activity) {
        AssetManager assetManager = activity.getAssets();
        String[] files;
        try {
            files = assetManager.list("route");
            InputStream in = null;
            OutputStream out = null;
            for (int i = 0; i < files.length; i++) {
                String filename = files[i];
                in = assetManager.open("route/" + filename);
                ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
                File directory = UDF.getChildrenFolder("hoho/route", activity);
                File mypath1 = new File(directory, filename);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                mypath1.createNewFile();
                out = new FileOutputStream(mypath1);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static void copyImages(Activity activity) {
        AssetManager assetManager = activity.getAssets();
        String[] files;
        try {
            files = assetManager.list("imageData");
            InputStream in = null;
            OutputStream out = null;
            for (int i = 0; i < files.length; i++) {
                String filename = files[i];

                in = assetManager.open("imageData/" + filename);
                ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
                File directory = cw.getDir("imgData", Context.MODE_PRIVATE);
                File mypath1 = new File(directory, filename);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                mypath1.createNewFile();
                out = new FileOutputStream(mypath1);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }



}
