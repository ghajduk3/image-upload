package si.fri.rso.image_upload.model.transformers;

import si.fri.rso.image_upload.model.dto.ImageDTO;
import si.fri.rso.image_upload.model.entities.ImageEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class ImageConverter extends GenericConverter<ImageEntity, ImageDTO> {

    @Override
    public ImageDTO transformToDTO(ImageEntity entity){
        ImageDTO dto = new ImageDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage_size(Long.valueOf(entity.getImage_size()));
        dto.setImage_uri(entity.getImage_uri());
        dto.setFileInputStream(null);
        dto.setFileLength(null);

        return dto;
    }

    @Override
    public List<ImageDTO> transformToDTO(List<ImageEntity> entities){
        List<ImageDTO> dtos = new ArrayList<>();
        for(ImageEntity entity : entities){
            dtos.add(transformToDTO(entity));
        }
        return dtos;
    }

    @Override
    public ImageEntity transformToEntity(ImageDTO dto){
        ImageEntity entity = new ImageEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImage_uri(dto.getImage_uri());
        entity.setImage_size(dto.getImage_size().intValue());

        return entity;
    }

    @Override
    public List<ImageEntity> transformToEntity(List<ImageDTO> dtos){
        List<ImageEntity> entities = new ArrayList<>();

        for(ImageDTO dto:dtos){
            entities.add(transformToEntity(dto));
        }
        return entities;
    }
}
