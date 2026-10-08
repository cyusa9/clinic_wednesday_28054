package kigali.clinic.rw.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kigali.clinic.rw.domain.Specialization;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, UUID> {

    // B3: IS EMPTY
    @Query("SELECT s FROM Specialization s WHERE s.doctors IS EMPTY")
    List<Specialization> findUnusedSpecializations();
}
