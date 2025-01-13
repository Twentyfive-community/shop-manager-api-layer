package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.exceptions.EntryNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.models.ids.EntryClosureId;
import org.twentyfive.shop_manager_api_layer.repositories.EntryClosureRepository;
import org.twentyfive.shop_manager_api_layer.repositories.EntryRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleEntry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final EntryClosureRepository entryClosureRepository;

    public List<Entry> getAll() {
        return entryRepository.findAll();
    }

    public Entry getByLabel(String label) {
        return entryRepository.findByLabel(label).orElseThrow(() -> new EntryNotFoundException("label not found"));
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

    public void createAndAddListOfEntryClosure(List<SimpleEntry> simpleEntries, CashRegister cashRegister) {
        for (SimpleEntry simpleEntry : simpleEntries) {
            createAndAddEntryClosure(simpleEntry, cashRegister);
        }
    }

    private void createAndAddEntryClosure(SimpleEntry simpleEntry, CashRegister cashRegister){
        Entry entry = getByLabel(simpleEntry.getLabel());

        EntryClosureId entryClosureId = new EntryClosureId(entry, cashRegister);
        EntryClosure entryClosure = new EntryClosure(entryClosureId, simpleEntry.getValue());

        entryClosureRepository.save(entryClosure);
    }
}
