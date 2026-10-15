package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.service.PatientService;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientServ;

    @PostMapping("/save")
    public ResponseEntity<?> savePatient(@RequestBody Patient patient) {
        String returnedMessage = patientServ.savePatient(patient);
        if (returnedMessage.equals("Patient saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
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
        if ("Patient deleted successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable UUID id, @RequestBody Patient patientDetails) {
        String result = patientServ.updatePatient(id, patientDetails);
        if ("Patient updated successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Patient>> getPatientsByLastName(@RequestParam("lastName") String lastName) {
        List<Patient> patients = patientServ.getPatientsByLastName(lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/of-doctor/{doctorId}")
    public ResponseEntity<?> getPatientsOfDoctor(@PathVariable UUID doctorId) {
        if (!patientServ.doctorExists(doctorId)) {
            return new ResponseEntity<>("The doctor with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patientServ.getPatientsOfDoctor(doctorId), HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Patient>> getPatientsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(patientServ.getPatientsPaged(page, size), HttpStatus.OK);
    }

    @GetMapping("/{ssNumber}/upcoming")
    public ResponseEntity<?> getUpcomingAppointments(@PathVariable String ssNumber) {
        List<Appointment> appointments = patientServ.getUpcomingAppointments(ssNumber);
        if (appointments == null) {
            return new ResponseEntity<>("The patient with that social security number does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}
