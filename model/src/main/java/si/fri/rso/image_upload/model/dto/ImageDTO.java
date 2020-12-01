package si.fri.rso.image_upload.model.dto;

import java.io.Serializable;

import java.util.Date;

public class ImageDTO implements Serializable {
    private String title;
    private String description;
    private long size;
    private Date created;
    private int height;
    private int width;
    private String blob_uri;
    private String thumbnail_blob_uri;

    public ImageDTO(){

    }

    public ImageDTO(String title, String description, long size, Date created, String blob_uri) {
        this.title = title;
        this.description = description;
        this.size = size;
        this.created = created;
        this.blob_uri = blob_uri;
    }

    public ImageDTO(String title, String description, long size, Date created, int height, int width, String blob_uri, String thumbnail_blob_uri) {
        this.title = title;
        this.description = description;
        this.size = size;
        this.created = created;
        this.height = height;
        this.width = width;
        this.blob_uri = blob_uri;
        this.thumbnail_blob_uri = thumbnail_blob_uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getBlob_uri() {
        return blob_uri;
    }

    public void setBlob_uri(String blob_uri) {
        this.blob_uri = blob_uri;
    }

    public String getThumbnail_blob_uri() {
        return thumbnail_blob_uri;
    }

    public void setThumbnail_blob_uri(String thumbnail_blob_uri) {
        this.thumbnail_blob_uri = thumbnail_blob_uri;
    }
}
