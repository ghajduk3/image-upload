package si.fri.rso.image_upload.services.infrastructure;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

//import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AzureStorage {

    @Inject @ConfigProperty(name="AZURE_ACCOUNT_NAME")
    private String accountName;
    @Inject @ConfigProperty(name="AZURE_ACCOUNT_KEY")
    private String accountKey;
    @Inject @ConfigProperty(name="AZURE_CONTAINER_NAME")
    private String containerName;


    @PostConstruct
    public void AfterCreate(){
        System.out.println("AzureStorage instance is created");
    }

    public BlobContainerClient getContainerClient(){
        String endpoint = "https://" + this.accountName + ".blob.core.windows.net";

        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(endpoint)
                .credential(credential)
                .buildClient();
        return blobServiceClient.getBlobContainerClient(containerName);

    }


}
