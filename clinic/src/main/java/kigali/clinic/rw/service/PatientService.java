package kigali.clinic.rw.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.repository.PatientRepository;

@Service 
public class PatientService {

    @Autowired 
    private PatientRepository patientRepo;

    public String savePatient(Patient patient){
        patientRepo.save(patient);
        return "Patient saved successfully";
    }
    
    public Patient getPatientById(UUID id) {
        return patientRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "Patient not found"
            ));
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public String deletePatient(UUID id) {
        if (patientRepo.existsById(id)) {
            patientRepo.deleteById(id);
            return "Patient deleted successfully";
        } else{
            return "Patient not found";
        }
    }

    public String updatePatient(UUID id, Patient patient) {
        if (patientRepo.existsById(id)) {
            // patient.setId(id); // Skipping setId for now
            patientRepo.save(patient);
            return "Patient updated successfully";
        }
        return "Patient not found";
    }
}
