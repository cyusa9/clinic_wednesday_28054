package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.service.PatientService;

@RestController 
@RequestMapping(value = "/api/patients")
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

    // A1
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Patient>> getPatientsByLastName(@RequestParam("lastName") String lastName) {
        List<Patient> patients = patientServ.getPatientsByLastName(lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    // B4
    @GetMapping("/of-doctor/{doctorId}")
    public ResponseEntity<?> getPatientsOfDoctor(@PathVariable UUID doctorId) {
        if (!patientServ.doctorExists(doctorId)) {
            return new ResponseEntity<>("The doctor with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patientServ.getPatientsOfDoctor(doctorId), HttpStatus.OK);
    }
}
