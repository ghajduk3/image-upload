package si.fri.rso.image_upload.services.cdi;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import si.fri.rso.image_upload.model.dto.ImageDTO;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


import si.fri.rso.image_upload.model.entities.ImageEntity;
import si.fri.rso.image_upload.model.transformers.ImageConverter;
import si.fri.rso.image_upload.services.dao.ImageDAO;
import si.fri.rso.image_upload.services.infrastructure.AzureStorage;

@RequestScoped
public class ImageBean {

    @Inject
    AzureStorage azureStorage;
    @Inject @ConfigProperty(name="AZURE_ACCOUNT_NAME")
    private String accountName;

    @Inject @ConfigProperty(name="AZURE_CONTAINER_NAME")
    private String containerName;

    @Inject
    private ImageDAO imageDAO;

    @Inject
    private ImageConverter imageConverter;


    public ImageDTO uploadImageToAzure(InputStream fileStream, String filename, Long filesize) {
        BlobContainerClient containerClient = azureStorage.getContainerClient();
        BlobClient blobClient = containerClient.getBlobClient(filename);
        ImageEntity entity = new ImageEntity();
        try {
            blobClient.upload(fileStream, filesize, true);
            String imageUri = generateBlobUrl(filename,azureStorage.generateSAStoken());
            entity = imageDAO.createNew(imageConverter.transformToEntity(new ImageDTO(filename,filesize,imageUri)));
            return  imageConverter.transformToDTO(entity);

        } catch (Exception ex) {
            System.out.printf("Greska je : %s", ex.toString());
        }

        if(entity == null){
            throw new InternalError();
        }else{
            return imageConverter.transformToDTO(entity);
        }

    }

    private String generateBlobUrl(String blobName, String sasToken) {
        String blobUrl = "https://" + this.accountName + ".blob.core.windows.net/" + this.containerName + "/" + blobName;
        return blobUrl + "?" + sasToken;


    }
}