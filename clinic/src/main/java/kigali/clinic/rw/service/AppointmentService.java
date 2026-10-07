package kigali.clinic.rw.service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.repository.AppointmentRepository;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // A4
    public String saveAppointment(Appointment appointment) {
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
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.save(appointment);
            return "Appointment updated successfully";
        }
        return "Appointment not found";
    }

    public String deleteAppointment(UUID id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return "Appointment deleted successfully";
        }
        return "Appointment not found";
    }

    // A2
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatusOrderByAppointmentDateAsc(status);
    }

    // A3
    public List<Appointment> getAppointmentsBetween(LocalDate start, LocalDate end) {
        return appointmentRepository.findByAppointmentDateBetweenOrderByAppointmentDateAsc(Date.valueOf(start), Date.valueOf(end));
    }
}
