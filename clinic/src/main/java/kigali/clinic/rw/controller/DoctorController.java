package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorServ;

    @PostMapping("/save")
    public ResponseEntity<?> saveDoctor(@RequestBody Doctor doctor) {
        String returnedMessage = doctorServ.saveDoctor(doctor);
        if (returnedMessage.equals("Doctor saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Doctor is already exist", HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorServ.getAllDoctors();
        if (doctors.isEmpty()) {
            return new ResponseEntity<>(doctors, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable UUID id) {
        String result = doctorServ.deleteDoctor(id);
        if ("Doctor deleted successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable UUID id, @RequestBody Doctor doctorDetails) {
        String result = doctorServ.updateDoctor(id, doctorDetails);
        if ("Doctor updated successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-specialization")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@RequestParam("name") String name) {
        return new ResponseEntity<>(doctorServ.getDoctorsBySpecialization(name), HttpStatus.OK);
    }

    @GetMapping("/without-office")
    public ResponseEntity<List<Doctor>> getDoctorsWithoutOffice() {
        return new ResponseEntity<>(doctorServ.getDoctorsWithoutOffice(), HttpStatus.OK);
    }

    @PutMapping("/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<?> addSpecializationToDoctor(@PathVariable UUID doctorId, @PathVariable UUID specializationId) {
        String result = doctorServ.addSpecializationToDoctor(doctorId, specializationId);
        if ("Specialization added to the doctor".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if ("The doctor already has that specialization".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<?> removeSpecializationFromDoctor(@PathVariable UUID doctorId, @PathVariable UUID specializationId) {
        String result = doctorServ.removeSpecializationFromDoctor(doctorId, specializationId);
        if ("Specialization removed from the doctor".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if ("The doctor does not have that specialization".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{doctorId}/office")
    public ResponseEntity<?> moveDoctorOffice(@PathVariable UUID doctorId, @RequestParam("officeNumber") int officeNumber) {
        String result = doctorServ.moveDoctorOffice(doctorId, officeNumber);
        if ("Office changed successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if ("That office is taken by other doctor".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}
