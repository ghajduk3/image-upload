package si.fri.rso.image_upload.services.dao;

import si.fri.rso.image_upload.model.entities.ImageEntity;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class ImageDAO extends GenericDAO<ImageEntity,Integer>{

    ImageDAO(){
        super(ImageEntity.class);
    }

    @Override
    public List<ImageEntity> findAll(){
        return em.createNamedQuery("Image.findAll",ImageEntity.class).getResultList();
    }

    @Override
    public ImageEntity update(ImageEntity image, Integer id){
        ImageEntity entity = findById(id);

        if(entity == null){
            return null;
        }

        try {
            beginTx();
            image.setId(entity.getId());
            image = em.merge(image);
            commitTx();
        }catch (Exception e) {
                rollbackTx();
        }
        return image;
    }



}
