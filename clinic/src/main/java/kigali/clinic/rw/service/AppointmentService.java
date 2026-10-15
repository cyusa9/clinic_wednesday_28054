package kigali.clinic.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public String saveAppointment(Appointment appointment) {
        if (appointment.getAppointmentDate() != null && appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            return "Appointment date cannot be in the past";
        }
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        boolean isBooked = appointmentRepository.existsByDoctorAndAppointmentDateAndStatusNot(
                appointment.getDoctor(), 
                appointment.getAppointmentDate(), 
                AppointmentStatus.CANCELLED
        );
        if (isBooked) {
            return "Doctor is already booked on that date";
        }
        appointmentRepository.save(appointment);
        return "Appointment saved successfully";
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAllAppointmentByPatientId(String id) {
        UUID uuid = UUID.fromString(id);
        return appointmentRepository.findByPatientId(uuid);
    }

    public String updateAppointment(UUID id, Appointment appointment) {
        Optional<Appointment> existingOpt = appointmentRepository.findById(id);
        if (existingOpt.isPresent()) {
            Appointment existing = existingOpt.get();
            existing.setAppointmentDate(appointment.getAppointmentDate());
            existing.setReason(appointment.getReason());
            existing.setDoctor(appointment.getDoctor());
            existing.setPatient(appointment.getPatient());
            appointmentRepository.save(existing);
            return "Appointment updated successfully";
        }
        return "Appointment not found";
    }

    public String updateStatus(UUID id, AppointmentStatus newStatus) {
        Optional<Appointment> opt = appointmentRepository.findById(id);
        if (!opt.isPresent()) {
            return "The appointment with that id does not exist";
        }
        Appointment appointment = opt.get();
        AppointmentStatus current = appointment.getStatus();
        boolean allowed = false;
        if (current == AppointmentStatus.SCHEDULED && (newStatus == AppointmentStatus.CONFIRMED || newStatus == AppointmentStatus.CANCELLED)) {
            allowed = true;
        } else if (current == AppointmentStatus.CONFIRMED && (newStatus == AppointmentStatus.COMPLETED || newStatus == AppointmentStatus.CANCELLED)) {
            allowed = true;
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        appointment.setStatus(newStatus);
        appointmentRepository.save(appointment);
        return "Appointment status updated successfully";
    }

    public String rescheduleAppointment(UUID id, LocalDate newDate) {
        Optional<Appointment> opt = appointmentRepository.findById(id);
        if (!opt.isPresent()) {
            return "The appointment with that id does not exist";
        }
        Appointment appointment = opt.get();
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            return "A COMPLETED appointment cannot be rescheduled";
        }
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            return "A CANCELLED appointment cannot be rescheduled";
        }
        if (newDate.isBefore(LocalDate.now())) {
            return "Appointment date cannot be in the past";
        }
        boolean isBooked = appointmentRepository.existsByDoctorAndAppointmentDateAndStatusNot(
                appointment.getDoctor(),
                newDate,
                AppointmentStatus.CANCELLED
        );
        if (isBooked) {
            return "Doctor is already booked on that date";
        }
        appointment.setAppointmentDate(newDate);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointmentRepository.save(appointment);
        return "Appointment rescheduled successfully";
    }

    public String deleteAppointment(UUID id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return "Appointment deleted successfully";
        }
        return "Appointment not found";
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatusOrderByAppointmentDateAsc(status);
    }

    public List<Appointment> getAppointmentsBetween(LocalDate start, LocalDate end) {
        return appointmentRepository.findByAppointmentDateBetweenOrderByAppointmentDateAsc(start, end);
    }

    public Page<Appointment> getAppointmentsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return appointmentRepository.findAll(pageable);
    }
}
