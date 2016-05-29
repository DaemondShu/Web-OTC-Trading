package data;

/**
 * Created by monkey_d_asce on 16-5-30.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Page基类<br>
 *
 * @describe：分页
 * @author：yangjian1004
 * @since：2011-11-23
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 665620345605746930L;
    /** 总条数 */
    private int count;
    /** 页码 */
    private int pageNo;
    /** 每页显示多少条 */
    private int rowsPerPage;
    /** 总页数 */
    private int totalPageCount;
    /** 起始条数 */
    private int firstRow;
    /** 结束条数 */
    private int lastRow;
    /** 查询结果集合形式的结果 */
    private List<T> result;
    /** 查询结果对象形式的结果 */
    public Object obj;

    public Integer code; // 返回码
    private boolean success = true;
    private String message;

    public Page() {
    }

    public Page(List<T> list) {
        this(list.size(), 1, list.size(), list);
    }

    public Page(int count, int pageNo, int rowsPerPage, List<T> result) {
        if (rowsPerPage < 1) {
            rowsPerPage = 1;
        }
        this.count = count;
        this.pageNo = pageNo;
        this.result = result;
        this.rowsPerPage = rowsPerPage;
        if (this.result == null)
            this.result = new ArrayList<T>();
        totalPageCount = count / rowsPerPage;
        if (count - (count / rowsPerPage) * rowsPerPage > 0)
            totalPageCount++;
        if (count == 0) {
            totalPageCount = 0;
            pageNo = 0;
        }

        firstRow = (pageNo - 1) * rowsPerPage + 1;
        if (count == 0) {
            firstRow = 0;
        }
        lastRow = (pageNo) * rowsPerPage;
        if (lastRow > count) {
            lastRow = count;
        }
    }

    /** 返回每页的条数 */
    public int getCount() {
        return count;
    }

    public List<T> getResult() {
        return result;
    }

    public int getPageNo() {
        return pageNo;
    }

    /** 返回每页的条数 */
    public int getRowsPerPage() {
        return rowsPerPage;
    }

    /** 返回总的页数 */
    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 计算起始条数
     */
    public static int calc(int pageNo, int rowsPerPage, int count) {
        if (pageNo <= 0)
            pageNo = 1;
        if (rowsPerPage <= 0)
            rowsPerPage = 10;

        // 当把最后一页数据删除以后,页码会停留在最后一个上必须减一
        int totalPageCount = count / rowsPerPage;
        if (pageNo > totalPageCount && (count % rowsPerPage == 0)) {
            pageNo = totalPageCount;
        }
        if (pageNo - totalPageCount > 2) {
            pageNo = totalPageCount + 1;
        }
        int firstRow = (pageNo - 1) * rowsPerPage;
        if (firstRow < 0) {
            firstRow = 0;
        }
        return firstRow;
    }

}