package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.Entry;
import org.twentyfive.shop_manager_api_layer.repositories.EntryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;

    public Boolean add(Entry entry) {
        entryRepository.save(entry);
        return true;
    }

    public List<Entry> getAll() {
        return entryRepository.findAll();
    }
}
