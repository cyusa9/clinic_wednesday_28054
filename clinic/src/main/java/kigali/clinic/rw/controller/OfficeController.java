package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.service.OfficeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController 
@RequestMapping (value = "/api/office")
public class OfficeController {

    @Autowired 
    private OfficeService officeServ;

    @PostMapping(value="/save")
    public ResponseEntity<?> saveOffice(@RequestBody Office office){

        String returnedMessage =  officeServ.saveOffice(office);

        if(returnedMessage.equals("Office saved successfully")){
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Office is already exist", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/room/{officeNumber}")
    public ResponseEntity<Office> getOfficeByNumber(@PathVariable("officeNumber") int officeNumber) {
        Office office = officeServ.getOfficeByNumber(officeNumber);
        return ResponseEntity.ok(office);
    }

    @GetMapping
    public ResponseEntity<List<Office>> getAllOffices() {
        List<Office> offices = officeServ.getAllOffices();
        if (offices.isEmpty()) {
            return new ResponseEntity<>(offices, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(offices, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOffice(@PathVariable UUID id) {
        String result = officeServ.deleteOffice(id);

        if ("Office deleted successful".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOffice(@PathVariable UUID id, @RequestBody Office officeDetails) {
        String result = officeServ.updateOffice(id, officeDetails);
        if ("Office updated successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
}
