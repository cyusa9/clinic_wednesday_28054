package kigali.clinic.rw.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.service.OfficeService;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveOffice(@RequestBody Office office) {
        String returnedMessage = officeServ.saveOffice(office);
        if (returnedMessage.equals("Office saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Office is already exist", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/free")
    public ResponseEntity<List<Office>> getFreeOffices() {
        return new ResponseEntity<>(officeServ.getFreeOffices(), HttpStatus.OK);
    }

    @GetMapping("/room/{officeNumber}")
    public ResponseEntity<?> getOfficeByNumber(@PathVariable("officeNumber") int officeNumber) {
        Optional<Office> office = officeServ.getOfficeByNumber(officeNumber);
        if (office.isPresent()) {
            return new ResponseEntity<>(office.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Office not found with room number: " + officeNumber, HttpStatus.NOT_FOUND);
        }
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
        if ("Office deleted successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOffice(@PathVariable UUID id, @RequestBody Office officeDetails) {
        String result = officeServ.updateOffice(id, officeDetails);
        if ("Office updated successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}
