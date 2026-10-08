package kigali.clinic.rw.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.service.AppointmentService;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    // A4
    @PostMapping("/save")
    public ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment) {
        String res = appointmentService.saveAppointment(appointment);
        if (res.equals("Doctor is already booked on that date")) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable UUID id, @RequestBody Appointment appointment) {
        String res = appointmentService.updateAppointment(id, appointment);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable UUID id) {
        String res = appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // A2
    @GetMapping("/by-status")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@RequestParam("status") AppointmentStatus status) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByStatus(status), HttpStatus.OK);
    }

    // A3
    @GetMapping("/between")
    public ResponseEntity<List<Appointment>> getAppointmentsBetween(@RequestParam("start") String start, @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(appointmentService.getAppointmentsBetween(startDate, endDate), HttpStatus.OK);
    }

    // C1
    @GetMapping("/canceled-count")
    public ResponseEntity<Long> countCanceled() {
        return new ResponseEntity<>(appointmentService.getCanceledCount(), HttpStatus.OK);
    }

    // C3
    @PutMapping("/cancel-for-patient/{patientId}")
    public ResponseEntity<Integer> cancelForPatient(@PathVariable UUID patientId) {
        return new ResponseEntity<>(appointmentService.cancelForPatient(patientId), HttpStatus.OK);
    }

    // C4
    @DeleteMapping("/canceled")
    public ResponseEntity<Integer> deleteCanceled() {
        return new ResponseEntity<>(appointmentService.deleteCanceled(), HttpStatus.OK);
    }

    // BONUS: Paging
    @GetMapping("/paged")
    public ResponseEntity<Page<Appointment>> getAppointmentsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(appointmentService.getAppointmentsPaged(page, size), HttpStatus.OK);
    }
}
