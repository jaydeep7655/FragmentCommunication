package com.example.t186.fragmentcommunication.utility;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by T186 on 4/11/2018.
 */

public class UDF {
    public static boolean isOnline(Context context) {
        Boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            isConnected = cm != null && cm.getActiveNetworkInfo() != null
                    && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected();
            if (isConnected) {
                return isConnected;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    private static final String TAG = UDF.class.getSimpleName();

    private static final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    static MediaPlayer player = null;

    /* Check for valis email pattern
    *
            * @param str a Strig value to be matchedfor Email ID
    * @return a Boolean (TRUE or FALSE)
    */
    public static boolean emailMatches(String str) {
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (str.matches(emailPattern)) {
            return true;
        }
        return false;
    }

    public static void EnableGPS(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(intent);
    }

    public static void createProgressDialog(Context context, ProgressDialog progressDialog, String msg) {
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    /**
     * Show prompt or alert dialog with ok and cancle button
     */
    public static void showAlertDialog(
            String warning,
            android.content.DialogInterface.OnClickListener positiveClickListener,
            Context context) {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
        alertdialog.setMessage(warning);

        alertdialog.setPositiveButton("ok", positiveClickListener);
        //alertdialog.setNegativeButton("Cancel", negativeClickListener);
        alertdialog.show();

    }


    public static InputStream parseXML(String url) {
        final OkHttpClient client = new OkHttpClient();
        final MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/plain; charset=utf-8");

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().byteStream();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

   /* public static InputStream getResponseStream(String url, final Context context)
            throws ClientProtocolException, IOException {
        String strUrl = url;
        DefaultHttpClient myClient = new DefaultHttpClient();
        HttpConnectionParams.setSoTimeout(myClient.getParams(), 30000);
        HttpConnectionParams.setConnectionTimeout(myClient.getParams(), 30000);
        HttpPost postMethod = new HttpPost(strUrl);
        HttpResponse response = myClient.execute(postMethod);
        HttpEntity entity = response.getEntity();
        return entity.getContent();
    }*/


    /*
     * public static boolean isOnline(Context context) { ConnectivityManager
     * manager = (ConnectivityManager)
     * context.getSystemService(Context.CONNECTIVITY_SERVICE); if
     * (manager.getActiveNetworkInfo() != null &&
     * manager.getActiveNetworkInfo().isAvailable() &&
     * manager.getActiveNetworkInfo().isConnected()) { return true; } else {
     * return false; } }
     */
    @SuppressLint("NewApi")
    public static ValueAnimator slideAnimator(int start, int end, final View v) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public static byte[] getBitmapFromURL(final String src) {
        byte[] bArray = null;
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            bArray = converter(myBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bArray;
    }

    public static byte[] converter(Bitmap icon) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(pxToDp(41), pxToDp(50));
        view.layout(0, 0, pxToDp(41), pxToDp(50));
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static Bitmap createDrawableFromView1(Context context, View view) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
            view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            view.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("NewApi")
    public static Bitmap getBitmapFromURLMyTour(String imageUrl) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * To Collapse any view with smooth animation
     */
    @SuppressLint("NewApi")
    public static void collapse1(final View v) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            final int initialHeight = v.getMeasuredHeight();
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        v.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return false;
                }
            };
            a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        } else {
            int finalHeight = v.getHeight();
            ValueAnimator mAnimator = slideAnimator(finalHeight, 0, v);
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    v.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            mAnimator.start();
        }
    }

    /**
     * To expand any view with smooth animation
     */
    @SuppressLint("NewApi")
    public static void expand1(final View v, final int height) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            v.measure(ViewGroup.LayoutParams.MATCH_PARENT, height);
            final int targtetHeight = height;
            v.getLayoutParams().height = 0;
            v.setVisibility(View.VISIBLE);
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    v.getLayoutParams().height = interpolatedTime == 1 ? height
                            : (int) (targtetHeight * interpolatedTime);
                    v.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return false;
                }
            };
            a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
            v.startAnimation(a);
        } else {
            v.setVisibility(View.VISIBLE);
            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            v.measure(widthSpec, heightSpec);
            ValueAnimator mAnimator = slideAnimator(0, v.getMeasuredHeight(), v);
            mAnimator.start();
        }
    }

    /**
     * collapse layout
     *
     * @param v view
     */

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    /**
     * this method use to expand layout
     *
     * @param v
     */
    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void drawerOpenKeyboardSetting(Context cntxt) {
        InputMethodManager inputManager = (InputMethodManager) cntxt.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) cntxt).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Date convertToDate(String date) {
        String dateString = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static Date convertToDate1(String date) {
        String dateString = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static long dateDifference(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;
        return elapsedDays;
    }

    public static void ringtone(@NonNull Context activity) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(activity.getApplicationContext(), notification);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static JSONObject getDataFromInternalStorage(Activity activity, String fileName) {
        JSONObject jsonObject = new JSONObject();
        ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
        File directory = UDF.getChildrenFolder("hoho/route", activity);
        File file = new File(directory, fileName);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (text.length() > 0) {
            try {
                jsonObject = new JSONObject(text.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static String getScreenDensity(Activity activity) {
        int screenSize = activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        String toastMsg;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "XHDPI";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "HDPI";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "MDPI";
                break;
            default:
                toastMsg = "MDPI";
        }
        return toastMsg;
    }

    public static String getDeviceResolution(Activity activity) {
        int density = activity.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return "MDPI";
            case DisplayMetrics.DENSITY_HIGH:
                return "HDPI";
            case DisplayMetrics.DENSITY_LOW:
                return "LDPI";
            case DisplayMetrics.DENSITY_XHIGH:
                return "XHDPI";
            case DisplayMetrics.DENSITY_TV:
                return "TV";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "XXHDPI";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "XXXHDPI";
            default:
                return "Unknown";
        }
    }

    public static String saveToInternalSoragePNG(String src, Activity activity, String id) {
        Bitmap bitmapImage = UDF.getUrlToBitmap(src);
        if (bitmapImage != null) {
            ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
            File directory = cw.getDir("imgData", Context.MODE_PRIVATE);
            File mypath = new File(directory, "pin" + id + ".png");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return directory.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static Bitmap loadImageFromStoragePNG(String id, @NonNull Context context) {
        Bitmap b = null;
        try {
            ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
            File directory = cw.getDir("imgData", Context.MODE_PRIVATE);
            File f = new File(directory, "pin" + id + ".png");

            if (!f.exists()) {
                f = new File("/data/data/" + context.getPackageName() + "/app_imgData", "pin" + id + ".png");
            }

            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static String saveKeyToInternalSoragePNG(String src, Context activity, String id) {
        Bitmap bitmapImage = UDF.getUrlToBitmap(src);
        if (bitmapImage != null) {
            ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
            File directory = cw.getDir("imgData", Context.MODE_PRIVATE);
            File mypath = new File(directory, "key" + id + ".png");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return directory.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static Bitmap loadKeyImageFromStoragePNG(String id, @NonNull Context context) {
        Bitmap b = null;
        try {
            ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
            File directory = cw.getDir("imgData", Context.MODE_PRIVATE);
            File f = new File(directory, "key" + id + ".png");
            if (!f.exists()) {
                f = new File("/data/data/" + context.getPackageName() + "/app_imgData", "key" + id + ".png");
            }

            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static Bitmap getUrlToBitmap(final String src) {
        Bitmap myBitmap = null;
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myBitmap;
    }

    public static String saveToInternalSorage(String src, Context activity, String id) {
        Bitmap bitmapImage = UDF.getUrlToBitmap(src);
        if (bitmapImage != null) {
            ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
            File directory = cw.getDir("imgData", Context.MODE_PRIVATE);
            File mypath = new File(directory, id + ".jpg");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return directory.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static String saveToInternalSorageSquare(String src, Context activity, String id) {
        return saveToInternalSorage(src, activity, "square_" + id);
    }


    public static String saveToInternalSorageBitmap(Bitmap bitmapImage, Context activity, String id) {
        if (bitmapImage != null) {
            ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
            File directory = cw.getDir("imgData", Context.MODE_PRIVATE);
            File mypath = new File(directory, id + ".jpg");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return directory.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static Bitmap loadImageFromStorage(String id, @NonNull Context context) {
        Bitmap b = null;
        try {
            ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
            File directory = cw.getDir("imgData", Context.MODE_PRIVATE);
            File f = new File(directory, id + ".jpg");

            if (!f.exists()) {
                f = new File("/data/data/" + context.getPackageName() + "/app_imgData", id + ".jpg");
            }

            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static File getChildrenFolder(@NonNull String path, @NonNull Context context) {
        File dir = context.getFilesDir();
        List<String> dirs = new ArrayList<String>(Arrays.<String>asList(path.split("/")));
        for (int i = 0; i < dirs.size(); ++i) {
            dir = new File(dir, dirs.get(i)); // Getting a file within the dir.
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        return dir;
    }

    public static File getChildrenFolderDownloadAsync(String path, Context context) {
        File dir = context.getFilesDir();
        List<String> dirs = new ArrayList<String>(Arrays.<String>asList(path.split("/")));
        for (int i = 0; i < dirs.size(); ++i) {
            dir = new File(dir, dirs.get(i)); // Getting a file within the dir.
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        return dir;
    }

    public static File getDownloadFolder(String path, Context context) {
        File dir = context.getFilesDir();
        List<String> dirs = new ArrayList<String>(Arrays.<String>asList(path.split("/")));
        for (int i = 0; i < dirs.size(); ++i) {
            dir = new File(dir, dirs.get(i)); // Getting a file within the dir.
        }
        return dir;
    }

    public static File getChildrenFolderInentService(String path, Service context) {
        File dir = context.getFilesDir();
        List<String> dirs = new ArrayList<String>(Arrays.<String>asList(path.split("/")));
        for (int i = 0; i < dirs.size(); ++i) {
            dir = new File(dir, dirs.get(i)); // Getting a file within the dir.
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        return dir;
    }

    public static void saveDebugLogs() {
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder log = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
            }
            // Log.d("LogLengh:", "::" + log.length());
            File directory = new File("/mnt/sdcard/DEBUG");
            if (!directory.exists())
                directory.mkdir();
            FileOutputStream fOut = new FileOutputStream(directory.getAbsolutePath() + "/" + "Debug_Logs.txt");
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(log);
            myOutWriter.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(StringBuilder data, final Activity activity) {
        try {
            File directory = new File("/mnt/sdcard/DEBUG");
            if (!directory.exists())
                directory.mkdir();
            FileOutputStream fOut = new FileOutputStream(directory.getAbsolutePath() + "/" + "Debug_Logs.txt");
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public static Drawable resize(Drawable image, Activity activity) {
        try {
            Bitmap b = ((BitmapDrawable) image).getBitmap();
            Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 38, 40, false);
            return new BitmapDrawable(activity.getResources(), bitmapResized);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showSnakBar(View view, String text) {
        final Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android

                .support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(12);
        snackbar.show();
    }

    public static boolean isPlaying() {
        if (player != null)
            return player.isPlaying();
        else
            return false;
    }

    public static boolean isMyServiceRunning(Activity activity, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public static String getVersion(Context context) {
        String version = null;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    public static String formatDateTimeStringForNotification(String dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date date = df.parse(dateTime);
            return new SimpleDateFormat("d MMM yyyy HH:mm a", Locale.ENGLISH).format(date.getTime());
        } catch (ParseException e) {
            return "";
        }
    }

    public static String getCurrentDateFullLength() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss", Locale.ENGLISH);
        return df.format(c.getTime());
    }

    public static String getCurrentDateDDxMMMMYYYY() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat(" MMMM, yyyy", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("DD", Locale.ENGLISH);

        int day = Integer.parseInt(dayFormat.format(c.getTime()));
        String dateWithSuffix = String.format("%d%s", day, getDaySuffix(day)) + df.format(c.getTime());
        return dateWithSuffix;
    }

    public static String getDateDDxMMMMYYYY(@NonNull Date date) {

        SimpleDateFormat df = new SimpleDateFormat(" MMM, yyyy", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String dateWithSuffix = String.format("%d%s", calendar.get(Calendar.DAY_OF_MONTH), getDaySuffix(calendar.get(Calendar.DAY_OF_MONTH))) + df.format(date);
        return dateWithSuffix;
    }

    public static String getDaySuffix(final int n) {
        if (n < 1 || n > 31)
            return "Invalid date";
        if (n >= 11 && n <= 13)
            return "th";

        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }


    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static String validateDouble(String doubleString) {
        double doubleValue = 0;
        try {
            doubleValue = Double.parseDouble(doubleString);
        } catch (Exception e) {
            Log.e(TAG, "validateDouble: " + doubleString + "false");
        }
        return String.valueOf(doubleValue);
    }

    @NonNull
    public static String formatDate(@NonNull String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateString = "";
        if (!TextUtils.isEmpty(dateString)) {
            try {
                Date date = sdf.parse(dateString.substring(0, 10));
                return formattedDateString + UDF.getDateDDxMMMMYYYY(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return "Unknown Date";
    }

    @NonNull
    public static String formatTime(@NonNull String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

        if (!TextUtils.isEmpty(dateString)) {
            try {
                Date date = sdf.parse(dateString);
                return timeFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return "Unknown Time";
    }

//    @NonNull
//    public static Date convertToLondonTimezone() {
//
//    }
//
//    @NonNull
//    public static Date convertToLocalTimezone() {
//
//    }

    /**
     * Method to get current activity
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Activity getRunningActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread")
                    .invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Didn't find the running activity");
    }

}
