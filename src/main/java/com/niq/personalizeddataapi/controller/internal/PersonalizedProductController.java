package com.niq.personalizeddataapi.controller.internal;

import com.niq.personalizeddataapi.exception.ApplicationException;
import com.niq.personalizeddataapi.service.PersonalizedProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/personalized-product")
@RequiredArgsConstructor
public class PersonalizedProductController {

    private final PersonalizedProductService personalizedProductService;

    @PostMapping("/products")
    public ResponseEntity<String> updateProducts() throws ApplicationException {

        log.debug("Request to update products has been received");
        // TODO: Should be async to avoid blocking the request in case of large data
        personalizedProductService.updateProducts();
        return ResponseEntity.ok("products updated");
    }

    @PostMapping("/shopper-shelf-items")
    public ResponseEntity<String> updateShopperShelfItems() throws ApplicationException {

        log.debug("Request to update shopper shelf items has been received");
        // TODO: Should be async to avoid blocking the request in case of large data
        personalizedProductService.updateShopperShelfItems();
        return ResponseEntity.ok("shelves updated");
    }

}
