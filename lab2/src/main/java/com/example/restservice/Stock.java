package com.example.restservice;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class Stock {

  private HashMap<String,Integer> inventory;

  public Stock() {
      inventory = new HashMap<>();
  }

  public HashMap<String, Integer> getInventory() {
    return inventory;
  }

  /**
   * Get the entire inventory
   * @return entry set of the inventory
   */
  public Set listStock() {
    return inventory.entrySet();
  }

  /**
   *  if inventory contains the item, decrement the stock level by numItem
   *  else throw NoSuchElement Exception
   * @param item
   * @param numItem
   * @throws NoSuchElementException
   */
  public void removeStock(String item, int numItem) throws NoSuchElementException {
      if(inventory.containsKey(item)){
        inventory.put(item, inventory.get(item) - numItem);
      } else {
        throw new NoSuchElementException(item + " does not exist in the inventory.");
      }
    }

  /**
   * if inventory has the item, increment it by stockLevel
   * else throw NoSuchElementException
   * @param item
   * @param stockLevel
   * @throws NoSuchElementException
   */
    public void addStock(String item, int stockLevel) throws NoSuchElementException {
      if(inventory.containsKey(item)) {
        inventory.put(item, inventory.get(item) + stockLevel);
      } else{
        throw new NoSuchElementException(item + " does not exist in the inventory.");
      }
    }

  /**
   * if inventory contains item, set the stock to stockLevel
   * else throw NoSuchElementException
   * @param item
   * @param stockLevel
   * @throws InvalidKeyException
   */
    public void setStock(String item, int stockLevel) throws InvalidKeyException {
      if(inventory.containsKey(item)) {
        inventory.put(item, stockLevel);
      } else {
        throw new NoSuchElementException(item + " does not exist in the inventory.");
      }
    }

  /**
   * if inventory doesn't already contain item, add the item to inventory with stocklevel of 0
   * else throw KeyAlreadyExistsException
   * @param item
   * @throws KeyAlreadyExistsException
   */
    public void addItem(String item) throws KeyAlreadyExistsException {
      if (!inventory.containsKey(item)) {
        inventory.put(item, 0);
      } else {
        throw new KeyAlreadyExistsException(item + " already exists.");
      }
    }
}
