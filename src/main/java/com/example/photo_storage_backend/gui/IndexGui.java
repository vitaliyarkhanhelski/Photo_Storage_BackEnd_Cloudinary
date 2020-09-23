package com.example.photo_storage_backend.gui;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.photo_storage_backend.repository.ImageRepository;

import javax.servlet.http.HttpServletRequest;

@Route("")
public class IndexGui extends VerticalLayout {

    private ImageRepository imageRepository;

    @Autowired
    public IndexGui(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        Span span = new Span("You are not authorised, but You still can watch Gallery and api");
        add(span);

        Div content = new Div();
        content.getElement().setProperty("innerHTML", "<p><b>User</b> can add photos</p>" +
                "<span><b>Admin</b> can add and delete photos</span>");

        add(content, new Hr());

        VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
        HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest).getHttpServletRequest();
        String requestUrl = httpServletRequest.getRequestURL().toString();

        Label label4 = new Label("Address is " + requestUrl);
        add(label4);

        Anchor anchor = new Anchor("api", "API");
        add(anchor);
        Anchor upload = new Anchor("upload", "Upload with Path");
        add(upload);
        Anchor uploadForm = new Anchor("uploadForm", "Upload with Form");
        add(uploadForm);
        Anchor delete = new Anchor("gallery", "Gallery");
        add(delete);
        Anchor logout = new Anchor("logout", "Logout");
        add(logout);

        imageRepository.findAll()
                .stream()
                .forEach(e -> add(new Image(e.getImageUrl(), "brak")));
    }
}