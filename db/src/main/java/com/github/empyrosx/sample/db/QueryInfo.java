package com.github.empyrosx.sample.db;

/**
 * Created by empyros on 29.08.17.
 */
public class QueryInfo {

    private int selectCount;
    private int insertCount;
    private int updateCount;
    private int deleteCount;

    public void incSelectCount() {
        selectCount++;
    }

    public void incInsertCount() {
        insertCount++;
    }

    public void incUpdateCount() {
        updateCount++;
    }

    public void incDeleteCount() {
        deleteCount++;
    }

    public void clear() {
        selectCount = 0;
        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public int getDeleteCount() {
        return deleteCount;
    }
}
