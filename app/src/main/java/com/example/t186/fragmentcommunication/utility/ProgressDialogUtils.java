package com.example.t186.fragmentcommunication.utility;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by T186 on 4/11/2018.
 */

public class ProgressDialogUtils {
    public static void showProgressDialog(Activity activity, ProgressDialog progressDialog) {
        if (!activity.isFinishing() && progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
    public static void dismissProgressDialog(Activity activity, ProgressDialog progressDialog) {
        if (!activity.isFinishing() && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        } }
}
