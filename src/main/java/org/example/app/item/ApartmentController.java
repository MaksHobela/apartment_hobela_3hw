package org.example.app.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;

@Controller
@RequestMapping("/apartment")
public class ApartmentController {

    private final ApartmentRepository repository;

    public ApartmentController(ApartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String list(Model model) {
        var apartments = repository.findAll();
        model.addAttribute("apartments", apartments);

        // Price summary
        double avg = 0, max = 0, min = Double.MAX_VALUE;
        int count = 0;
        for (Apartment a : apartments) {
            double p = a.pricepernight().doubleValue();
            avg += p;
            if (p > max) max = p;
            if (p < min) min = p;
            count++;
        }
        if (count == 0) { avg = 0; min = 0; max = 0; }
        else avg /= count;

        model.addAttribute("avgPrice", avg);
        model.addAttribute("maxPrice", max);
        model.addAttribute("minPrice", min == Double.MAX_VALUE ? 0 : min);

        return "item/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Add Apartment");
        model.addAttribute("formAction", "/apartment/create");
        model.addAttribute("submitLabel", "Create");
        model.addAttribute("apartment", new ApartmentForm());
        return "item/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ApartmentForm form, RedirectAttributes ra) {
        repository.save(Apartment.of(
            form.getName(), form.getDescription(),
            form.getPrice(), form.getPropertyType(),
            form.getPostalCode(), form.getHostId(), form.getAdminId()
        ));
        ra.addFlashAttribute("successMessage", "Apartment created successfully!");
        return "redirect:/apartment";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var apt = repository.findById(id).orElseThrow();
        ApartmentForm form = new ApartmentForm();
        form.setName(apt.apartmentname());
        form.setDescription(apt.description());
        form.setPrice(apt.pricepernight());
        form.setPropertyType(apt.propertytype());
        form.setPostalCode(apt.postalcode());
        form.setHostId(apt.hostid());
        form.setAdminId(apt.admin_id());

        model.addAttribute("pageTitle", "Edit Apartment");
        model.addAttribute("formAction", "/apartment/" + id + "/edit");
        model.addAttribute("submitLabel", "Save Changes");
        model.addAttribute("apartment", form);
        return "item/form";
    }

    // HANDLE EDIT
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute ApartmentForm form, RedirectAttributes ra) {
        var existing = repository.findById(id).orElseThrow();
        var updated = new Apartment(
            id,
            form.getName(),
            form.getDescription(),
            form.getPrice(),
            form.getPropertyType(),
            existing.dateofReg(),
            form.getPostalCode(),
            form.getHostId(),
            form.getAdminId()
        );
        repository.save(updated);
        ra.addFlashAttribute("successMessage", "Apartment updated successfully!");
        return "redirect:/apartment";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        repository.deleteById(id);
        ra.addFlashAttribute("successMessage", "Apartment deleted.");
        return "redirect:/apartment";
    }
}