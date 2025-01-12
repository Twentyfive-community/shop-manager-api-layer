package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.exceptions.LabelNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.Entry;
import org.twentyfive.shop_manager_api_layer.repositories.EntryRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;

    public List<Entry> getAll() {
        return entryRepository.findAll();
    }

    public Entry getByLabel(String label) {
        return entryRepository.findByLabel(label).orElseThrow(() -> new LabelNotFoundException("label not found"));
    }

    public Set<Entry> getAllByLabelList(List<String> labels) {
        Set<Entry> entries = new HashSet<>();
        for (String label : labels) {
            Entry entry = getByLabel(label);
            entries.add(entry);
        }
        return entries;
    }

    public Boolean add(Entry entry) {
        entryRepository.save(entry);
        return true;
    }


}
