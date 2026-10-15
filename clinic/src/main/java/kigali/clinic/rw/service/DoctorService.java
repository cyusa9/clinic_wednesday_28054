package kigali.clinic.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.AppointmentRepository;
import kigali.clinic.rw.repository.DoctorRepository;
import kigali.clinic.rw.repository.OfficeRepository;
import kigali.clinic.rw.repository.SpecializationRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private SpecializationRepository specializationRepo;

    @Autowired
    private OfficeRepository officeRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    public String saveDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
        return "Doctor saved successfully";
    }

    public Optional<Doctor> getDoctorById(UUID id) {
        return doctorRepo.findById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public String deleteDoctor(UUID id) {
        if (doctorRepo.existsById(id)) {
            doctorRepo.deleteById(id);
            return "Doctor deleted successfully";
        }
        return "Doctor not found";
    }

    public String updateDoctor(UUID id, Doctor doctor) {
        if (doctorRepo.existsById(id)) {
            doctor.setId(id);
            doctorRepo.save(doctor);
            return "Doctor updated successfully";
        }
        return "Doctor not found";
    }

    public List<Doctor> getDoctorsBySpecialization(String name) {
        return doctorRepo.findBySpecializationName(name);
    }

    public List<Doctor> getDoctorsWithoutOffice() {
        return doctorRepo.findDoctorsWithoutOffice();
    }

    public String addSpecializationToDoctor(UUID doctorId, UUID specializationId) {
        Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);
        if (!doctorOpt.isPresent()) {
            return "The doctor with that id does not exist";
        }
        Optional<Specialization> specOpt = specializationRepo.findById(specializationId);
        if (!specOpt.isPresent()) {
            return "That specialization does not exist";
        }
        Doctor doctor = doctorOpt.get();
        Specialization spec = specOpt.get();
        if (doctor.getSpecializations().contains(spec)) {
            return "The doctor already has that specialization";
        }
        doctor.getSpecializations().add(spec);
        doctorRepo.save(doctor);
        return "Specialization added to the doctor";
    }

    public String removeSpecializationFromDoctor(UUID doctorId, UUID specializationId) {
        Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);
        if (!doctorOpt.isPresent()) {
            return "The doctor with that id does not exist";
        }
        Optional<Specialization> specOpt = specializationRepo.findById(specializationId);
        if (!specOpt.isPresent()) {
            return "That specialization does not exist";
        }
        Doctor doctor = doctorOpt.get();
        Specialization spec = specOpt.get();
        if (!doctor.getSpecializations().contains(spec)) {
            return "The doctor does not have that specialization";
        }
        doctor.getSpecializations().remove(spec);
        doctorRepo.save(doctor);
        return "Specialization removed from the doctor";
    }

    public String moveDoctorOffice(UUID doctorId, int officeNumber) {
        Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);
        if (!doctorOpt.isPresent()) {
            return "The doctor with that id does not exist";
        }
        Optional<Office> officeOpt = officeRepo.findByOfficeNumber(officeNumber);
        if (!officeOpt.isPresent()) {
            return "The office with that office number does not exist";
        }
        Office office = officeOpt.get();
       if (doctorRepo.existsByOffice(office)) {
    return "That office is taken by other doctor";
        }
        Doctor doctor = doctorOpt.get();
        doctor.setOffice(office);
        doctorRepo.save(doctor);
        return "Office changed successfully";
    }

    public List<Appointment> getDoctorAgenda(UUID doctorId, LocalDate date) {
        if (!doctorRepo.existsById(doctorId)) {
            return null;
        }
        return appointmentRepo.findByDoctorIdAndAppointmentDateAndStatusNot(doctorId, date, AppointmentStatus.CANCELLED);
    }

    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepo.searchByName(name);
    }

    public List<Object[]> getDoctorWorkload(LocalDate start, LocalDate end) {
        return appointmentRepo.findDoctorWorkload(start, end, AppointmentStatus.COMPLETED);
    }
}
