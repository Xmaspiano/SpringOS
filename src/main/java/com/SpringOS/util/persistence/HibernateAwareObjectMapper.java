package com.SpringOS.util.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Created by huludawang on 4/10/16.
 */
public class HibernateAwareObjectMapper  extends ObjectMapper{
    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate5Module());
    }
}
