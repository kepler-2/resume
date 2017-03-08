package com.kepler.resume;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.kepler.resume.adapter.UrlListingAdapter;
import com.kepler.resume.datebaseHelper.URLSetterGetter;
import com.kepler.resume.datebaseHelper.UrlDAO;
import com.kepler.resume.support.ActivityStarter;
import com.kepler.resume.support.Logger;
import com.kepler.resume.support.MyDialog;
import com.kepler.resume.support.OnActionListener;
import com.kepler.resume.support.Params;

import butterknife.BindView;

public class UrlListing extends BaseActivity implements OnActionListener, AdapterView.OnItemClickListener {

    @BindView(R.id.listView)
    ListView listView;
    private UrlListingAdapter urlListingAdapter;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>List</font>",Html.FROM_HTML_MODE_LEGACY));
        }else
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>List</font>"));
        urlListingAdapter = new UrlListingAdapter(getApplicationContext());
        listView.setAdapter(urlListingAdapter);
        listView.setOnItemClickListener(this);
        url = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Params.URL, Params.URL_AMITKUMARJAISWAL);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_url_listing;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_url_listing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

                openAddUrlDialog("Add Url");

//                ActivityStarter.startBaseActivityForResult(WebActivity.this, null, UrlListing.class, 999);
                break;
        }
        return true;
    }

    private void openAddUrlDialog(final String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        View view = getLayoutInflater().inflate(R.layout.add_url, null);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        final EditText et_url = (EditText) view.findViewById(R.id.et_url);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.protocals, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
//// Set up the input
        builder.setView(view);

// Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UrlListing.this.onOkay(spinner.getSelectedItem().toString() + "#" + et_url.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                UrlListing.this.onCancel(null);
            }
        });
        final Dialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    @Override
    public void onOkay(Object object) {
        UrlDAO urlDAO = new UrlDAO(getApplicationContext());
        urlDAO.insert_url(new URLSetterGetter(object.toString().split("#")[0] + "://" + Params.WWW + object.toString().split("#")[1]));
        urlDAO.close();
        urlListingAdapter.notifyNewUrl();
    }

    @Override
    public void onCancel(Object object) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                .putString(Params.URL, urlListingAdapter.getItem(i).getURL()).apply();
        urlListingAdapter.setDefault(i);
        Logger.makeSimpleToast(getApplicationContext(), "This url set as default");
    }

    @Override
    public void onBackPressed() {
        if (!url.equalsIgnoreCase(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Params.URL, Params.URL_AMITKUMARJAISWAL))) {
            setResult(Activity.RESULT_OK);
        }
        super.onBackPressed();
    }
}
