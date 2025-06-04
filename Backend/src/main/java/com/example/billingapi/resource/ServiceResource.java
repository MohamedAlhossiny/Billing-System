package com.example.billingapi.resource;

import com.example.billingapi.dao.ServiceDAO;
import com.example.billingapi.model.Service;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/service")
public class ServiceResource {

    private ServiceDAO serviceDAO = new ServiceDAO(); // In a real application, use dependency injection

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addService(Service service) {
        serviceDAO.addService(service);
        return Response.status(Response.Status.CREATED).entity(service).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateService(Service service) {
        // You might want to check if the service exists before updating
        serviceDAO.updateService(service);
        return Response.ok(service).build();
    }
} 