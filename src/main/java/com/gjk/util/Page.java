package com.gjk.util;

public class Page {
    private Integer start;
    private Integer count;
    private Integer total;
    private String param;

    private static final Integer defCountEveryPage = 5;

    public Page() {
        start = 0;
        count = defCountEveryPage;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getLast(){
        int res = total%count;
        if(res == 0){
            return total - count;
        }else {
            return total - res;
        }
    }

    public Boolean getHasPreviouse(){
        if(start <= 0)
            return false;
        return true;
    }

    public Boolean getHasNext(){
        if(start >= getLast())
            return false;
        return true;
    }

    public Integer getTotalPage(){
        Integer totalCount = total/count;
        if(total % count != 0){
            totalCount++;
        }
        return totalCount;
    }
}
