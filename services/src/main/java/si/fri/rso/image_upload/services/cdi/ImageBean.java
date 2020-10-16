package si.fri.rso.image_upload.services.cdi;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;

@RequestScoped
public class ImageBean{

    @PersistenceContext(unitName = "image-upload")
    private EntityManager em;


}