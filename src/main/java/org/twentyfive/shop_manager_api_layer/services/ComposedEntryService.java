package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.exceptions.ComposedEntryNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.models.ids.ComposedEntryClosureId;
import org.twentyfive.shop_manager_api_layer.repositories.ComposedEntryClosureRepository;
import org.twentyfive.shop_manager_api_layer.repositories.ComposedEntryRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleComposedEntry;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComposedEntryService {

    private final ComposedEntryRepository composedEntryRepository;
    private final ComposedEntryClosureRepository composedEntryClosureRepository;
    private final EntryService entryService;

    public List<ComposedEntry> getAll() {
        return composedEntryRepository.findAll();
    }

    public ComposedEntry getByLabel(String label) {
        return composedEntryRepository.findByLabel(label).orElseThrow(() -> new ComposedEntryNotFoundException("composed entry not found"));
    }
    public Boolean add(ComposedEntry composedEntry) {
        composedEntryRepository.save(composedEntry);
        return true;
    }


    public void createAndAddListOfComposedEntryClosure(List<SimpleComposedEntry> simpleComposedEntries, CashRegister cashRegister) {
        for (SimpleComposedEntry simpleComposedEntry : simpleComposedEntries) {
            createAndAddComposedEntryClosure(simpleComposedEntry, cashRegister);
        }
    }

    private void createAndAddComposedEntryClosure(SimpleComposedEntry simpleComposedEntry, CashRegister cashRegister){
        ComposedEntry composedEntry = getByLabel(simpleComposedEntry.getLabel());

        ComposedEntryClosureId composedEntryClosureId = new ComposedEntryClosureId(composedEntry, cashRegister);
        //TODO da fixare
        ComposedEntryClosure composedEntryClosure = new ComposedEntryClosure(composedEntryClosureId, new ArrayList<>());
        composedEntryClosureRepository.save(composedEntryClosure);
    }
}
