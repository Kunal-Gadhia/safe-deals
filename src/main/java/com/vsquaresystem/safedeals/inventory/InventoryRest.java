package com.vsquaresystem.safedeals.inventory;

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
@RequestMapping("/inventory")
public class InventoryRest {
    
    @Autowired
    private InventoryDAL inventoryDAL;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Inventory> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return inventoryDAL.findAll(offset);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Inventory findById(@PathVariable("id") Integer id) {
        return inventoryDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Inventory insert(@RequestBody Inventory inventory) throws JsonProcessingException {
        return inventoryDAL.insert(inventory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Inventory update(@RequestBody Inventory inventory) throws JsonProcessingException {
        return inventoryDAL.update(inventory);
    }
    
     @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        inventoryDAL.delete(id);
    }
}
