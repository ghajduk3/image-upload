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


import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import si.fri.rso.image_upload.model.dto.ImageDTO;
import si.fri.rso.image_upload.services.cdi.ImageBean;


@Path("/upload")
@RequestScoped
public class UploadResource {

    @Inject
    private ImageBean imageBean;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@FormDataParam("file") File inputFile, @FormDataParam("file") FormDataContentDisposition fileMetadata,@FormDataParam("description") String description) {
        try {
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(inputFile));
            ImageDTO imageMetadata = imageBean.uploadImageToAzure(fileInputStream, fileMetadata.getFileName(), inputFile.length(),description);
            return Response.ok(imageMetadata).build();
        } catch (FileNotFoundException e) {
            return Response.status(404).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }


}

//    @POST
//    @Path("/test")
//    public Response testPost(@FormDataParam("fajl") File fileInput , @FormDataParam("fajl") FormDataContentDisposition fileMetadata) throws FileNotFoundException {
////        byte[] Buffer = new byte[fileInputStream.available()];
//
//        imageBean.uploadBlobToAzure(new BufferedInputStream(new FileInputStream(fileInput)), fileMetadata.getFileName(), fileInput.length());
//        return Response.status(Response.Status.OK).entity("gojko").build();
//
//    }

//    @GET
//    public Response getUpload(){
//        return Response.status(Response.Status.OK).entity("{\n" +
//                "    \"clani\": [\"gh8590\", \"mn3322\"],\n" +
//                "    \"opis_projekta\": \"Nas projekt implementira aplikacijo za oddajo nepremicnin.\",\n" +
//                "    \"mikrostoritve\": [\"http://35.189.96.118:8081/v1/orders\", \"http://35.197.209.159:8080/v1/customers/\"],\n" +
//                "    \"github\": [\"https://github.com/jmezna/rso-customers\", \"https://github.com/jmezna/rso-orders\"],\n" +
//                "    \"travis\": [\"https://travis-ci.org/jmezna/rso-customers\", \"https://travis-ci.org/jmezna/rso-orders\"],\n" +
//                "    \"dockerhub\": [\"https://hub.docker.com/r/jmezna/rso-customers/\", \"https://hub.docker.com/r/jmezna/rso-orders/\"]\n" +
//                "}").build();
//    }
//    @GET
//    @Path("/image")
//    public Response getTestUpload(){
//        imageBean.uploadBlobToAzure();
//    // imageBean.uploadBlobToAzure("DefaultEndpointsProtocol=https;AccountName=gojkohajdukovicstorage;AccountKey=+1QLyBX0JXk3Kim7D7XzKmAxSQIxJ1u5auakuzfl3N0ClUC1tcgnCn0zCFSkT6MkpEUCYAMZrnxXbpYT3f+7UA==;EndpointSuffix=core.windows.net","rso-images");
//        return Response.status(Response.Status.OK).entity("accName").build();
//
//    }









