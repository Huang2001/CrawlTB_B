package com.youlu;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ExcelListener extends AnalysisEventListener<LinkedHashMap> {
    private LinkedList<String> isbnList = new LinkedList<String>();

    private HashSet<String> set;

    public void setSet(HashSet<String> set) {
        this.set = set;
    }

    public LinkedList<String> getArrayList() {
        return this.isbnList;
    }

    public void invoke(LinkedHashMap map, AnalysisContext var2) {
        String isbn = (String)map.get(Integer.valueOf(0));
        if (isbn != null && isbn.length() > 10 && !this.set.contains(isbn))
            this.isbnList.add(isbn);
    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext) {}

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        String isbn = headMap.get(Integer.valueOf(0));
        if (isbn != null && isbn.length() > 10 && !this.set.contains(isbn))
            this.isbnList.add(isbn);
    }
}

