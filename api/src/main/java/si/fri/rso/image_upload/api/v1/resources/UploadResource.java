package si.fri.rso.image_upload.api.v1.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Base64;
import java.util.UUID;


import com.kumuluz.ee.logs.cdi.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import si.fri.rso.image_upload.model.dto.ImageDTO;
import si.fri.rso.image_upload.services.cdi.ImageBean;

@Log
@Path("/upload")
@RequestScoped
public class UploadResource {

    @Inject
    private ImageBean imageBean;

    @Operation(summary = "Uploads image to cloud storage", tags = {"Event"},
            description = "Uploads file to azure blob storage",
            responses = {
                    @ApiResponse(
                            description = "Image SAS uri",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Server error",
                            responseCode = "500"
                    )
            }
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(ImageDTO dto) {
        try {
            ImageDTO image = imageBean.uploadImageToAzure(new ByteArrayInputStream(Base64.getDecoder().decode(dto.getFileInputStream())), UUID.randomUUID().toString() + ".txt",dto.getFileLength());
            return Response.status(Response.Status.OK).entity(image.getImage_uri()).build();
        } catch (Exception e) {
            e.printStackTrace();
         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }
    }


}









