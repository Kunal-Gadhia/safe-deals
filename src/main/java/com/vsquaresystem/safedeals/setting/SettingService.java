package com.vsquaresystem.safedeals.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
public class SettingService {

    @Autowired
    private SettingDAL settingDAL;


    public void set(SettingKey key, String value) {
        try {
          
            Setting setting = settingDAL.findByKey(key);
            setting.setSettingValue(value);
            settingDAL.update(setting);
        } catch (EmptyResultDataAccessException erdae) {
           
            Setting setting = new Setting();
            setting.setSettingKey(key);
            setting.setSettingValue(value);
            settingDAL.insert(setting);
        }
    }

    public Setting get(SettingKey key) {        
        try {
            return settingDAL.findByKey(key);
        } catch (EmptyResultDataAccessException erdae) {
       
            Setting setting = new Setting();
            setting.setSettingKey(key);
            return setting;
        }
    }

}

