package com.example.photo_storage_backend.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.photo_storage_backend.service.ImageService;

@Route("upload")
public class UploadGui extends VerticalLayout {

    private ImageService imageService;

    @Autowired
    public UploadGui(ImageService imageService) {
        this.imageService = imageService;

        Label label = new Label();
        Label label2 = new Label("Upload to Storage and scales to 800px width, provide photo path (only .jpeg, .jpg, .gif or .png):");
        TextField textField = new TextField();
        textField.setWidth("450px");
        Button button = new Button("upload");

        button.addClickListener(buttonClickEvent -> {
            if (textField.getValue().endsWith(".jpg") || textField.getValue().endsWith(".jpeg") ||
                    textField.getValue().endsWith(".gif") || textField.getValue().endsWith(".png")) {
                String uploadImage = imageService.uploadImageAndSaveToDb(textField.getValue());
                Image image = new Image(uploadImage, "brak");
                label.setText("Uploaded successfully");
                add(label);
                add(image);
            } else {
                label.setText("You can upload only .jpeg, .jpg, .gif or .png");
                add(label);
            }
        });
        add(label2);
        add(textField);
        add(button);
        Anchor home = new Anchor("","Go Home");
        add(home);
    }
}
