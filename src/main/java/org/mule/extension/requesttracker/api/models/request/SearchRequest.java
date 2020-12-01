package org.mule.extension.requesttracker.api.models.request;

import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import java.util.*;

public class SearchRequest {

    @Parameter
    @Summary("Search query. <Field> <Comparison operator> '<Value>' [AND|OR]... Parenthesis also allowed. Example: Owner = 'Nobody' AND ( Status = 'new' OR Status = 'open' )")
    @Example("Created > '2020-11-20 10:00:00'")
    private String query;

    @Parameter
    @Optional
    @Summary("Name of field to order by. Prefix with + or - to order ascending or descending. Example: +Created")
    private String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }


    public Map<String, String> asParams() {
        Map<String, String> params = new HashMap<>();
        if(StringUtils.isNotBlank(orderBy)) {
            params.put("orderby", orderBy);
        }
        params.put("query", query);
        params.put("format", "l");
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchRequest that = (SearchRequest) o;
        return query.equals(that.query) &&
                Objects.equals(orderBy, that.orderBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, orderBy);
    }
}
