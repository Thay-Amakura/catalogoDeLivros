package br.com.thaynna.catalogodelivros.dto;

import java.util.List;

public class ApiData {
    private Integer count;
    private String next;
    private String previous;
    private List<LivroData> results;

    public ApiData() {
    // Intencionalmente vazio para frameworks de desserialização
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<LivroData> getResults() {
        return results;
    }

    public void setResults(List<LivroData> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ApiData{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}