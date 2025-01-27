package org.twentyfive.shop_manager_api_layer.mappers;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetAllTotalEntriesReq;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntry;
import org.twentyfive.shop_manager_api_layer.models.Entry;
import org.twentyfive.shop_manager_api_layer.models.EntryClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleEntryClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleGenericEntry;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntryMapperService {

    public List<SimpleGenericEntry>  mapListEntrytoListSimpleEntry(List<Entry> entries){
        List<SimpleGenericEntry> simpleEntries = new ArrayList<>();
        for(Entry entry : entries){
            SimpleGenericEntry simpleGenericEntry = mapEntrytoSimpleEntry(entry);
            simpleEntries.add(simpleGenericEntry);
        }
        return simpleEntries;
    }

    private SimpleGenericEntry mapEntrytoSimpleEntry(Entry entry){
        SimpleGenericEntry simpleGenericEntry = new SimpleGenericEntry();
        simpleGenericEntry.setLabel(entry.getLabel());
        return simpleGenericEntry;
    }

    public List<GetAllTotalEntriesReq> mapTotalEntriesToDTO(List<Entry> entries, List<ComposedEntry> composedEntries) {
        List<GetAllTotalEntriesReq> totalEntries = new ArrayList<>();

        for(Entry entry : entries){
            GetAllTotalEntriesReq getAllTotalEntriesReq = new GetAllTotalEntriesReq();
            getAllTotalEntriesReq.setLabel(entry.getLabel());
            getAllTotalEntriesReq.setRequired(entry.getRequired());
            getAllTotalEntriesReq.setMultiple(false);
            totalEntries.add(getAllTotalEntriesReq);
        }

        for(ComposedEntry composedEntry : composedEntries){
            GetAllTotalEntriesReq getAllTotalEntriesReq = new GetAllTotalEntriesReq();
            getAllTotalEntriesReq.setLabel(composedEntry.getLabel());
            getAllTotalEntriesReq.setRequired(composedEntry.getRequired());
            getAllTotalEntriesReq.setMultiple(true);
            totalEntries.add(getAllTotalEntriesReq);
        }

        return totalEntries;
    }

    public List<SimpleEntryClosure> mapListEntryClosureToDTO(List<EntryClosure> entries) {
        List<SimpleEntryClosure> simpleEntries = new ArrayList<>();

        for(EntryClosure entryClosure : entries){
            SimpleEntryClosure simpleEntryClosure = mapEntryClosureToSimpleEntryClosure(entryClosure);
            simpleEntries.add(simpleEntryClosure);
        }

        return simpleEntries;
    }

    private SimpleEntryClosure mapEntryClosureToSimpleEntryClosure(EntryClosure entryClosure) {
        SimpleEntryClosure simpleEntryClosure = new SimpleEntryClosure();

        simpleEntryClosure.setLabel(entryClosure.getId().getEntry().getLabel());
        simpleEntryClosure.setValue(entryClosure.getValue());

        return simpleEntryClosure;
    }
}
