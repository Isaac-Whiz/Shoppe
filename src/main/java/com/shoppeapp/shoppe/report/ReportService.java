package com.shoppeapp.shoppe.report;

import com.shoppeapp.shoppe.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private static UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        ReportService.userRepository = userRepository;
    }

    public static long findUserNo() {
        return userRepository.findAll().size();
    }
}
