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
import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.service.SpecializationService;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    @Autowired
    private SpecializationService specService;

    @PostMapping("/save")
    public ResponseEntity<?> saveSpecialization(@RequestBody Specialization specialization) {
        String res = specService.saveSpecialization(specialization);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        return new ResponseEntity<>(specService.getAllSpecializations(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSpecialization(@PathVariable UUID id, @RequestBody Specialization specialization) {
        String res = specService.updateSpecialization(id, specialization);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSpecialization(@PathVariable UUID id) {
        String res = specService.deleteSpecialization(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Specialization>> getUnusedSpecializations() {
        return new ResponseEntity<>(specService.getUnusedSpecializations(), HttpStatus.OK);
    }
}
