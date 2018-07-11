package com.company.llp.spediteur;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.company.llp.spediteur.common.APP_CONSTANTS;
import com.company.llp.spediteur.common.model.SettingBean;
import com.company.llp.spediteur.service.ConfigurationDAOImpl;
import com.company.llp.spediteur.service.interfaces.IConfigurationDAO;
import com.company.llp.spediteur.utlities.DBManager;
import com.company.llp.spediteur.utlities.DatabaseHelper;

public class ServerSetttings extends AppCompatActivity {
    private static final String TAG = "ServerSetttings";
    private IConfigurationDAO settingsDAO = ConfigurationDAOImpl.getInstance();

    private DBManager dbManager;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{DatabaseHelper._ID,
            DatabaseHelper.SERVER_URL, DatabaseHelper.EXTRACTION_KEY};

    final int[] to = new int[]{R.id.showTitle, R.id.showTitle, R.id.showTitle};
    private EditText txtServerURL;
    private EditText txtExtractionKey;
    private Switch switchServerState;
    private EditText txtAppId;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_setttings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbManager = new DBManager(this);
        txtAppId = (EditText) findViewById(R.id.appIdText);
        txtExtractionKey = (EditText) findViewById(R.id.extractionKey);
        txtServerURL = (EditText) findViewById(R.id.serverUrlText);
        switchServerState = (Switch) findViewById(R.id.serverOnOffToggle);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int appId=0 ;
                if(txtAppId.getEditableText()!=null && !"".equals(txtAppId.getEditableText().toString())){
                    appId= Integer.parseInt(txtAppId.getEditableText().toString());
                }

                if (appId > 0) {
                    loadSavedSettingsForAppId(appId);
                } else {
                    Snackbar.make(view, "Please enter Application ID e.g Velo de Piste:1", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            }
        });
        Button saveBtn = (Button) findViewById(R.id.saveSettingBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int appId = Integer.parseInt(txtAppId.getEditableText().toString());
                String extractKey = txtExtractionKey.getEditableText().toString();
                String serverUrl = txtServerURL.getEditableText().toString();
                boolean state = switchServerState.isChecked();
                SettingBean settingBean = new SettingBean();
                settingBean.setAppId(appId);
                settingBean.setEnabled(state);
                settingBean.setExtractionKey(extractKey.trim());
                settingBean.setForwardURL(serverUrl.trim());
                saveOrUpdateDB(settingBean);

            }
        });

    }


    private void saveOrUpdateDB(SettingBean settingBean) {
        if (!dbManager.checkIfOpened())
            dbManager.open();
        //settingsDAO.saveConfig(bean);
        if (settingBean.getId() > 0) {
            // update settings
            long id = dbManager.update(settingBean);
            if (id > 0) {
                Toast.makeText(this, "Updated Sucesssfully " + id, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Error occured during update", Toast.LENGTH_LONG).show();

            }


        } else {
            // insert
            long id = dbManager.insert(settingBean);
            if (id > 0) {
                Toast.makeText(this, "Saved Sucesssfully " + id, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Error occured", Toast.LENGTH_LONG).show();

            }
        }

        setSession(settingBean);
        dbManager.close();
        resetForm();

    }

    private void loadSavedSettingsForAppId(int appId) {

        SettingBean settingBean=null;
        if (!dbManager.checkIfOpened())
            dbManager.open()

        // fetchOne
        ;
        Cursor cursor = dbManager.getSettingOne(appId);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DatabaseHelper._ID));
            String extractionKey = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.EXTRACTION_KEY));
            String forwardURL = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.SERVER_URL));
            short serverState = cursor.getShort(cursor.getColumnIndexOrThrow(DatabaseHelper.IS_ENABLED));
            Log.e(TAG, "Server State " + serverState);
            settingBean = new SettingBean();
            settingBean.setAppId(1);
            settingBean.setEnabled(serverState == 0 ? false : true);
            settingBean.setExtractionKey(extractionKey);
            settingBean.setForwardURL(forwardURL);
            settingBean.setId(itemId);
            Log.e(TAG, "KEY" + extractionKey + "  url" + forwardURL);
        }
        if (settingBean!=null && settingBean.getId() > 0) {
            setDataToView(settingBean);
        } else {
            // show snackbar
            Toast.makeText(this, "No records found", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        dbManager.close();



    }

    private void setSession(SettingBean bean){
        sharedpreferences = getSharedPreferences(APP_CONSTANTS.SpediteurPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(APP_CONSTANTS.APP_ID, bean.getAppId());
        editor.putString(APP_CONSTANTS.URL, bean.getForwardURL());
        editor.putBoolean(APP_CONSTANTS.SERVER_STATUS, bean.isEnabled());
        editor.putString(APP_CONSTANTS.EXTRACT_KEY, bean.getExtractionKey());

        editor.commit();


    }
    private void setDataToView(SettingBean bean) {
        txtAppId.setText(String.valueOf(bean.getAppId()));
        txtExtractionKey.setText(bean.getExtractionKey());
        txtServerURL.setText(bean.getForwardURL());
        switchServerState.setChecked(bean.isEnabled());
    }

    private void resetForm() {
        txtAppId.setText("");
        txtExtractionKey.setText("");
        txtServerURL.setText("");
        switchServerState.setChecked(false);
        }
}
