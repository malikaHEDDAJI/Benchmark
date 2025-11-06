package com.benchmark.rest.projection;

import com.benchmark.rest.model.Category;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "categorySummary", types = { Category.class })
public interface CategorySummary {
    Long getId();
    String getCode();
    String getName();
}
