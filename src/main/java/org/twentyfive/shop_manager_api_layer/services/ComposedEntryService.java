package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddComposedEntryClosureReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddComposedEntryReq;
import org.twentyfive.shop_manager_api_layer.exceptions.ComposedEntryNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.ComposedEntryMapperService;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.models.ids.ComposedEntryClosureId;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterRepository;
import org.twentyfive.shop_manager_api_layer.repositories.ComposedEntryClosureRepository;
import org.twentyfive.shop_manager_api_layer.repositories.ComposedEntryRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleComposedEntryClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleGenericEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComposedEntryService {

    private final ComposedEntryRepository composedEntryRepository;
    private final ComposedEntryClosureRepository composedEntryClosureRepository;

    private final ComposedEntryMapperService composedEntryMapperService;
    private final EntryService entryService;
    private final CashRegisterRepository cashRegisterRepository;

    public List<SimpleGenericEntry> getAll() {
        List<ComposedEntry> composedEntries = composedEntryRepository.findAll();
        return composedEntryMapperService.mapListComposedEntrytoListSimpleEntry(composedEntries);
    }

    public ComposedEntry getByLabel(String label) {
        return composedEntryRepository.findByLabel(label).orElseThrow(() -> new ComposedEntryNotFoundException("composed entry not found with label: " + label));
    }
    public Boolean add(AddComposedEntryReq addComposedEntryReq) {
        ComposedEntry composedEntry = createComposedEntryFromAddComposedEntryReq(addComposedEntryReq);
        composedEntryRepository.save(composedEntry);
        return true;
    }


    public void createAndAddListOfComposedEntryClosure(List<AddComposedEntryClosureReq> simpleComposedEntries, CashRegister cashRegister) {
        for (AddComposedEntryClosureReq simpleComposedEntryClosure : simpleComposedEntries) {
            createAndAddComposedEntryClosure(simpleComposedEntryClosure, cashRegister);
        }
    }

    private void createAndAddComposedEntryClosure(AddComposedEntryClosureReq simpleComposedEntryClosure, CashRegister cashRegister){
        ComposedEntry composedEntry = getByLabel(simpleComposedEntryClosure.getComposedLabelEntry());

        ComposedEntryClosureId composedEntryClosureId = new ComposedEntryClosureId(composedEntry, cashRegister);
        ComposedEntryClosure composedEntryClosure = new ComposedEntryClosure(composedEntryClosureId, simpleComposedEntryClosure.getLabelAndValues());

        composedEntryClosureRepository.save(composedEntryClosure);
    }

    private ComposedEntry createComposedEntryFromAddComposedEntryReq(AddComposedEntryReq addComposedEntryReq) {
        ComposedEntry composedEntry = new ComposedEntry();
        composedEntry.setLabel(addComposedEntryReq.getLabel());
        return composedEntry;
    }

    public void updateAndRemoveComposedEntryClosure(List<AddComposedEntryClosureReq> composedEntries, CashRegister updatedCashRegister) {
        // Estrapolazione della lista di label
        List<ComposedEntryClosure> delComposedEntries = updatedCashRegister.getComposedEntryClosures();

        delComposedEntries.clear();
        cashRegisterRepository.save(updatedCashRegister);
        if(composedEntries != null) {
            createAndAddListOfComposedEntryClosure(composedEntries, updatedCashRegister);
        }
    }
}
