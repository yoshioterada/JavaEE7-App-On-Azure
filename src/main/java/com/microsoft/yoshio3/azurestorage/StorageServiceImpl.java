/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azurestorage;

import javax.enterprise.context.Dependent;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Yoshio Terada
 */
@Dependent
public class StorageServiceImpl implements Serializable{

    private static final Logger logger = Logger.getLogger(StorageServiceImpl.class.getName());

    // Azure Storage サービスに接続するためのキー
    private final static String storageConnectionString
            = "DefaultEndpointsProtocol=https;"
            + "AccountName=yoshio3storage;"
            + "AccountKey=gtBNpjvTzaAXhib2MTpPw+o3AR0HI+rvCxE51NA1Cv9FPJYmH+GkNjMKraK7DP6TsH8Iu05PmBNCpfLJdykczA==";

    private CloudBlobClient blobClient;

    //初期化
    @PostConstruct
    public void init() {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
        } catch (URISyntaxException | InvalidKeyException ex) {
            logger.log(Level.SEVERE, "Invalid Account", ex);
        }
    }

    //ディレクトリ(コンテナ)の新規作成
    public void createContainer(String containerName) {
        try {
            String lowercase = containerName.toLowerCase(); //if it include Uppercase 400 error
            CloudBlobContainer container = blobClient.getContainerReference(lowercase);
            container.createIfNotExists();

            BlobContainerPermissions permissions = new BlobContainerPermissions();
            permissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
            container.uploadPermissions(permissions);
        } catch (URISyntaxException ex) {
            logger.log(Level.SEVERE, "Invalid URISyntax", ex);
        } catch (StorageException ste) {
            logger.log(Level.SEVERE, "Invalid Strage type", ste);
        }
    }

    //ファイルのアップロード
    public void uploadFile(String containerName, UploadedFile file) {
        CloudBlobContainer container;
        try {
            container = blobClient.getContainerReference(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(file.getFileName());

            blob.upload(file.getInputstream(), file.getSize());
        } catch (URISyntaxException | StorageException ex) {
            Logger.getLogger(StorageServiceImpl.class.getName()).log(Level.SEVERE, "", ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    //フィアルの一覧取得
    public List<BlobEntity> getAllFiles(String containerName) {
        List<BlobEntity> entity = new ArrayList<>();
        try {
            CloudBlobContainer container = blobClient.getContainerReference(containerName);

            Iterable<ListBlobItem> items = container.listBlobs();
            Spliterator<ListBlobItem> spliterator = items.spliterator();
            Stream<ListBlobItem> stream = StreamSupport.stream(spliterator, false);

            List<CloudBlob> blockBlob = stream
                    .filter(item -> item instanceof CloudBlob)
                    .map(item -> (CloudBlob) item)
                    .collect(Collectors.toList());

            entity = blockBlob.stream().map(blob -> convertEntity(blob)).collect(Collectors.toList());
        } catch (URISyntaxException | StorageException ex) {
            logger.log(Level.SEVERE, "", ex);
        }
        return entity;
    }

    //表示項目出力用のビーンに変換
    private BlobEntity convertEntity(CloudBlob blob) {
        BlobEntity entity = new BlobEntity();
        try {

            BlobProperties properties = blob.getProperties();

            entity.setLastModifyDate(properties.getLastModified());
            entity.setName(blob.getName());
            entity.setSize(properties.getLength());
            entity.setURI(blob.getUri().toString());

        } catch (URISyntaxException ex) {
            Logger.getLogger(StorageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entity;
    }

    public void deleteAll(String containerName) {
        try {
            CloudBlobContainer container = blobClient.getContainerReference(containerName);
            Iterable<ListBlobItem> items = container.listBlobs();
            items.forEach((ListBlobItem item) -> {
                if (item instanceof CloudBlob) {
                    try {
                        CloudBlob blob = (CloudBlob) item;
                        String name = blob.getName();
                        
                        CloudBlockBlob delFile;
                        delFile = container.getBlockBlobReference(name);
                        // Delete the blob.
                        delFile.deleteIfExists();
                    } catch (URISyntaxException | StorageException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }

                }
            });

        } catch (URISyntaxException | StorageException ex) {
            Logger.getLogger(StorageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
