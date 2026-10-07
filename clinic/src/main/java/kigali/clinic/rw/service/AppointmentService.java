package kigali.clinic.rw.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.repository.AppointmentRepository;
import kigali.clinic.rw.repository.PatientRepository;

@Service 
public class AppointmentService{
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository , PatientRepository patientRepository){
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public List<Appointment> getAllAppointmentByPatientId(String id){
        UUID uuid = UUID.fromString(id);
        return appointmentRepository.findByPatientId(uuid);
    }
}
