package com.yueonsu.www.board.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 페이징
@Data
public class BoardPageable {
    
    // 한 페이지에 나오는 게시물 수
    private int rowCnt = 15;
    // 한 페이지에 나오는 페이지 수
    private int pageCnt = 10;
    private int startIdx;
    private int page;
    // 페이지 마지막 번호
    private int lastPage;
    // 페이지 시작 번호
    private int startPage;
    // 전체 글 수
    private int totalCount;
    // 전체 페이지 수
    private int maxPage;
    // 페이지 시작부터 끝까지 배열
    private List<Integer> pageArr;
    // 검색내용
    private String text;
    // 검색 카테고리
    private int sel;

    private boolean isPrevious;
    private boolean isNext;

    public void setPage(int page) {
        this.startIdx = Math.max((page - 1) * this.rowCnt, 0);
        this.page = page;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.maxPage = (int) Math.ceil((double) totalCount / (double) this.rowCnt);
        this.lastPage = ((int) Math.ceil((double) this.page /(double) this.pageCnt)) * this.pageCnt;
        this.startPage = (this.lastPage - this.pageCnt) + 1;

        this.isPrevious = this.startPage != 1;
        this.isNext = this.lastPage < this.maxPage;

        this.lastPage = Math.min(this.lastPage, this.maxPage);
        this.startPage = (this.startPage < 0) ? 1 : this.startPage;

        this.pageArr = new ArrayList<>();
        for(int i=this.startPage; i<=this.lastPage; i++) {
            this.pageArr.add(i);
        }

    }

    public List<Integer> getPageArr() {
        return this.pageArr;
    }
}
