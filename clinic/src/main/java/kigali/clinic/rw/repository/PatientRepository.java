package kigali.clinic.rw.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kigali.clinic.rw.domain.Patient;

@Repository 
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    // Removed findByOfficeNumber and findByName because Patient does not have those properties!
    // If you want to find by name, use findByFirstName(String firstName) or findByLastName(String lastName)
}
