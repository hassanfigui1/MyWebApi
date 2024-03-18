package net.codejava.ws;
import java.net.*;
import java.util.*;

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

@Path("/products")
public class ProductResource {
	private ProductDAO dao = ProductDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> list(){
		return dao.listAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Product product) throws URISyntaxException {
		int newProductId = dao.add(product);
		URI uri = new URI("/products/"+newProductId);
		return Response.created(uri).build();
	}
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Product product = dao.get(id);
		if(product !=null) {
			return Response.ok(product, 
					MediaType.APPLICATION_JSON).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Product product) {
		product.setId(id);
		if(dao.update(product)) {
			return Response.ok().build();
		}
		return Response.notModified().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		if(dao.delete(id)) {
			return Response.ok().build();
		}
		return Response.notModified().build();
	}
	
	
	
	
	
}
