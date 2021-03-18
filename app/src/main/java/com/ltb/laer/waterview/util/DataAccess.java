package com.ltb.laer.waterview.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ltb.laer.waterview.model.TreeRecord;

import java.util.ArrayList;


public class DataAccess {

    public Context context;
    private SqlHelper helper;

    public DataAccess(Context context) {
        helper = new SqlHelper();
        this.context = context;

    }

    /**
     * 插入数据
     *
     * @param info
     */
    public void insertUser(TreeRecord info) {
        ContentValues cv = new ContentValues();
        cv.put("time", info.getTime());
        cv.put("state", info.getState());
        cv.put("num", info.getNum());
        cv.put("total", info.getTotal());
        cv.put("during", info.getDuring());
        helper.Insert(context, SqlHelper.Tree_Table, cv);
    }

    /**
     * 修改用户
     *
     * @param info
     */
    public void updateTreeRecord(TreeRecord info) {
        ContentValues cv = new ContentValues();
        cv.put("time", info.getTime());
        cv.put("state", info.getState());
        cv.put("num", info.getNum());
        cv.put("total", info.getTotal());
        cv.put("during", info.getDuring());
        helper.Update(context, SqlHelper.Tree_Table, cv, "id ='" + info.getId() + "'", null);
    }

    /**
     * 查询用户
     *
     * @param selection
     * @param selectionArgs
     * @return
     */
    public ArrayList<TreeRecord> queryTreeRecord(String selection, String[] selectionArgs) {
        Cursor cursor = helper.Query(context, SqlHelper.Tree_Table, null, selection, selectionArgs, null, null, null);
        ArrayList<TreeRecord> list = new ArrayList<TreeRecord>();
        if (cursor.moveToFirst()) {
            do {
                TreeRecord treeRecord = new TreeRecord(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5));
                list.add(treeRecord);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * del 删除用户
     *
     * @param
     */
    public void delTreeRecord(TreeRecord bean) {
        helper.Delete(context, SqlHelper.Tree_Table, "id ='" + bean.getId() + "'", null);
    }


}
