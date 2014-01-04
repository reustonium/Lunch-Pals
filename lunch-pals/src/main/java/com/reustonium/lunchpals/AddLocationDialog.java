package com.reustonium.lunchpals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by Andrew on 1/1/14.
 */
public class AddLocationDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(LocationsFragment);
        builder.setTitle("Add New Location");

        return builder.create();
    }
}
