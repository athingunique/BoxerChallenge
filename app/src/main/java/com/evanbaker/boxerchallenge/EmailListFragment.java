package com.evanbaker.boxerchallenge;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * A fragment representing a list of Emails.
 * Activities containing this fragment MUST implement the {@link EmailListFragmentCallback}
 * interface.
 */

public class EmailListFragment extends ListFragment {

    /**
     * The interface callback representing the parent Activity
     */
    private EmailListFragmentCallback mCallback;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmailListFragment() {}

    /*
     * Fragment lifecycle methods
     */

    /**
     * Sets up this ListFragment (binds the Adapter)
     * @param savedInstanceState a Bundle to restore from
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the Adapter that will provide data to the List (since the emails are stored in an
        // ArrayList, use the default ArrayAdapter)
        BaseAdapter adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.centered_list_item,
                mCallback.requestEmails());

        // Attach the Adapter
        setListAdapter(adapter);

        // Register the Adapter in the parent Activity so that it can be modified by that Activity
        if (mCallback != null) {
            mCallback.registerAdapter(adapter);
        }
    }


    /**
     * When this Fragment is attached to an Activity, check and make sure that Activity is qualified
     * to host this Fragment.
     * @param activity the parent Activity
     * @throws ClassCastException error indicating the parent Activity does not implement {@link EmailListFragmentCallback}
     */
    @Override
    public void onAttach(Activity activity) throws ClassCastException {
        super.onAttach(activity);
        mCallback = (EmailListFragmentCallback) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Null the callback to avoid leaking an Activity reference
        mCallback = null;
    }

    /*
     * End lifecycle methods
     */


    /*
     * Work methods
     */

    /**
     * This method handles onClick events on the list items
     * @param l the parent ListView
     * @param v the clicked View
     * @param position the position of the clicked View in the ListView
     * @param id the id of the clicked View
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (mCallback != null) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mCallback.onListItemSelected(position);
        }
    }

    /*
     * End work methods
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
    public interface EmailListFragmentCallback {
        /**
         * This method registers the Adapter in the parent Activity so that it can be referenced
         * there
         * @param adapter the Adapter to register
         */
        void registerAdapter(BaseAdapter adapter);

        /**
         * This method is called by the ListFragment when a list item is selected.
         * @param position the selected item position
         */
        void onListItemSelected(int position);

        /**
         * This method is called by this fragment needs the List of emails.
         * @return the List of emails.
         */
        List<String> requestEmails();
    }

}
