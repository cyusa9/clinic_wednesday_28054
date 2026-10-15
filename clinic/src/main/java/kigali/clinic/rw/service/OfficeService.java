package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.repository.OfficeRepository;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepo;

    public String saveOffice(Office office) {
        officeRepo.save(office);
        return "Office saved successfully";
    }

    public Optional<Office> getOfficeByNumber(int officeNumber) {
        return officeRepo.findByOfficeNumber(officeNumber);
    }

    public List<Office> getAllOffices() {
        return officeRepo.findAll();
    }

    public List<Office> getFreeOffices() {
        return officeRepo.findFreeOffices();
    }

    public String deleteOffice(UUID id) {
        if (officeRepo.existsById(id)) {
            officeRepo.deleteById(id);
            return "Office deleted successfully";
        }
        return "Office not found";
    }

    public String updateOffice(UUID id, Office office) {
        if (officeRepo.existsById(id)) {
            office.setId(id);
            officeRepo.save(office);
            return "Office updated successfully";
        }
        return "Office not found";
    }
}
