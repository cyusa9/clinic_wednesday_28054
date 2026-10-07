package kigali.clinic.rw.repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Doctor;

@Repository 
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatientId(UUID patientId);

    // A2
    List<Appointment> findByStatusOrderByAppointmentDateAsc(AppointmentStatus status);

    // A3
    List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAsc(Date start, Date end);

    // A4
    boolean existsByDoctorAndAppointmentDateAndStatusNot(Doctor doctor, Date appointmentDate, AppointmentStatus status);
}
