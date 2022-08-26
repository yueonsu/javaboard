package com.yueonsu.www.board.comment.model;

import lombok.Data;

@Data
public class CommentPageable extends CommentVo{
    private int rowCnt = 5;
    private int pageCnt = 5;
    private int startIdx;
    private int totalPage;
    private int page;
    private int startPage;
    private int lastPage;

    private boolean next;
    private boolean previous;

    public void setPage(int page) {
        this.page = page;
        this.startIdx = (this.page - 1) * this.rowCnt;
        this.lastPage = (int) Math.ceil((double) this.page / this.pageCnt) * this.pageCnt;
        this.startPage = (this.lastPage - this.pageCnt <= 0) ? 1 : (this.lastPage - this.pageCnt) + 1;
        this.previous = this.startPage != 1;
        this.totalPage = (int) Math.ceil((double) this.totalPage / this.rowCnt);
        this.next = this.totalPage > this.lastPage;
        this.lastPage = Math.min(this.lastPage, this.totalPage);
    }
}
