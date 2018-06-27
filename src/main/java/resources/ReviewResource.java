package resources;

import model.Review;
import services.ReviewService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/review")
public class ReviewResource {

    private ReviewService service;

    public ReviewResource(){
        service = new ReviewService();
    }

    @GET
    @PermitAll
    @Path("/{reviewID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Review getReview(@PathParam("reviewID") int id){
        return service.getReview(id);
    }


//    http://localhost:8080/review?courseId=14
    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<Review> getReviewsOfCourse(@QueryParam("courseId") int courseId){
        return service.getReviewsOfCourse(courseId);
    }

    @POST
    @RolesAllowed("USER")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Review registerReview(Review review){
        return service.registerReview(review);
    }

    @PUT
    @RolesAllowed("USER")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Review updateReview(Review review) {
        return service.updateReview(review);
    }

    @DELETE
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{reviewID}")
    public void deleteReview(@PathParam("reviewID") int id) {
        service.deleteReview(id);
    }
}
