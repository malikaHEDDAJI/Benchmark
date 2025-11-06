package com.benchmark.rest.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class HomeResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String home() {
        return "API is running!";
    }
}
