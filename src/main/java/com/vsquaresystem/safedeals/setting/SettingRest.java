/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setting")
public class SettingRest {

    @Autowired
    private SettingDAL settingDAL;

    @Autowired
    private SettingService settingService;

//    @RequestMapping(method = RequestMethod.GET)
//    public List<Setting> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
//        return settingDAL.findAll(offset);
//
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Setting findById(@PathVariable("id") Integer id) {
//        return settingDAL.findById(id);
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public Setting insert(@RequestBody Setting setting) {
//        return settingDAL.insert(setting);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable("id") Integer id) {
//        settingDAL.delete(id);
//    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
//    public Setting update(@RequestBody Setting setting) {
//        return settingDAL.update(setting);
//    }
//     @RequestMapping(value = "/setting_key", method = RequestMethod.GET)
//    public SettingKey[] getSettingKeys() {
//        return settingService.getSettingKeys();
//    }
    @RequestMapping(value = "/{settingKey}", method = RequestMethod.GET)
    public Setting getSettingValue(@PathVariable SettingKey settingKey) {
        return settingService.get(settingKey);
    }

    @RequestMapping(value = "/{settingKey}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setSettingValue(@PathVariable SettingKey settingKey, @RequestBody Setting setting) {
        settingService.set(settingKey, setting.getSettingValue());
    }
}
