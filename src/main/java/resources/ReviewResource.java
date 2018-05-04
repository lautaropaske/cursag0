package resources;

import model.Review;
import services.ReviewService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class ReviewResource {

    private ReviewService service;

    public ReviewResource(){
        service = new ReviewService();
    }

    @GET
    @Path("/{reviewID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Review getReview(@PathParam("reviewID") int id){
        return service.getReview(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Review registerReview(Review review){
        return service.registerReview(review);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Review updateReview(Review review) {
        return service.updateReview(review);
    }

    @DELETE
    @Path("/{reviewID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteReview(@PathParam("reviewID") int id) {
        service.deleteReview(id);
    }
}
