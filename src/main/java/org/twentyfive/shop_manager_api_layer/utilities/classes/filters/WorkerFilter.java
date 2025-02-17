package org.twentyfive.shop_manager_api_layer.utilities.classes.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerFilter extends GenericFilter{
    private String role;
}
