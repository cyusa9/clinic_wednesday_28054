package kigali.clinic.rw.repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Doctor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository 
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatientId(UUID patientId);

    // A2
    List<Appointment> findByStatusOrderByAppointmentDateAsc(AppointmentStatus status);

    // A3
    List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAsc(Date start, Date end);

    // A4
    boolean existsByDoctorAndAppointmentDateAndStatusNot(Doctor doctor, Date appointmentDate, AppointmentStatus status);

    // C1: Count canceled
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.status = 'CANCELLED'")
    long countCanceledAppointments();

    // C3: Bulk update
    @Modifying
    @Transactional
    @Query("UPDATE Appointment a SET a.status = 'CANCELLED' WHERE a.patient.id = :patientId AND a.status != 'CANCELLED'")
    int cancelAppointmentsForPatient(@Param("patientId") UUID patientId);

    // C4: Bulk delete
    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.status = 'CANCELLED'")
    int deleteCanceledAppointments();
}
