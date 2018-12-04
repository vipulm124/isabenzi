package com.example.isebenzi.business.objects;


/**
 * Created by takhleeqmacpro on 12/2/16.
 */

//public class ParamString extends AddProjects {
public class ParamString {

    private String key;
    private String value;
//    private ArrayList<AddProjects> data;

    public ParamString(String key, String value) {
        this.key = key;
        this.value = value;
    }

//    public ParamString(String key, ArrayList<AddProjects> data1) {
//        this.key=key;
//        this.data=data1;
//    }
//
//    public ArrayList<AddProjects> getData() {
//        return data;
//    }
//
//    public void setData(ArrayList<AddProjects> data) {
//        this.data = data;
//    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
