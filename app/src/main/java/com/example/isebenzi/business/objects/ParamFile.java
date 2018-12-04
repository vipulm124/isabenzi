package com.example.isebenzi.business.objects;

import java.io.File;

/**
 * Created by takhleeqmacpro on 12/2/16.
 */

public class ParamFile {
    private String key;
    private File file;
    private String value;
    public ParamFile(String key, File value) {
        this.key = key;
        this.file = value;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
