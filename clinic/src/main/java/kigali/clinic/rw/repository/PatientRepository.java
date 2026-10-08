package kigali.clinic.rw.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kigali.clinic.rw.domain.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository 
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    // A1
    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // B4: DISTINCT, path navigation
    @Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Patient> findPatientsByDoctorId(@Param("doctorId") UUID doctorId);

    // C2: Count patients with > 1 appointment
    @Query("SELECT COUNT(p) FROM Patient p WHERE SIZE(p.appointments) > 1")
    long countPatientsWithMultipleAppointments();
}
