package kigali.clinic.rw.controller;

import java.time.LocalDate;
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
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.service.AppointmentService;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/save")
    public ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment) {
        String res = appointmentService.saveAppointment(appointment);
        if ("Appointment date cannot be in the past".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else if ("Doctor is already booked on that date".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam("status") AppointmentStatus status) {
        String res = appointmentService.updateStatus(id, status);
        if ("Appointment status updated successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if ("The appointment with that id does not exist".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<?> rescheduleAppointment(@PathVariable UUID id, @RequestParam("newDate") String newDate) {
        LocalDate parsedDate = LocalDate.parse(newDate);
        String res = appointmentService.rescheduleAppointment(id, parsedDate);
        if ("Appointment rescheduled successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if ("The appointment with that id does not exist".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else if ("Doctor is already booked on that date".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
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

    @DeleteMapping(value = {"/{id}", "/delete/{id}"})
    public ResponseEntity<?> deleteAppointment(@PathVariable UUID id) {
        String res = appointmentService.deleteAppointment(id);
        if ("Appointment deleted successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if ("That appointment has prescriptions, delete them first".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@RequestParam("status") AppointmentStatus status) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Appointment>> getAppointmentsBetween(@RequestParam("start") String start, @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(appointmentService.getAppointmentsBetween(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Appointment>> getAppointmentsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(appointmentService.getAppointmentsPaged(page, size), HttpStatus.OK);
    }
}
