package employeeManagement.service;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import employeeManagement.entity.LeaveInformation;
import employeeManagement.repository.LeaveInformationRepo;

@Service
public class LeaveService {

    private final LeaveInformationRepo lRepo;

    public LeaveService(LeaveInformationRepo lRepo) {
        this.lRepo = lRepo;
    }

    // Save or update leave information
    @CachePut(
            value = "leaveInformation",
            key = "#result.id"
    )
    public LeaveInformation saveLeaveDetails(
            LeaveInformation leaveInformation) {

        return lRepo.save(leaveInformation);
    }

    // Get the latest leave information
    //
    // This method is intentionally NOT cached because
    // it returns the latest record.
    public LeaveInformation getLeaveDetails() {

        List<LeaveInformation> leaves =
                (List<LeaveInformation>) lRepo.findAll();

        if (leaves.isEmpty()) {
            return null;
        }

        return leaves.get(leaves.size() - 1);
    }

    // Get leave information by ID
    @Cacheable(
            value = "leaveInformation",
            key = "#id"
    )
    public LeaveInformation getLeaveDetailsById(int id) {

        System.out.println(
                "Fetching leave information from DB: " + id);

        return lRepo.findById(id)
                .orElse(null);
    }
}
