package com.vsquaresystem.safedeals.incomeslab;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/incomeslab")
public class IncomeslabRest {
    
    @Autowired
    private IncomeslabDAL incomeslabDAL;
    
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Incomeslab> findAll(@RequestParam(value = "offset",required = false, defaultValue = "0") Integer offset){
        
        return incomeslabDAL.findAll(offset);
        
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    
    public Incomeslab findById (@PathVariable("id") Integer id) {

        return incomeslabDAL.findById(id);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Incomeslab insert(@RequestBody Incomeslab incomeslab) {
        return incomeslabDAL.insert(incomeslab);
    }
    
    @RequestMapping(value = "/find/bank_id", method = RequestMethod.GET)
    public List<Incomeslab> findByBankId(@RequestParam("bankId") Integer bankId) {
        return incomeslabDAL.findByBankId(bankId);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        incomeslabDAL.delete(id);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Incomeslab update(@RequestBody Incomeslab incomeslab) {
        return incomeslabDAL.update(incomeslab);
    }

}
       