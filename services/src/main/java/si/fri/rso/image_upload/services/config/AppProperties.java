package si.fri.rso.image_upload.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@ConfigBundle("azure-properties")
public class AppProperties {

    @ConfigValue(value = "azure-account-name",watch = true)
    private String accountName;

    @ConfigValue(value = "azure-account-key",watch = true)
    private String accountKey;

    @ConfigValue(value = "azure-container-name",watch = true)
    private String containerName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
}