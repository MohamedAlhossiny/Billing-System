package com.example.billingapi.resource;

import com.example.billingapi.dao.CustomerDAO;
import com.example.billingapi.model.Customer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/customer")
public class CustomerResource {

    private CustomerDAO customerDAO = new CustomerDAO(); // In a real application, use dependency injection

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @GET
    @Path("/{mobileNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerByMobileNumber(@PathParam("mobileNumber") String mobileNumber) {
        Customer customer = customerDAO.getCustomerByMobileNumber(mobileNumber);
        if (customer != null) {
            return Response.ok(customer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(Customer customer) {
        // You might want to check if the customer exists before updating
        customerDAO.updateCustomer(customer);
        return Response.ok(customer).build();
    }

    @DELETE
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("customerId") int customerId) {
        // You might want to check if the customer exists before deleting
        customerDAO.deleteCustomer(customerId);
        return Response.ok().entity("Customer deleted").build();
    }
} 