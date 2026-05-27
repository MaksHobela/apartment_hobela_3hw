package org.example.app.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apartment/reports")
public class ApartmentReportController {

    private final ApartmentReportRepository reportRepository;

    public ApartmentReportController(ApartmentReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/overview")
    public String overview(Model model) {
        model.addAttribute("apartments", reportRepository.findApartmentsWithLocation());
        return "item/report_overview";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", reportRepository.findBookingsWithDetails());
        return "item/report_bookings";
    }

    @GetMapping("/summary")
    public String summary(Model model) {
        model.addAttribute("summary", reportRepository.findPriceSummaryByType());
        return "item/report_summary";
    }
}