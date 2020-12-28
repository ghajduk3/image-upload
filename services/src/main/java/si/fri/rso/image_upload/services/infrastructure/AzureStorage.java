package si.fri.rso.image_upload.services.infrastructure;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

//import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import si.fri.rso.image_upload.services.config.AppProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@ApplicationScoped
public class AzureStorage {

    @Inject
    private AppProperties config;


    @PostConstruct
    public void AfterCreate(){
        System.out.println("AzureStorage instance is created");
    }

    public BlobContainerClient getContainerClient(){
        String endpoint = "https://" + config.getAccountName() + ".blob.core.windows.net";

        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(config.getAccountName(), config.getAccountKey());
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(endpoint)
                .credential(credential)
                .buildClient();
        return blobServiceClient.getBlobContainerClient(config.getContainerName());

    }
    public String generateSAStoken(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -2);
        String start = fmt.format(cal.getTime());
        cal.add(Calendar.MONTH, 2);
        String expiry =  fmt.format(cal.getTime());
        String StorageAccountName = config.getAccountName();
        String StorageAccountKey = config.getAccountKey();
        String apiVersion="2019-07-07";
        String  resource ="sco";
        String permissions ="rwdlac";
        String service = "b";
        String stringToSign = StorageAccountName + "\n" +
                permissions +"\n" +  // signed permissions
                service+"\n" + // signed service
                resource+"\n" + // signed resource type
                start + "\n" + // signed start
                expiry + "\n" + // signed expiry
                "\n" +  // signed IP
                "https\n" + // signed Protocol
                apiVersion+"\n"; // signed version

        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(StorageAccountKey), "HmacSHA256");
        Mac sha256HMAC = null;
        String signature= null;
        String sasToken = null;
        try {
            sha256HMAC = Mac.getInstance("HmacSHA256");
            sha256HMAC.init(secretKey);
            signature = Base64.getEncoder().encodeToString(sha256HMAC.doFinal(stringToSign.getBytes("UTF-8")));
            sasToken = "sv=" + apiVersion +
                    "&ss=" + service+
                    "&srt=" + resource+
                    "&sp=" +permissions+
                    "&se=" + URLEncoder.encode(expiry, "UTF-8") +
                    "&st=" + URLEncoder.encode(start, "UTF-8") +
                    "&spr=https" +
                    "&sig=" + URLEncoder.encode(signature,"UTF-8");
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return sasToken;
    }


}
