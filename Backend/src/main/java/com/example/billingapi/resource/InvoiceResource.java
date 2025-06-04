package com.example.billingapi.resource;

import com.example.billingapi.dao.InvoiceDAO;
import com.example.billingapi.model.Invoice;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/invoice")
public class InvoiceResource {

    private InvoiceDAO invoiceDAO = new InvoiceDAO(); // In a real application, use dependency injection

    @GET
    @Path("/{mobileNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvoicesByMobileNumber(@PathParam("mobileNumber") String mobileNumber) {
        List<Invoice> invoices = invoiceDAO.getInvoicesByMobileNumber(mobileNumber);
        if (!invoices.isEmpty()) {
            return Response.ok(invoices).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No invoices found for this mobile number").build();
        }
    }
} 