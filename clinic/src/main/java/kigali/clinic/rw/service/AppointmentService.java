package kigali.clinic.rw.service;

import java.util.List;
import java.util.UUID;

import kigali.clinic.rw.entity.Appointment;
import kigali.clinic.rw.repository.AppointmentRespository;

@service 
public class AppointmentService{
    private final AppointmentRespository appointmentRespository;
    public AppointmentService(AppointmentRespository appointmentRespository , PatientRepository patientRepository   ){
        this.appointmentRespository=appointmentRespository;
        this.patientRepository=patientRepository;
    }

    public List<Appointment> getAllAppointmentByPatientId(String id){
        UUID uuid = UUID.fromString(id);
        return appointmentRespository.findByPatientId(uuid);
    }
    
    
}
