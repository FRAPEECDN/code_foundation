package com.frapee.basic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
/**
 * String return service function for controller examples of Plain type
 * Do note that DTO mapping is not done for this, except for the mapped version
 */
public class StringService {

    private final List<String> internalList = new ArrayList<>(10);
    
    /**
     * Testing helper -> reset internal list
     */
    public void resetRepositoryList() {
        this.internalList.clear();
    }

    /**
     * Testing helper -> populate internal list
     * @param input input list to add for population;
     */
    public void setInternalStrings(List<String> input) {
        this.internalList.addAll(input);
    }

    /**
     * Service returns all elements in the list
     * @return list of Strings
     */
    public @Nonnull List<String> getAll() {
        log.info("Retrievig all items from the list");
        return this.internalList;
    }

    /**
     * Service returns one element in the list
     * @param idx - the index of the element to return
     * @return single string
     */
    public @Nonnull String getOne(int idx) {
        log.info("Retrieving item from the list");
        return this.internalList.get(idx);
    }

    /**
     * Service appends string to the list
     * @param input - string to add
     * @return size of 
     */
    public Integer createOne(@Nullable String input) {
        log.info("Adding new item to the list");
        if (input == null) {
            throw new NullPointerException();
        }
        if (this.internalList.contains(input)) {
            throw new IllegalArgumentException("already in list");
        }
        this.internalList.add(input);
        return this.internalList.lastIndexOf(input);
    }

    /**
     * Service updates string at index to provided string
     * @param idx - the index of the element to replace
     * @param newValue - string to modify too
     * @return modified string
     */
    public String updateOne(int idx, @Nullable String newValue)  {
        log.info("Chaning an item at index with new value");
        if (newValue == null) {
            throw new NullPointerException();
        }
        if (this.internalList.contains(newValue)) {
            throw new IllegalArgumentException("already in list");
        }
        String oldValue = this.internalList.set(idx, newValue);
        String replacedValue = this.internalList.get(idx);
        assert(!replacedValue.equals(oldValue));
        assert(replacedValue.equals(newValue));
        return replacedValue;
    }

    /**
     * Service removes string at index
     * @param idx - the index of the element to remove
     */
    public void deleteOne(int idx) {
        log.info("Removing an item from the list");
        this.internalList.remove(idx);        
    }

}
