package com.yueonsu.www.board.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardPageable {
    // 페이징
    private int rowCnt = 15;
    private int pageCnt = 10;

    private int startIdx;
    private int page;
    private int lastPage;
    private int startPage;
    private int totalCount;
    private int maxPage;
    private List<Integer> pageArr;

    // 검색내용
    private String text;
    // 검색 카테고리
    private int sel;


    public void setPage(int page) {
        this.startIdx = (page - 1) * this.rowCnt;
        this.page = page;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.maxPage = (int) Math.ceil((double) totalCount / (double) this.rowCnt);
        this.lastPage = ((int) Math.ceil((double) this.page /(double) this.pageCnt)) * this.pageCnt;
        this.lastPage = (this.lastPage  < this.maxPage) ? this.lastPage : this.maxPage;
        this.startPage = (this.lastPage - this.pageCnt) + 1;
        this.startPage = (this.startPage < 0) ? 1 : this.startPage;

        this.pageArr = new ArrayList<>();
        for(int i=this.startPage; i<=this.lastPage; i++) {
            this.pageArr.add(i);
        }
    }

    public List<Integer> getPageArr() {
        return this.pageArr;
    }

    public boolean isPrevious() {
        boolean result = false;
        if(this.startPage == 1) {
            result = true;
        }
        return result;
    }

    public boolean isNext() {
        boolean result = false;
        if(this.lastPage == this.maxPage) {
            result = true;
        }
        return result;
    }
}
