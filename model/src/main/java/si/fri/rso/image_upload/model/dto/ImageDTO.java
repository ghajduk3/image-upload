package si.fri.rso.image_upload.model.dto;

import javax.persistence.Column;
import java.io.BufferedInputStream;
import java.io.Serializable;

import java.util.Date;

public class ImageDTO implements Serializable {

    private Integer id;
    private String name;
    private Date dateCreated = new Date();
    private Long image_size;
    private String image_uri;
    private String fileInputStream;
    private Long fileLength;


    public ImageDTO(){

    }

    public ImageDTO(String fileInputStream, Long fileLength) {
        this.fileInputStream = fileInputStream;
        this.fileLength = fileLength;
    }

    public ImageDTO(String name, Long image_size, String image_uri) {
        this.name = name;
        this.image_size = image_size;
        this.image_uri = image_uri;
    }

    public ImageDTO(Integer id, String name, Date dateCreated, Long image_size, String image_uri) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.image_size = image_size;
        this.image_uri = image_uri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getImage_size() {
        return image_size;
    }

    public void setImage_size(Long image_size) {
        this.image_size = image_size;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(String fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public Long getFileLength() {
        return fileLength;
    }

    public void setFileLength(Long fileLength) {
        this.fileLength = fileLength;
    }
}
