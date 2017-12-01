/*
 * Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.samples.flightbooking.persistence.impl;

import com.consol.citrus.samples.flightbooking.entity.CustomerEntity;
import com.consol.citrus.samples.flightbooking.entity.converter.CustomerConverter;
import com.consol.citrus.samples.flightbooking.model.Customer;
import com.consol.citrus.samples.flightbooking.persistence.CustomerDao;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * @author Christoph Deppisch
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;
    
    public Customer find(String customerId) {
        return CustomerConverter.from(em.find(CustomerEntity.class, customerId));
    }

    @SuppressWarnings("unchecked") public List<Customer> findAll() {
        Query query = em.createQuery("from CustomerEntity c");
        
        return query.getResultList();
    }

    public void merge(Customer customer) {
        em.merge(CustomerConverter.from(customer));
    }

    public void persist(Customer customer) {
        em.persist(CustomerConverter.from(customer));
    }

    public void remove(Customer customer) {
        em.remove(CustomerConverter.from(customer));
    }

}
