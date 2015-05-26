package com.evanbaker.boxerchallenge;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;


/**
 * A DialogFragment with an EditText that for inputting an email address.
 * Activities containing this fragment MUST implement {@link EmailEntryDialogFragmentCallback}
 */

public class EmailEntryDialogFragment extends DialogFragment {

    /**
     * The interface callback representing the parent Activity.
     */
    private EmailEntryDialogFragmentCallback mCallback;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmailEntryDialogFragment() {}


    /*
     * Fragment lifecycle methods
     */

    /**
     * Sets up this DialogFragment
     * @param savedInstanceState a savedInstanceState to restore from
     * @return the fully constructed DialogFragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the edit text here so that the text can be pulled out of it later
        final EditText emailEntryEditText = (EditText) getActivity().getLayoutInflater().inflate(R.layout.edittext_email_entry, null);

        // Build the Dialog through the handy AlertDialog#Builder class
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(emailEntryEditText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String emailEntry = emailEntryEditText.getText().toString();
                        if (!TextUtils.isEmpty(emailEntry) && android.util.Patterns.EMAIL_ADDRESS.matcher(emailEntry).matches()) {
                            mCallback.attemptEmailEntry(emailEntryEditText.getText().toString());
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        return dialogBuilder.create();
    }

    /**
     * When this Fragment is attached to an Activity, check and make sure that Activity is qualified
     * to host this Fragment.
     * @param activity the parent Activity
     * @throws ClassCastException error indicating the parent Activity does not implement {@link EmailEntryDialogFragmentCallback}
     */
    @Override
    public void onAttach(Activity activity) throws ClassCastException {
        super.onAttach(activity);
        mCallback = (EmailEntryDialogFragmentCallback) activity;
    }

    @Override
    public void onDetach() {
        // Null the callback to prevent leaking an Activity reference
        mCallback = null;
        super.onDetach();
    }

    /*
     * End lifecycle methods
     */


    /*
     * Interface definitions
     */

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface EmailEntryDialogFragmentCallback {
        /**
         * Method that passes a String to the parent Activity, which attempts to add it to the
         * email List
         * @param entry the email to pass to the parent
         */
        void attemptEmailEntry(String entry);
    }
}
