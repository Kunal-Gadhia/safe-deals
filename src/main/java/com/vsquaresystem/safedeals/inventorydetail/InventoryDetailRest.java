/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.inventorydetail;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/inventory_detail")
public class InventoryDetailRest {

    @Autowired
    private InventoryDetailDAL inventoryDetailDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<InventoryDetail> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return inventoryDetailDAL.findAll(offset);
    }

    @RequestMapping(value = "/find/inventoryHeadId", method = RequestMethod.GET)
    public List<InventoryDetail> findByInventoryHeadId(@RequestParam("inventoryHeadId") Integer inventoryHeadId) {
        return inventoryDetailDAL.findByInventoryHeadId(inventoryHeadId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public InventoryDetail findById(@PathVariable("id") Integer id) {
        return inventoryDetailDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public InventoryDetail insert(@RequestBody InventoryDetail inventoryDetail) throws JsonProcessingException {
        return inventoryDetailDAL.insert(inventoryDetail);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public InventoryDetail update(@RequestBody InventoryDetail inventoryDetail) throws JsonProcessingException {
        return inventoryDetailDAL.update(inventoryDetail);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        inventoryDetailDAL.delete(id);
    }

}
