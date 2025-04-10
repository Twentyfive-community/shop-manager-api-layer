package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddEntryClosureReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddEntryReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetAllTotalEntriesReq;
import org.twentyfive.shop_manager_api_layer.exceptions.EntryNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.EntryMapperService;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.models.ids.EntryClosureId;
import org.twentyfive.shop_manager_api_layer.repositories.*;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleGenericEntry;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final EntryClosureRepository entryClosureRepository;

    private final ComposedEntryRepository composedEntryRepository;

    private final EntryMapperService entryMapperService;
    private final ComposedEntryClosureRepository composedEntryClosureRepository;
    private final CashRegisterRepository cashRegisterRepository;
    private final MsUserClient msUserClient;

    public List<SimpleGenericEntry> getAll() {
        List<Entry> entries = entryRepository.findAll();
        return entryMapperService.mapListEntrytoListSimpleEntry(entries);
    }

    public Entry getByLabel(String label,Business business) {
        return entryRepository.findByLabelAndBusiness(label,business).orElseThrow(() -> new EntryNotFoundException("entry not found with label: " + label));
    }

    public Boolean add(AddEntryReq addEntryReq) {
        Entry entry = createEntryFromAddEntryReq(addEntryReq);
        entryRepository.save(entry);
        return true;
    }


    public void createAndAddListOfEntryClosure(List<AddEntryClosureReq> simpleEntries, CashRegister cashRegister,Business business) {
        for (AddEntryClosureReq simpleEntryClosure : simpleEntries) {
            createAndAddEntryClosure(simpleEntryClosure, cashRegister, business);
        }
    }

    private void createAndAddEntryClosure(AddEntryClosureReq simpleEntryClosure, CashRegister cashRegister, Business business) {
        Entry entry = getByLabel(simpleEntryClosure.getLabel(), business);

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

    public List<GetAllTotalEntriesReq> getAllTotalEntries(String authorization) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        List<Entry> entries = entryRepository.findAllByBusinessOrderByIdAsc(business);
        List<ComposedEntry> composedEntries = composedEntryRepository.findAllByBusinessOrderByIdAsc(business);
        return entryMapperService.mapTotalEntriesToDTO(entries,composedEntries);
    }

    public void updateAndRemoveEntryClosure(List<AddEntryClosureReq> entries, CashRegister updatedCashRegister, Business business) {
        // Estrapolazione della lista di label
        List<EntryClosure> delComposedEntries = updatedCashRegister.getEntryClosures();

        delComposedEntries.clear();
        cashRegisterRepository.save(updatedCashRegister);
        if (entries != null) {
            createAndAddListOfEntryClosure(entries, updatedCashRegister, business);
        }

    }
}
