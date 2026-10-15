package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.repository.PatientRepository;
import kigali.clinic.rw.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    public String savePatient(Patient patient) {
        patientRepo.save(patient);
        return "Patient saved successfully";
    }

    public Optional<Patient> getPatientById(UUID id) {
        return patientRepo.findById(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public String deletePatient(UUID id) {
        if (patientRepo.existsById(id)) {
            patientRepo.deleteById(id);
            return "Patient deleted successfully";
        }
        return "Patient not found";
    }

    public String updatePatient(UUID id, Patient patient) {
        if (patientRepo.existsById(id)) {
            patient.setId(id);
            patientRepo.save(patient);
            return "Patient updated successfully";
        }
        return "Patient not found";
    }

    public List<Patient> getPatientsByLastName(String lastName) {
        return patientRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public boolean doctorExists(UUID doctorId) {
        return doctorRepo.existsById(doctorId);
    }

    public List<Patient> getPatientsOfDoctor(UUID doctorId) {
        return patientRepo.findPatientsByDoctorId(doctorId);
    }

    public Page<Patient> getPatientsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepo.findAll(pageable);
    }
}
