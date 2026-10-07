package kigali.clinic.rw.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.repository.OfficeRepository;

@Service 
public class OfficeService {

    @Autowired 
    private OfficeRepository officeRepo;

    public String saveOffice(Office office){
        //officeRepo.findByOfficeNub
        officeRepo.save(office);
        return "Office saved successfully";
    }
    
    public Office getOfficeByNumber(int officeNumber) {
        return officeRepo.findByOfficeNumber(officeNumber)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "Office not found with room number: " + officeNumber
            ));
    }

    public List<Office> getAllOffices() {
        return officeRepo.findAll();
    }
    
    public String deleteOffice(UUID id) {
        if (officeRepo.existsById(id)) {
            officeRepo.deleteById(id);
            return "Office deleted successful";
        } else{
            return "Office not found";
        }
        
    }

    public String updateOffice(UUID id, Office office) {
        if (officeRepo.existsById(id)) {
            office.setId(id);
            officeRepo.save(office);
            return "Office updated successfully";
        }
        return "Office not found";
    }

    //public String updateById(UUID id, Office office){}
}
