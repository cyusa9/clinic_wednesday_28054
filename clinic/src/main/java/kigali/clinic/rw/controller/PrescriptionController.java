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
import org.springframework.web.bind.annotation.RestController;
import kigali.clinic.rw.domain.Prescription;
import kigali.clinic.rw.service.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/save")
    public ResponseEntity<?> savePrescription(@RequestBody Prescription prescription) {
        String result = prescriptionService.savePrescription(prescription);
        if ("Prescription saved successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else if ("The appointment with that id does not exist".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if ("Prescriptions are only for COMPLETED appointments".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getPrescriptionsByAppointment(@PathVariable UUID appointmentId) {
        List<Prescription> list = prescriptionService.getPrescriptionsByAppointment(appointmentId);
        if (list == null) {
            return new ResponseEntity<>("The appointment with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/patient/{ssNumber}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatientSsNumber(@PathVariable String ssNumber) {
        List<Prescription> list = prescriptionService.getPrescriptionsByPatientSsNumber(ssNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping(value = {"/{id}", "/update/{id}"})
    public ResponseEntity<?> updatePrescription(@PathVariable UUID id, @RequestBody Prescription prescription) {
        String result = prescriptionService.updatePrescription(id, prescription);
        if ("Prescription updated successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = {"/{id}", "/delete/{id}"})
    public ResponseEntity<?> deletePrescription(@PathVariable UUID id) {
        String result = prescriptionService.deletePrescription(id);
        if ("Prescription deleted successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/stats/top-medicines")
    public ResponseEntity<List<Object[]>> getTopMedicines() {
        return new ResponseEntity<>(prescriptionService.getTopMedicines(), HttpStatus.OK);
    }
}
