package com.benchmark.rest.resource;

import com.benchmark.rest.dao.CategoryDAO;
import com.benchmark.rest.model.Category;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("benchmarkPU");
    private CategoryDAO categoryDAO = new CategoryDAO(emf);

    @GET
    public List<Category> list(@QueryParam("page") @DefaultValue("0") int page,
                               @QueryParam("size") @DefaultValue("50") int size) {
        return categoryDAO.findAll(page, size);
    }

    @GET @Path("/{id}")
    public Category get(@PathParam("id") Long id) {
        return categoryDAO.find(id);
    }

    @POST
    public Response create(Category category) {
        categoryDAO.save(category);
        return Response.status(Response.Status.CREATED).entity(category).build();
    }

    @PUT @Path("/{id}")
    public Category update(@PathParam("id") Long id, Category category) {
        Category existing = categoryDAO.find(id);
        if (existing == null) throw new WebApplicationException("Not found", 404);

        existing.setName(category.getName());
        existing.setCode(category.getCode());
        return categoryDAO.update(existing);
    }

    @DELETE @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Category existing = categoryDAO.find(id);
        if (existing == null) throw new WebApplicationException("Not found", 404);

        categoryDAO.delete(existing);
        return Response.noContent().build();
    }
}
