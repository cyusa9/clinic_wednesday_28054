package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kigali.clinic.rw.domain.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    Optional<Patient> findBySsNumber(String ssNumber);

    boolean existsBySsNumber(String ssNumber);

    @Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Patient> findPatientsByDoctorId(@Param("doctorId") UUID doctorId);
}
