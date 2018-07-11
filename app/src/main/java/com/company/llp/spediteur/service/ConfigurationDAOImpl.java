package com.company.llp.spediteur.service;

import android.util.Log;

import com.company.llp.spediteur.common.model.SettingBean;
import com.company.llp.spediteur.service.interfaces.IConfigurationDAO;

public class ConfigurationDAOImpl implements IConfigurationDAO {
    private static final String TAG = "ConfigurationDAOImpl";
    private static ConfigurationDAOImpl service;


    public static ConfigurationDAOImpl getInstance() {
        if (service == null) {
            service = new ConfigurationDAOImpl();
        }
        return service;
    }

    @Override
    public boolean saveConfig(SettingBean bean) {
        Log.d(TAG,bean.toString());

        return false;
    }

    @Override
    public SettingBean getConfig(int appId) {
        return null;
    }
}
