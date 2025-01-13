package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTimeSlot {
    private Long id;
    private String name;
    private String description;
    private LocalTime start;
    private LocalTime end;
}
