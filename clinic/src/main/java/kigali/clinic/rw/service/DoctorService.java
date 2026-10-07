package kigali.clinic.rw.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.repository.DoctorRepository;

@Service 
public class DoctorService {

    @Autowired 
    private DoctorRepository doctorRepo;

    public String saveDoctor(Doctor doctor){
        doctorRepo.save(doctor);
        return "Doctor saved successfully";
    }
    
    public Doctor getDoctorById(UUID id) {
        return doctorRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "Doctor not found"
            ));
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public String deleteDoctor(UUID id) {
        if (doctorRepo.existsById(id)) {
            doctorRepo.deleteById(id);
            return "Doctor deleted successfully";
        } else{
            return "Doctor not found";
        }
    }

    public String updateDoctor(UUID id, Doctor doctor) {
        if (doctorRepo.existsById(id)) {
            // doctor.setId(id); // Skipping setId for now since it doesn't exist
            doctorRepo.save(doctor);
            return "Doctor updated successfully";
        }
        return "Doctor not found";
    }
}
