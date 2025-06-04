package com.example.billingapi.resource;

import com.example.billingapi.dao.ServicePackageDAO;
import com.example.billingapi.model.ServicePackage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/servicepackage")
public class ServicePackageResource {

    private ServicePackageDAO servicePackageDAO = new ServicePackageDAO(); // In a real application, use dependency injection

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addServicePackage(ServicePackage servicePackage) {
        servicePackageDAO.addServicePackage(servicePackage);
        return Response.status(Response.Status.CREATED).entity(servicePackage).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateServicePackage(ServicePackage servicePackage) {
        // You might want to check if the service package exists before updating
        servicePackageDAO.updateServicePackage(servicePackage);
        return Response.ok(servicePackage).build();
    }
} 