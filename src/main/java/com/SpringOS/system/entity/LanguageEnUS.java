package com.SpringOS.system.entity;

import com.SpringOS.system.entity.common.IdEntity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by AlbertXmas on 16/9/7.
 */
@Entity
@Table(name = "os_language_en_US")
public class LanguageEnUS extends IdEntity{
    private String parentId;
    private String name;
    private Date datetime;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
