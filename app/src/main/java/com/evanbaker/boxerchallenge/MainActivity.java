package com.evanbaker.boxerchallenge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * The application MainActivity.
 * Serves as a psuedo controller class since it manages the data interactions of all of the child
 * Fragments.
 * Contains a ListFragment of alphabetically sorted emails, with methods to add emails to the List
 * (which stays sorted), and fire an intent to send an email to one of the list entries.
 * New entries will persist through Activity recreate, but not between Application instances.
 * The list is prepopulated with 15 static email addresses from a string-array.
 */

public class MainActivity extends AppCompatActivity implements EmailListFragment.EmailListFragmentCallback, EmailEntryDialogFragment.EmailEntryDialogFragmentCallback {

    /**
     * SavedInstanceState Key for the emails String ArrayList {@link #mEmailsList}
     */
    private static final String BUNDLE_EMAILS_KEY = "com.evanbaker.boxerchallenge.main_activity_bundle.emails";

    /**
     * The emails ArrayList. The specific type is used here so that it's not necessary to cast at
     * every usage of the {@link AlphabeticalArrayList#sortedAdd(String...)} method
     */
    AlphabeticalArrayList mEmailsList;

    /**
     * A reference to the {@link EmailListFragment}'s Adapter, allowing modifications to the Adapter
     * of the currently attached {@link EmailListFragment} without having to lookup the Fragment
     */
    BaseAdapter mEmailListAdapter;


    /*
     * Lifecycle methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the emails Collection
        // Since alphabetical String sorting is achieved through fundamental comparisons
        // ("a" < "b"), placing and sorting entries in a simple ArrayList is straightforward
        mEmailsList = new AlphabeticalArrayList();

        if (savedInstanceState == null) {
            addRandomEmails(); // Prepopulate *iff* we're not restoring an existing List
        } else {
            restoreListState(savedInstanceState);
        }

        setContentView(R.layout.activity_main);
    }


    /**
     * This is a custom restoreInsanceState style method. The default is not overriden and used
     * because it is important that mEmailsList gets set up before the {@link EmailListFragment}
     * requests it.
     * @param savedInstanceState the Bundle to restore from
     */
    public void restoreListState(Bundle savedInstanceState) {
        // Pull the saved String ArrayList out of the Bundle
        ArrayList<String> savedEmails = savedInstanceState.getStringArrayList(BUNDLE_EMAILS_KEY);

        // Add the saved ArrayList (resorting happens along the way)
        mEmailsList.addAll(savedEmails);
    }

    /**
     * This method saves the instance state of this Activity to a Bundle that is then used to
     * restore data after Activity recreation. Here, it's used to store the emails List.
     * @param savedInstanceState the Bundle to save to
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        if (mEmailsList != null) {
            // Store the entire ArrayList in the Bundle
            savedInstanceState.putStringArrayList(BUNDLE_EMAILS_KEY, mEmailsList);
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Handle clicks from the "ADD" Menu Item
        if (id == R.id.menu_action_add) {
            showEmailEntryDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * End LifeCycle methods
     */


    /*
     * Work methods. These are the ones that do everything
     */

    /**
     * A helper method that adds 15 random emails to the emails List
     */
    private void addRandomEmails() {
        // This simple not-so-random algorithm was how I was testing initially
        //
        // String letters = "abcdefghijklmno";
        // for (int i = 0; i < 15; i++) {
        //     String randomEmail = String.valueOf(letters.charAt(i)) + "@gmail.com";
        //     mEmailsList.sortedAdd(randomEmail);
        // }

        // Retrieve the array of unordered emails to prepopulate the emails List
        String[] emails = getResources().getStringArray(R.array.random_emails);
        mEmailsList.sortedAdd(emails);
    }

    /**
     * A helper method that shows the email text-entry dialog
     */
    private void showEmailEntryDialog() {
        DialogFragment dialogFragment = new EmailEntryDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    /**
     * A helper method that shows the duplicate email entry error dialog
     */
    private void showEmailDuplicateDialog() {
        new AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.ok, null)
                .setTitle(R.string.dialog_title_duplicate)
                .setMessage(R.string.dialog_message_duplicate)
                .show();
    }

    /**
     * A helper method that takes a passed String and fires an Intent to send an email with that
     * String in the "To:" field
     * @param email the String to use in the "To:" field
     */
    private void sendEmailIntent(String email) {
        Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO);
        feedbackIntent.setType("*/*");
        feedbackIntent.setData(Uri.parse("mailto:"));
        feedbackIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        startActivity(feedbackIntent);
    }

    /*
     * End work methods
     */


    /*
     * Interface methods
     */

    /*
     * EmailListFragment interface methods
     */

    /**
     * This method registers the {@link EmailListFragment}'s Adapter in this Activity so that it can
     * be referenced
     * there
     * @param adapter the Adapter to register
     */
    @Override
    public void registerAdapter(BaseAdapter adapter) {
        mEmailListAdapter = adapter;
    }

    /**
     * This method is called by the ListFragment when a list item is selected. It takes the item
     * position, gets the associated email String, and passes that on to {@link #sendEmailIntent(String)}
     * @param position the selected item position
     */
    @Override
    public void onListItemSelected(int position) {
        sendEmailIntent(mEmailsList.get(position));
    }

    /**
     * This method is called by the {@link EmailListFragment} when it needs the List of emails.
     * @return the List of emails.
     */
    @Override
    public List<String> requestEmails() {
        return mEmailsList;
    }


    /*
     * Dialog interface methods
     */

    /**
     * This method is called by the email entry DialogFragment when a text entry has been made and
     * should be added to the List
     * @param emailEntry the email entry to attempt to add
     */
    public void attemptEmailEntry(String emailEntry) {
        // Check if the entry exists
        if (!mEmailsList.contains(emailEntry)) {
            // Add it if it doesn't
            mEmailsList.sortedAdd(emailEntry);

            // Notify the adapter that the dataset has changed
            mEmailListAdapter.notifyDataSetChanged();
        } else {
            // Show the dupe dialog if the email already exists in the list
            showEmailDuplicateDialog();
        }
    }

    /*
     * End Interface methods
     */
}
