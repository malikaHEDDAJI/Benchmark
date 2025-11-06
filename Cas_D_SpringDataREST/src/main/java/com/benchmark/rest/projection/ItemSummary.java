package com.benchmark.rest.projection;

import com.benchmark.rest.model.Item;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "itemSummary", types = { Item.class })
public interface ItemSummary {
    Long getId();
    String getSku();
    String getName();
    Double getPrice();
}
