package com.benchmark.rest.resource;

import com.benchmark.rest.dao.ItemDAO;
import com.benchmark.rest.dao.CategoryDAO;
import com.benchmark.rest.model.Item;
import com.benchmark.rest.model.Category;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("benchmarkPU");
    private ItemDAO itemDAO = new ItemDAO(emf);
    private CategoryDAO categoryDAO = new CategoryDAO(emf);

    @GET
    public List<Item> list(@QueryParam("page") @DefaultValue("0") int page,
                           @QueryParam("size") @DefaultValue("50") int size,
                           @QueryParam("categoryId") Long categoryId) {
        if (categoryId != null) {
            return itemDAO.findByCategoryId(categoryId, page, size);
        } else {
            return itemDAO.findAll(page, size);
        }
    }

    @GET @Path("/{id}")
    public Item get(@PathParam("id") Long id) {
        return itemDAO.find(id);
    }

    @POST
    public Response create(Item item) {
        if (item.getCategory() == null || item.getCategory().getId() == null) {
            throw new WebApplicationException("Category required", 400);
        }
        Category cat = categoryDAO.find(item.getCategory().getId());
        if (cat == null) throw new WebApplicationException("Category not found", 400);

        item.setCategory(cat);
        itemDAO.save(item);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    @PUT @Path("/{id}")
    public Item update(@PathParam("id") Long id, Item item) {
        Item existing = itemDAO.find(id);
        if (existing == null) throw new WebApplicationException("Item not found", 404);

        existing.setName(item.getName());
        existing.setSku(item.getSku());
        existing.setPrice(item.getPrice());
        existing.setStock(item.getStock());
        existing.setUpdatedAt(java.time.LocalDateTime.now());

        if (item.getCategory() != null && item.getCategory().getId() != null) {
            Category cat = categoryDAO.find(item.getCategory().getId());
            if (cat != null) existing.setCategory(cat);
        }

        return itemDAO.update(existing);
    }

    @DELETE @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Item existing = itemDAO.find(id);
        if (existing == null) throw new WebApplicationException("Item not found", 404);

        itemDAO.delete(existing);
        return Response.noContent().build();
    }
}
