package com.example.billingapi.resource;

import com.example.billingapi.dao.RatePlanDAO;
import com.example.billingapi.dao.ServicePackageDAO;
import com.example.billingapi.dao.ServiceDAO;
import com.example.billingapi.model.RatePlan;
import com.example.billingapi.model.ServicePackage;
import com.example.billingapi.model.Service;
import com.example.billingapi.dto.RatePlanDetails;
import com.example.billingapi.dto.ServicePackageDetails;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/current-assignment")
public class CurrentAssignmentResource {

    private RatePlanDAO ratePlanDAO = new RatePlanDAO(); // In a real application, use dependency injection
    private ServicePackageDAO servicePackageDAO = new ServicePackageDAO(); // In a real application, use dependency injection
    private ServiceDAO serviceDAO = new ServiceDAO(); // In a real application, use dependency injection

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RatePlanDetails> getCurrentAssignments() {
        List<RatePlanDetails> ratePlanDetailsList = new ArrayList<>();

        List<RatePlan> ratePlans = ratePlanDAO.getAllRatePlans();

        for (RatePlan ratePlan : ratePlans) {
            List<ServicePackageDetails> servicePackageDetailsList = new ArrayList<>();
            List<ServicePackage> servicePackages = servicePackageDAO.getServicePackagesByRatePlanId(ratePlan.getRateplanId());

            for (ServicePackage servicePackage : servicePackages) {
                List<Service> services = serviceDAO.getServicesByPackageId(servicePackage.getPackageId());
                ServicePackageDetails servicePackageDetails = new ServicePackageDetails(servicePackage, services);
                servicePackageDetailsList.add(servicePackageDetails);
            }

            RatePlanDetails ratePlanDetails = new RatePlanDetails(ratePlan, servicePackageDetailsList);
            ratePlanDetailsList.add(ratePlanDetails);
        }

        return ratePlanDetailsList;
    }
} 