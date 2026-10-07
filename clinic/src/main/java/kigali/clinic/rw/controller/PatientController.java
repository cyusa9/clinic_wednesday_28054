package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.service.PatientService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController 
@RequestMapping (value = "/api/patient")
public class PatientController {

    @Autowired 
    private PatientService patientServ;

    @PostMapping(value="/save")
    public ResponseEntity<?> savePatient(@RequestBody Patient patient){

        String returnedMessage =  patientServ.savePatient(patient);

        if(returnedMessage.equals("Patient saved successfully")){
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Patient is already exist", HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientServ.getAllPatients();
        if (patients.isEmpty()) {
            return new ResponseEntity<>(patients, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable UUID id) {
        String result = patientServ.deletePatient(id);

        if ("Patient deleted successful".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable UUID id, @RequestBody Patient patientDetails) {
        String result = patientServ.updatePatient(id, patientDetails);
        if ("Patient updated successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}
