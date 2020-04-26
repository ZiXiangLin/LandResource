package com.gis.demo.domain.ModelParament;

public class NavSearch {
    private int navLevel;
    private String parentId;
    private int dicId;

    public int getNavLevel() {
        return navLevel;
    }
    public void setNavLevel(int nAvLevel) {
        navLevel = nAvLevel;
    }
    public String getParentId(){return parentId;}
    public void setParentId(String pArentId){
        parentId = pArentId;
    }
    public int getDicId(){return dicId;}
    public void setDicId(int dIcId){dicId=dIcId;}

}
