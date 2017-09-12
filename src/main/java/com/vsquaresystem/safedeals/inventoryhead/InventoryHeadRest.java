package com.vsquaresystem.safedeals.inventoryhead;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory_head")

public class InventoryHeadRest {

    @Autowired
    private InventoryHeadDAL inventoryHeadDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<InventoryHead> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return inventoryHeadDAL.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public InventoryHead findById(@PathVariable("id") Integer id) {
        return inventoryHeadDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public InventoryHead insert(@RequestBody InventoryHead inventoryHead) throws JsonProcessingException {
        return inventoryHeadDAL.insert(inventoryHead);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public InventoryHead update(@RequestBody InventoryHead inventoryHead) throws JsonProcessingException {
        return inventoryHeadDAL.update(inventoryHead);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        inventoryHeadDAL.delete(id);
    }
}
