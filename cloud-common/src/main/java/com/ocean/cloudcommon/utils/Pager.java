package com.ocean.cloudcommon.utils;

import java.util.List;
/**
 * 用于分页的工具类
 * @author 80818647@qq.com
 */
public class Pager<T> implements java.io.Serializable{
    
    private List<T> list;       //对象记录结果集
    private int total = 0;      // 总条数数
    private int limit = 20;     // 每页显示条数
    private int pages = 1;      // 总页数
    private int pageNumber = 1; // 当前页

    private boolean isFirstPage=false;        //是否为第一页
    private boolean isLastPage=false;         //是否为最后一页
    private boolean hasPreviousPage=false;   //是否有前一页
    private boolean hasNextPage=false;       //是否有下一页

    private int navigatePages=8; //导航页码数
    private int[] navigatePageNumbers;  //所有导航页号

    public Pager(int total, int pageNumber) {
        init(total, pageNumber, limit);
    }
    public Pager(int total, int pageNumber, int limit) {
        init(total, pageNumber, limit);
    }

    private void init(int total, int pageNumber, int limit){
        //设置基本参数
        this.total=total;
        this.limit=limit;
        this.pages=(this.total-1)/this.limit+1; //总页数
        //根据输入可能错误的当前号码进行自动纠正
        if(pageNumber<1){
            this.pageNumber=1;
        }else if(pageNumber>this.pages){
            this.pageNumber=this.pages;
        }else{
            this.pageNumber=pageNumber;
        }
        //基本参数设定之后进行导航页面的计算
        calcNavigatePageNumbers();
        //以及页面边界的判定
        judgePageBoudary();

    }

    /**
     * 计算导航页
     */
    private void calcNavigatePageNumbers(){
        //当总页数小于或等于导航页码数时
        if(pages<=navigatePages){
            navigatePageNumbers=new int[pages];
            for(int i=0;i<pages;i++){
                navigatePageNumbers[i]=i+1;
            }

        }else{ //当总页数大于导航页码数时
            navigatePageNumbers=new int[navigatePages];
            int startNum=pageNumber-navigatePages/2;
            int endNum=pageNumber+navigatePages/2;

            if(startNum<1){
                startNum=1;
                //(最前navPageCount页
                for(int i=0;i<navigatePages;i++){
                    navigatePageNumbers[i]=startNum++;
                }
            }else if(endNum>pages){
                endNum=pages;
                //最后navPageCount页
                for(int i=navigatePages-1;i>=0;i--){
                    navigatePageNumbers[i]=endNum--;
                }
            }else{
                //所有中间页
                for(int i=0;i<navigatePages;i++){
                    navigatePageNumbers[i]=startNum++;
                }

            }

        }

    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary(){

        isFirstPage = pageNumber == 1;
        isLastPage = pageNumber == pages && pageNumber!=1;
        hasPreviousPage = pageNumber!=1;
        hasNextPage = pageNumber!=pages;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    /**
     * 得到当前页的内容
     * @return {List}
     */
    public List<T> getList() {
        return list;

    }
    /**
     * 得到记录总数
     * @return {int}
     */
    public int getTotal() {
        return total;
    }

    /**
     * 得到每页显示多少条记录
     * @return {int}
     */
    public int getLimit() {
        return limit;
    }
    /**
     * 得到页面总数
     * @return {int}
     */
    public int getPages() {
        return pages;
    }
    /**
     * 得到当前页号
     * @return {int}
     */
    public int getPageNumber() {
        return pageNumber;
    }
    /**
     * 得到所有导航页号 
     * @return {int[]}
     */
    public int[] getNavigatePageNumbers() {
        return navigatePageNumbers;
    }
    public boolean isFirstPage() {
        return isFirstPage;
    }
    public boolean isLastPage() {
        return isLastPage;
    }
    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }
    public boolean hasNextPage() {
        return hasNextPage;
    }

    public String toString(){
        String str=new String();
        str= "[" +
            "total="+total+
            ",pages="+pages+
            ",pageNumber="+pageNumber+
            ",limit="+limit+
            //",navigatePages="+navigatePages+
            ",isFirstPage="+isFirstPage+
            ",isLastPage="+isLastPage+
            ",hasPreviousPage="+hasPreviousPage+
            ",hasNextPage="+hasNextPage+
            ",navigatePageNumbers=";
        int len=navigatePageNumbers.length;
        if(len>0)str+=(navigatePageNumbers[0]);
        for(int i=1;i<len;i++){
            str+=(" "+navigatePageNumbers[i]);
        }
        //sb+=",list="+list;
        str+="]";
        return str;
    }

}