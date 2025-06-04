package com.example.billingapi.resource;

import java.net.URI;
import java.util.List;

import com.example.billingapi.dao.ProfileDAO;
import com.example.billingapi.model.Profile;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/profile")
public class ProfileResource {

    private ProfileDAO profileDAO = new ProfileDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profile> getAllProfiles() {
        return profileDAO.getAllProfiles();
    }

    @GET
    @Path("/{profileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileById(@PathParam("profileId") int profileId) {
        Profile profile = profileDAO.getProfileById(profileId);
        if (profile != null) {
            return Response.ok(profile).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Profile not found").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProfile(Profile profile) {
        profileDAO.addProfile(profile);
        URI uri = UriBuilder.fromResource(ProfileResource.class).path(String.valueOf(profile.getProfileId())).build();
        return Response.created(uri).entity(profile).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(Profile profile) {
        profileDAO.updateProfile(profile);
        return Response.ok(profile).build();
    }

    @DELETE
    @Path("/{profileId}")
    public Response deleteProfile(@PathParam("profileId") int profileId) {
        profileDAO.deleteProfile(profileId);
        return Response.ok().entity("Profile deleted").build();
    }
} 