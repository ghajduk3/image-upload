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


import si.fri.rso.image_upload.services.config.AppProperties;
import si.fri.rso.image_upload.services.infrastructure.AzureStorage;

@RequestScoped
public class ImageBean {

    @Inject
    AzureStorage azureStorage;
    @Inject
    private AppProperties config;



    public ImageDTO uploadImageToAzure(InputStream fileStream, String filename, Long filesize) {
        BlobContainerClient containerClient = azureStorage.getContainerClient();
        BlobClient blobClient = containerClient.getBlobClient(filename);
        String imageUri = null;
        try {
            blobClient.upload(fileStream, filesize, true);
            imageUri = generateBlobUrl(filename,azureStorage.generateSAStoken());
            System.out.println(imageUri);

        } catch (Exception ex) {
            System.out.printf("Greska je : %s", ex.toString());
        }
        System.out.println(imageUri);
        return new ImageDTO(filename,filesize,imageUri);

    }

    private String generateBlobUrl(String blobName, String sasToken) {
        String blobUrl = "https://" + config.getAccountName() + ".blob.core.windows.net/" + config.getContainerName() + "/" + blobName;
        return blobUrl + "?" + sasToken;


    }
}