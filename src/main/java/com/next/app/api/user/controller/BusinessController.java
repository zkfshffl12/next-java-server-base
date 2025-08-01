package com.next.app.api.user.controller;

import com.next.app.api.user.entity.Business;
import com.next.app.api.user.service.BusinessService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping
    public Business createBusiness(@RequestBody Business business) {
        return businessService.createBusiness(business);
    }

    @GetMapping
    public List<Business> getAllBusinesses() {
        return businessService.getAllBusinesses();
    }

}
