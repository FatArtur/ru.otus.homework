package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.service.DBServiceClient;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final DBServiceClient serviceClient;

    @GetMapping
    public ModelAndView getIndex(ModelAndView model) {
        var clients = serviceClient.findAll();
        model.getModel().put("clients", clients);
        model.setViewName("clients");
        return model;
    }

    @PostMapping
    public String saveClient(@ModelAttribute ClientDto client) {
        serviceClient.saveClient(client);
        return "redirect:/clients";
    }
}
