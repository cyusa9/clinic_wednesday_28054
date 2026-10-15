package kigali.clinic.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Prescription;
import kigali.clinic.rw.repository.AppointmentRepository;
import kigali.clinic.rw.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    public String savePrescription(Prescription prescription) {
        if (prescription.getAppointment() == null || prescription.getAppointment().getId() == null) {
            return "The appointment with that id does not exist";
        }
        Optional<Appointment> appointmentOpt = appointmentRepo.findById(prescription.getAppointment().getId());
        if (!appointmentOpt.isPresent()) {
            return "The appointment with that id does not exist";
        }
        Appointment appointment = appointmentOpt.get();
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            return "Prescriptions are only for COMPLETED appointments";
        }
        prescription.setAppointment(appointment);
        prescription.setIssuedDate(LocalDate.now());
        prescriptionRepo.save(prescription);
        return "Prescription saved successfully";
    }

    public List<Prescription> getPrescriptionsByAppointment(UUID appointmentId) {
        if (!appointmentRepo.existsById(appointmentId)) {
            return null;
        }
        return prescriptionRepo.findByAppointmentId(appointmentId);
    }

    public List<Prescription> getPrescriptionsByPatientSsNumber(String ssNumber) {
        return prescriptionRepo.findHistoryByPatientSsNumber(ssNumber);
    }

    public String updatePrescription(UUID id, Prescription payload) {
        Optional<Prescription> prescriptionOpt = prescriptionRepo.findById(id);
        if (!prescriptionOpt.isPresent()) {
            return "Prescription not found";
        }
        Prescription prescription = prescriptionOpt.get();
        prescription.setMedicineName(payload.getMedicineName());
        prescription.setDosage(payload.getDosage());
        prescription.setDurationDays(payload.getDurationDays());
        prescriptionRepo.save(prescription);
        return "Prescription updated successfully";
    }

    public String deletePrescription(UUID id) {
        if (!prescriptionRepo.existsById(id)) {
            return "Prescription not found";
        }
        prescriptionRepo.deleteById(id);
        return "Prescription deleted successfully";
    }

    public List<Object[]> getTopMedicines() {
        return prescriptionRepo.findTopMedicines();
    }
}
