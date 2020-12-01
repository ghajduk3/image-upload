package si.fri.rso.image_upload.services.cdi;
import si.fri.rso.image_upload.model.dto.ImageDTO;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import java.io.*;
import java.util.Date;



import si.fri.rso.image_upload.services.infrastructure.AzureStorage;

@RequestScoped
public class ImageBean{

    @Inject
    AzureStorage azureStorage;

    public ImageDTO uploadImageToAzure(InputStream fileStream,String filename,Long filesize,String description){
        BlobContainerClient containerClient = azureStorage.getContainerClient();
        BlobClient blobClient = containerClient.getBlobClient(filename);
        try {
            blobClient.upload(fileStream,filesize,true);
        }
        catch (Exception ex){
            System.out.printf("Greska je : %s",ex.toString());
        }
        return new ImageDTO(filename,description,filesize,new Date(),"default_url");
    }



}
