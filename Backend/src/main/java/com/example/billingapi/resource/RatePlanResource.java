package com.example.billingapi.resource;

import com.example.billingapi.dao.RatePlanDAO;
import com.example.billingapi.model.RatePlan;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/rateplan")
public class RatePlanResource {

    private RatePlanDAO ratePlanDAO = new RatePlanDAO(); // In a real application, use dependency injection

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRatePlan(RatePlan ratePlan) {
        ratePlanDAO.addRatePlan(ratePlan);
        return Response.status(Response.Status.CREATED).entity(ratePlan).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RatePlan> getAllRatePlans() {
        return ratePlanDAO.getAllRatePlans();
    }
} 