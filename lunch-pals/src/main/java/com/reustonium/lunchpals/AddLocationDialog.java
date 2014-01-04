package com.reustonium.lunchpals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Andrew on 1/1/14.
 */
public class AddLocationDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add New Location");
        final EditText editText = new EditText(getActivity());
        builder.setView(editText);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //TODO check if name is unique
                ParseQuery<ParseObject> nameQuery = ParseQuery.getQuery("Location");
                nameQuery.whereEqualTo("name", editText.getText().toString());
                nameQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        if (parseObjects.size() > 0 || editText.getText().toString().length() < 1) {
                            return;
                        } else {
                            ParseObject location = new ParseObject("Location");

                            location.put("name", editText.getText().toString());
                            location.put("createdBy", ParseUser.getCurrentUser());
                            location.saveInBackground();
                        }
                    }
                });

                return;
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        return builder.create();
    }
}
