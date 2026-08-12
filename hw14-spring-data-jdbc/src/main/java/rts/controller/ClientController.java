package rts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rts.model.Address;
import rts.model.Client;
import rts.model.Phone;
import rts.service.ClientService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/clients")
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public String clients(Model model) {
        model.addAttribute(
                "clients",
                clientService.findAll()
        );

        return "clients";
    }

    @GetMapping("/{id}")
    public String client(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute(
                "client",
                clientService.findById(id)
        );

        return "client";
    }

    @GetMapping("/new")
    public String newClient(Model model) {

        model.addAttribute(
                "client",
                new Client()
        );

        return "client-form";
    }

    @PostMapping
    public String create(
            @RequestParam String name,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String phone
    ) {

        Client client = new Client(name);

        if (street != null && !street.isBlank()) {
            client.setAddress(
                    new Address(null, street)
            );
        }

        if (phone != null && !phone.isBlank()) {
            client.addPhone(
                    new Phone(null, phone)
            );
        }

        clientService.save(client);

        return "redirect:/admin/clients";
    }

    @GetMapping("/{id}/edit")
    public String editClient(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "client",
                clientService.findEntityById(id)
        );

        return "client-form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String phone
    ) {

        Client client = clientService.findEntityById(id);

        client.setName(name);

        if (street != null && !street.isBlank()) {
            client.setAddress(
                    new Address(null, street)
            );
        } else {
            client.setAddress(null);
        }

        client.setPhones(
                phone == null || phone.isBlank()
                        ? new java.util.ArrayList<>()
                        : new java.util.ArrayList<>(
                        java.util.List.of(
                                new Phone(null, phone)
                        )
                )
        );

        clientService.save(client);

        return "redirect:/admin/clients";
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id
    ) {

        clientService.delete(id);

        return "redirect:/admin/clients";
    }
}