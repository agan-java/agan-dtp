package com.agan.common.search;

import java.util.List;

/**
 * 分页响应结果
 *
 */
public class PageResult<T>
{
    /**
     * 总数
     */
    private long total;
    /**
     * 当页数据行集
     */
    private List<T> rows;

    public PageResult()
    {
    }

    public PageResult(long total, List<T> rows)
    {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<T> getRows()
    {
        return rows;
    }

    public void setRows(List<T> rows)
    {
        this.rows = rows;
    }
}
