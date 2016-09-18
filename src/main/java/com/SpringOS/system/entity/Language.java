package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by AlbertXmas on 16/9/7.
 */
@Entity
@Table(name = "os_language")
public class Language extends IdEntity{
    private String realPath;
    private String name;
    private Date datetime;

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
