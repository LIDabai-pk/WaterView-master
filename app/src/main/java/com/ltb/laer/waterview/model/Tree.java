package com.ltb.laer.waterview.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity(tableName = "Trees")
public class Tree {
    public Tree(int id){
        this.treeId = id;
    }

    //用于到时候可以种多种不同的树
    private int treeId;
}
