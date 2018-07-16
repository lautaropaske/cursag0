package resources;





import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
@Path("/image")
public class ImageResource {

    @GET
    @PermitAll
    @Produces({"image/png","images/jpg"})
    @Path("/{courseID}")
    public Response getImage(@PathParam("courseID") int id) throws IOException {
        File file = new File("/Users/agustinbettati/projects/cursago/resources/images/"+id+".png");
        if(file.exists()) {
            BufferedImage image = ImageIO.read(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageData = baos.toByteArray();

            return Response.ok(imageData).build();
        }
        else {
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/upload/{courseID}")
    @RolesAllowed("USER")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadImage(@FormDataParam("image") InputStream imageInputStream,
                                @FormDataParam("image") FormDataContentDisposition fileDetail,
                                @PathParam("courseID") int id) throws Exception{

        File dest = new File("/Users/agustinbettati/projects/cursago/resources/images/" + id +".png");
        // write the inputStream to a FileOutputStream
        OutputStream outputStream =
                new FileOutputStream(dest);

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = imageInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        return Response.ok("Image upload was successful").build();
    }


}
