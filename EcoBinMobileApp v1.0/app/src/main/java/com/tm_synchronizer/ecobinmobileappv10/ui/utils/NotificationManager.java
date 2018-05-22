package com.tm_synchronizer.ecobinmobileappv10.ui.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by yeqim_000 on 11/08/16.
 */
public class NotificationManager {

    public static final String MSG_ERROR = "ERROR!";

    private static ProgressDialog progress;

    public static void DisplayAlertDialog(Context context, String title, String msg)
    {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void ShowProgressDialog(Context context, String title, String msg)
    {
        progress = ProgressDialog.show(context, title, msg, true);
    }

    public static void HideProgressDialog()
    {
        progress.dismiss();
    }

}
