package com.example.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;


@RestController
public class StockController {

  private Stock inventory = new Stock();

  @GetMapping("/addItem")
  public Stock addItem(@RequestParam("item") String item) {
    try {
      inventory.addItem(item);
    } catch (KeyAlreadyExistsException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    }
    return inventory;
  }

  @GetMapping("/setStock")
  public Stock setStock(@RequestParam("item") String item, @RequestParam("stockLevel") int stockLevel) {
    try {
      inventory.setStock(item, stockLevel);
    } catch (InvalidKeyException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    }
    return inventory;
  }

  @GetMapping("/addStock")
  public Stock addStock(@RequestParam("item") String item, @RequestParam("numItem") int numItem) {
    try {
      inventory.addStock(item, numItem);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    }
    return inventory;
  }

  @GetMapping("/removeStock")
  public Stock removeStock(@RequestParam("item") String item, @RequestParam("numItem") int numItem) {
    try {
      inventory.removeStock(item, numItem);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    }
    return inventory;
  }

  @GetMapping("/listStock")
  public Stock listStock() {
    return inventory;
  }
}
