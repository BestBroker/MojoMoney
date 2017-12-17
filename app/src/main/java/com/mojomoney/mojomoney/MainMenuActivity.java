package com.mojomoney.mojomoney;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements ThemePickerDialogFragment.NoticeDialogListener {

    private DrawerLayout mDrawerLayout;
    private ListView mlistView;
    String [] navigation_drawer_options;

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        SharedPreferences preferences = getSharedPreferences("theme_toggle", MODE_PRIVATE);
        SplashActivity.theme = preferences.getInt("theme_toggle", 0);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Navigation Drawer methods
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mlistView = (ListView) findViewById(R.id.drawer_menu_listview);
        Resources res = getResources();
        navigation_drawer_options = res.getStringArray(R.array.navigation_options);

        //TODO: So richtig?
        MyArrayAdapter adapter = new MyArrayAdapter(this, navigation_drawer_options);
        mlistView.setAdapter(adapter);
        mlistView.setOnItemClickListener(new DrawerItemClickListener());


        //TODO: TRY and CATCH
        try{}catch(Exception e){
            Toast.makeText(getApplicationContext(), "Fehler",  Toast.LENGTH_LONG);
        }

        if(SplashActivity.theme == 1) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    class MyArrayAdapter extends ArrayAdapter<String>{
        Context context;

        String myNavigationOptions [];

        MyArrayAdapter(Context c, String [] navigation_drawer_options){
            super(c, R.layout.listview_resource, R.id.drawer_menu_listview_test);
            this.context = c;
            this.myNavigationOptions = navigation_drawer_options;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listview_resource = layoutInflater.inflate(R.layout.listview_resource, parent, false);
            TextView myOption = (TextView) findViewById(R.id.MenuOptions);
            myOption.setText(navigation_drawer_options[position]);
            return listview_resource;
        }


    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ListViewClicked(i);
        }


    }

    //TODO: Nicht benutzt
    class ArrayAdapterListView implements  AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ListViewClicked(i);
        }
    }

    public void ListViewClicked ( int i){
        switch(i) {
            case 0:
                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG);
                break;
        }
        mlistView.setItemChecked(i, true);
        mDrawerLayout.closeDrawer(mlistView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryRefinementEnabled(true);


        return true;
    }


    //TODO: Unnoetig geworden
    public void reset(View view) {
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("password", "");
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(intent);
        finish();
    }

    public void showAllEntries (View view){
        Intent intent = new Intent(getApplicationContext(), ViewEntriesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, NewEntryActivity.class);
        Intent intent2 = new Intent(getApplicationContext(), ViewEntriesActivity.class);
        switch (item.getItemId()) {
            case R.id.action_search:

                //onSearchRequested();
                return true;

            case R.id.action_settings:

                startActivity(intent);
                return true;

            case R.id.action_settings2:

                startActivity(intent2);
                return true;

            case R.id.action_settings3:

                DialogFragment newFragment = new ThemePickerDialogFragment();
                newFragment.show(getSupportFragmentManager(), "theme");
                return true;

            case R.id.reset_password:

                DialogFragment newnewFragment = new ResetPasswordDialogFragment();
                newnewFragment.show(getSupportFragmentManager(), "reset_password");
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    public void new_entry(View view) {
        startActivity(new Intent(getApplicationContext(), NewEntryActivity.class));
    }
}
