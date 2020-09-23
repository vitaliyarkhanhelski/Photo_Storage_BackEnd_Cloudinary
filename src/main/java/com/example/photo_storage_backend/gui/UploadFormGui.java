package com.example.photo_storage_backend.gui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.photo_storage_backend.service.ImageService;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import java.io.InputStream;


@Route("uploadForm")
public class UploadFormGui extends VerticalLayout {

    private ImageService imageService;

    @Autowired
    public UploadFormGui(ImageService imageService) {
        this.imageService = imageService;

        add(new Label("Upload Photo to Storage and scales to 800px width (only .jpeg, .jpg, .gif or .png):"));

        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload upload = new Upload(memoryBuffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/jpg", "image/gif", "image/png");
        upload.addSucceededListener(e -> {
            InputStream inputStream = memoryBuffer.getInputStream();
            imageService.uploadFromForm(inputStream, e.getFileName());

        });
        add(upload);
        add(new Anchor("", "Go Home"));
    }
}
