package com.yueonsu.www.config;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;

import java.util.Map;

public class DefaultViewPreparer implements ViewPreparer {

    @Override
    public void execute(Request request, AttributeContext attributeContext) {
        Map<String, Object> model = request.getContext("request");
        model.put("data", "default data");
    }
}
