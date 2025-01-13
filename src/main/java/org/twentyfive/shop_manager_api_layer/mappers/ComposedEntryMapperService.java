package org.twentyfive.shop_manager_api_layer.mappers;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntry;
import org.twentyfive.shop_manager_api_layer.models.Entry;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleGenericEntry;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComposedEntryMapperService {

    public List<SimpleGenericEntry> mapListComposedEntrytoListSimpleEntry(List<ComposedEntry> composedEntries) {
        List<SimpleGenericEntry> simpleEntries = new ArrayList<>();
        for(ComposedEntry composedEntry : composedEntries){
            SimpleGenericEntry simpleGenericEntry = mapComposedEntrytoSimpleEntry(composedEntry);
            simpleEntries.add(simpleGenericEntry);
        }
        return simpleEntries;
    }

    private SimpleGenericEntry mapComposedEntrytoSimpleEntry(ComposedEntry composedEntry){
        SimpleGenericEntry simpleGenericEntry = new SimpleGenericEntry();
        simpleGenericEntry.setLabel(composedEntry.getLabel());
        return simpleGenericEntry;
    }
}
