package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddComposedEntryReq;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntry;
import org.twentyfive.shop_manager_api_layer.models.Entry;
import org.twentyfive.shop_manager_api_layer.repositories.ComposedEntryRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ComposedEntryService {

    private final ComposedEntryRepository composedEntryRepository;

    private final EntryService entryService;

    public Boolean add(AddComposedEntryReq addComposedEntryReq) {
        Set<Entry> entries = entryService.getAllByLabelList(addComposedEntryReq.getEntryNames());
        ComposedEntry composedEntry = addComposedEntryReq.getComposedEntry();
        composedEntry.setEntries(entries);
        composedEntryRepository.save(composedEntry);
        return true;
    }

    public List<ComposedEntry> getAll() {
        return composedEntryRepository.findAll();
    }
}
