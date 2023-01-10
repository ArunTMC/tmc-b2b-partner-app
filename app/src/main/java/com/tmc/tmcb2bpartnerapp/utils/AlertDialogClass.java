package com.tmc.tmcb2bpartnerapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tmc.tmcb2bpartnerapp.R;


public class AlertDialogClass {





/*
    public static void showDialog(Activity activity, String msg, int zero){
        activity. runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final Dialog dialog = new Dialog(activity);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.alert_dialog);

                TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }
*/


    public static void showDialog(Activity activity, int msg){
        activity.runOnUiThread(() -> {

            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog);

            TextView text =  dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogButton =  dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        });
    }







/*
    public static void showAlert(final Context activity, String message)
    {


        AlertDialog.Builder alertDialog=new AlertDialog.Builder(activity);


        alertDialog.setTitle(R.string.app_name);
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("Yes", (dialog, which) -> {

        });
        alertDialog.setNegativeButton("CANCEL", (dialog, which) -> {


        });


        alertDialog.show();


    }


 */
}
