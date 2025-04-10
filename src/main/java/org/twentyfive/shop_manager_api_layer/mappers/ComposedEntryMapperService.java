package org.twentyfive.shop_manager_api_layer.mappers;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntry;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntryClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.*;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleComposedEntryClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleGenericEntry;

import java.util.ArrayList;
import java.util.Comparator;
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

        composedEntryClosures.sort(Comparator.comparing(c -> c.getId().getComposedEntry().getId()));

        for (ComposedEntryClosure composedEntryClosure : composedEntryClosures) {
            SimpleComposedEntryClosure simpleComposedEntryClosure = mapComposedEntryClosureToSimpleComposedEntryClosure(composedEntryClosure);
            composedSimpleEntries.add(simpleComposedEntryClosure);
        }

        return composedSimpleEntries;
    }
    public List<ComposedEntryClosureDetails> mapComposedEntryClosureToDetailsDTO(List<ComposedEntryClosure> composedEntryClosures) {
        List<ComposedEntryClosureDetails> entryClosureDetails = new ArrayList<>();
        composedEntryClosures.sort(Comparator.comparing(c -> c.getId().getComposedEntry().getId()));

        for (ComposedEntryClosure composedEntryClosure : composedEntryClosures) {
            ComposedEntryClosureDetails composedEntryClosureDetail = mapComposedEntryClosureToDetailsComposedEntryClosure(composedEntryClosure);
            entryClosureDetails.add(composedEntryClosureDetail);
        }
        return entryClosureDetails;
    }

    private ComposedEntryClosureDetails mapComposedEntryClosureToDetailsComposedEntryClosure(ComposedEntryClosure composedEntryClosure) {
        ComposedEntryClosureDetails composedEntryClosureDetail = new ComposedEntryClosureDetails();

        composedEntryClosureDetail.setLabel(composedEntryClosure.getId().getComposedEntry().getLabel());
        composedEntryClosureDetail.setLabelAndValues(composedEntryClosure.getValue());

        return composedEntryClosureDetail;
    }

    private SimpleComposedEntryClosure mapComposedEntryClosureToSimpleComposedEntryClosure(ComposedEntryClosure composedEntryClosure) {
        SimpleComposedEntryClosure simpleComposedEntryClosure = new SimpleComposedEntryClosure();

        simpleComposedEntryClosure.setComposedLabelEntry(composedEntryClosure.getId().getComposedEntry().getLabel());
        simpleComposedEntryClosure.setLabelAndValues(composedEntryClosure.getValue());

        return simpleComposedEntryClosure;
    }

}
