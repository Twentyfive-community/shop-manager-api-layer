package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddEntryReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetAllTotalEntriesReq;
import org.twentyfive.shop_manager_api_layer.exceptions.EntryNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.EntryMapperService;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.models.ids.EntryClosureId;
import org.twentyfive.shop_manager_api_layer.repositories.ComposedEntryRepository;
import org.twentyfive.shop_manager_api_layer.repositories.EntryClosureRepository;
import org.twentyfive.shop_manager_api_layer.repositories.EntryRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleGenericEntry;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleEntryClosure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final EntryClosureRepository entryClosureRepository;

    private final ComposedEntryRepository composedEntryRepository;

    private final EntryMapperService entryMapperService;

    public List<SimpleGenericEntry> getAll() {
        List<Entry> entries = entryRepository.findAll();
        return entryMapperService.mapListEntrytoListSimpleEntry(entries);
    }

    public Entry getByLabel(String label) {
        return entryRepository.findByLabel(label).orElseThrow(() -> new EntryNotFoundException("entry not found with label: " + label));
    }

    public Set<Entry> getAllByLabelList(List<String> labels) {
        Set<Entry> entries = new HashSet<>();
        for (String label : labels) {
            Entry entry = getByLabel(label);
            entries.add(entry);
        }
        return entries;
    }

    public Boolean add(AddEntryReq addEntryReq) {
        Entry entry = createEntryFromAddEntryReq(addEntryReq);
        entryRepository.save(entry);
        return true;
    }


    public void createAndAddListOfEntryClosure(List<SimpleEntryClosure> simpleEntries, CashRegister cashRegister) {
        for (SimpleEntryClosure simpleEntryClosure : simpleEntries) {
            createAndAddEntryClosure(simpleEntryClosure, cashRegister);
        }
    }

    private void createAndAddEntryClosure(SimpleEntryClosure simpleEntryClosure, CashRegister cashRegister){
        Entry entry = getByLabel(simpleEntryClosure.getLabel());

        EntryClosureId entryClosureId = new EntryClosureId(entry, cashRegister);
        EntryClosure entryClosure = new EntryClosure(entryClosureId, simpleEntryClosure.getValue());

        entryClosureRepository.save(entryClosure);
    }

    private Entry createEntryFromAddEntryReq(AddEntryReq addEntryReq) {
        Entry entry = new Entry();
        entry.setLabel(addEntryReq.getLabel());
        entry.setOperation(addEntryReq.getOperation());
        return entry;
    }

    public List<GetAllTotalEntriesReq> getAllTotalEntries() {
        List<Entry> entries = entryRepository.findAll();
        List<ComposedEntry> composedEntries = composedEntryRepository.findAll();
        return entryMapperService.mapTotalEntriesToDTO(entries,composedEntries);
    }
}
