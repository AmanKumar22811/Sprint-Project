package com.classicmodel.projection;

import com.classicmodel.entity.ProductLine;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "productLineExcerpt", types = {ProductLine.class})
public interface ProductLineProjection {
    String getProductLine();
    String getTextDescription();
    String getHtmlDescription();
    String getImageUrl();
}

