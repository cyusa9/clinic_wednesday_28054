package kigali.clinic.rw.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.SpecializationRepository;

@Service
public class SpecializationService {
    @Autowired
    private SpecializationRepository specRepo;

    public String saveSpecialization(Specialization specialization) {
        specRepo.save(specialization);
        return "Specialization saved successfully";
    }

    public List<Specialization> getAllSpecializations() {
        return specRepo.findAll();
    }

    public String updateSpecialization(UUID id, Specialization specialization) {
        if (specRepo.existsById(id)) {
            specRepo.save(specialization);
            return "Specialization updated successfully";
        }
        return "Specialization not found";
    }

    public String deleteSpecialization(UUID id) {
        if (specRepo.existsById(id)) {
            specRepo.deleteById(id);
            return "Specialization deleted successfully";
        }
        return "Specialization not found";
    }
}
