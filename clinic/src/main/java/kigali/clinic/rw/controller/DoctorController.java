package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController 
@RequestMapping (value = "/api/doctor")
public class DoctorController {

    @Autowired 
    private DoctorService doctorServ;

    @PostMapping(value="/save")
    public ResponseEntity<?> saveDoctor(@RequestBody Doctor doctor){

        String returnedMessage =  doctorServ.saveDoctor(doctor);

        if(returnedMessage.equals("Doctor saved successfully")){
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else{
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

        if ("Doctor deleted successful".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable UUID id, @RequestBody Doctor doctorDetails) {
        String result = doctorServ.updateDoctor(id, doctorDetails);
        if ("Doctor updated successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-specialization")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@RequestParam("name") String name) {
        return new ResponseEntity<>(doctorServ.getDoctorsBySpecialization(name), HttpStatus.OK);
    }
    // B2
    @GetMapping("/without-office")
    public ResponseEntity<List<Doctor>> getDoctorsWithoutOffice() {
        return new ResponseEntity<>(doctorServ.getDoctorsWithoutOffice(), HttpStatus.OK);
    }
}

