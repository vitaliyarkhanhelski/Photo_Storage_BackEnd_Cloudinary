package com.example.photo_storage_backend.gui;

import com.example.photo_storage_backend.repository.ImageRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.example.photo_storage_backend.service.ImageService;

@Route("gallery")
public class GalleryGui extends VerticalLayout {

    private ImageRepository imageRepository;
    private ImageService imageService;

    public GalleryGui(ImageRepository imageRepository, ImageService imageService) {
        this.imageRepository = imageRepository;
        this.imageService = imageService;

        Anchor home = new Anchor("","Go Home");
        add(home);

        imageRepository.findAll()
                .stream()
                .forEach(e -> {
                    Image image = new Image(e.getImageUrl(), "brak");
                    add(image);
                    Button button = new Button("Delete");
                    button.addClickListener(buttonClickEvent -> {
                        imageService.deleteImageAndDeleteFromDb(e.getPublicId());
                        UI.getCurrent().getPage().reload();
                    });
                    add(button);
                });
    }
}
