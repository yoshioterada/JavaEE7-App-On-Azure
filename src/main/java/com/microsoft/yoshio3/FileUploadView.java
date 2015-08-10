package com.microsoft.yoshio3;

import com.microsoft.yoshio3.azurestorage.BlobEntity;
import com.microsoft.yoshio3.azurestorage.StorageServiceImpl;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author tyoshio2002
 */
@ViewScoped
@Named(value = "fileUploadView")
public class FileUploadView implements Serializable {

    private List<UploadedFile> files;
    private List<BlobEntity> entities;

    public List<UploadedFile> getFile() {
        return files;
    }

    public void setFile(List<UploadedFile> files) {
        this.files = files;
    }

    /**
     * @return the entities
     */
    public List<BlobEntity> getEntities() {
        return serviceImpl.getAllFiles("container1");
//        return entities;
    }

    /**
     * @param entities the entities to set
     */
    public void setEntities(List<BlobEntity> entities) {
        this.entities = entities;
    }

    @Inject
    private StorageServiceImpl serviceImpl;

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file != null) {
            serviceImpl.uploadFile("container1", file);
        }

    }

    public void deleteAll() {
        serviceImpl.deleteAll("container1");
    }

}
