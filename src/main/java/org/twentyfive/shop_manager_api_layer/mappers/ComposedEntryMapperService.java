package org.twentyfive.shop_manager_api_layer.mappers;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntry;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntryClosure;
import org.twentyfive.shop_manager_api_layer.models.Entry;
import org.twentyfive.shop_manager_api_layer.utilities.classes.LabelAndValue;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleComposedEntryClosure;
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

    public List<SimpleComposedEntryClosure> mapListComposedEntryClosureToDTO(List<ComposedEntryClosure> composedEntryClosures) {
        List<SimpleComposedEntryClosure> composedSimpleEntries = new ArrayList<>();

        for (ComposedEntryClosure composedEntryClosure : composedEntryClosures) {
            SimpleComposedEntryClosure simpleComposedEntryClosure = mapComposedEntryClosureToSimpleComposedEntryClosure(composedEntryClosure);
            composedSimpleEntries.add(simpleComposedEntryClosure);
        }

        return composedSimpleEntries;
    }

    private SimpleComposedEntryClosure mapComposedEntryClosureToSimpleComposedEntryClosure(ComposedEntryClosure composedEntryClosure) {
        SimpleComposedEntryClosure simpleComposedEntryClosure = new SimpleComposedEntryClosure();

        simpleComposedEntryClosure.setComposedLabelEntry(composedEntryClosure.getId().getComposedEntry().getLabel());
        simpleComposedEntryClosure.setLabelAndValues(composedEntryClosure.getValue());
        simpleComposedEntryClosure.setTotalValue(composedEntryClosure.getTotalValue());

        return simpleComposedEntryClosure;
    }
}
