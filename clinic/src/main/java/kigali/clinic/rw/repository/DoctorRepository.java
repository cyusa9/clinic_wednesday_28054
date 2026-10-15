package kigali.clinic.rw.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Office;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    @Query("SELECT d FROM Doctor d JOIN d.specializations s WHERE LOWER(s.name) = LOWER(:name)")
    List<Doctor> findBySpecializationName(@Param("name") String name);

    @Query("SELECT d FROM Doctor d WHERE d.office IS NULL ORDER BY d.lastName ASC")
    List<Doctor> findDoctorsWithoutOffice();

    boolean existsByOffice(Office office);

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY d.lastName ASC")
    List<Doctor> searchByName(@Param("name") String name);
}
