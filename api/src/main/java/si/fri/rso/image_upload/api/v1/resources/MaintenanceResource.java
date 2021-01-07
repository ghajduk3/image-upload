package si.fri.rso.image_upload.api.v1.resources;

import si.fri.rso.image_upload.services.config.RestProperties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/maintenance")
@RequestScoped
public class MaintenanceResource {

    @Inject
    private RestProperties restProperties;

    @POST
    @Path("/break")
    public Response breakResource() {
        restProperties.setMaintenanceMode(true);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/recover")
    public Response recoverResource() {
        restProperties.setMaintenanceMode(false);
        return Response.status(Response.Status.OK).build();
    }
}