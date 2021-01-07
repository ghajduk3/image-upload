
package si.fri.rso.image_upload.api.v1;
import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

@RegisterService(value="image-upload", ttl = 20, pingInterval = 15, version = "1.0.0", singleton = false)
@ApplicationPath("/v1")
public class UploadApplication extends Application{

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.server.provider.classnames",
                "org.glassfish.jersey.media.multipart.MultiPartFeature");
        return props;
    }



}