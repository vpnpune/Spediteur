package com.company.llp.spediteur.service.interfaces;

import com.company.llp.spediteur.common.model.SettingBean;

public interface IConfigurationDAO{

    boolean saveConfig(SettingBean settingBean);
    SettingBean getConfig(int appId);
}