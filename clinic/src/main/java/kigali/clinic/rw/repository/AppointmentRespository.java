package kigali.clinic.rw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.entity.Appointment;

@Repository 
public interface AppointmentRespository extends JpaRepository<Appointment, UUID> {
    List <Appointment> findByPatientId(String uuid);
}
