package com.coxtunes.firebaseremotconfig;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UpdateHelper.with(this)
                .onUpdateCheack(this)
                .check();
    }

    @Override
    public void onUpdateCheckListener(final String urlApp) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("UPDATE AVAILABLE");
        builder.setMessage("ARE YOU SURE WANT TO UPDATE APP?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlApp));
                startActivity(i);
            }

        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
}
