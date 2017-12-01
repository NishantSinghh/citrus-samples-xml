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

import com.consol.citrus.samples.flightbooking.entity.FlightEntity;
import com.consol.citrus.samples.flightbooking.entity.converter.FlightConverter;
import com.consol.citrus.samples.flightbooking.model.Flight;
import com.consol.citrus.samples.flightbooking.persistence.FlightDao;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * @author Christoph Deppisch
 */
@Repository
public class FlightDaoImpl implements FlightDao {

    @PersistenceContext
    private EntityManager em;
    
    public Flight find(String flightId) {
        return FlightConverter.from(em.find(FlightEntity.class, flightId));
    }

    @SuppressWarnings("unchecked") public List<Flight> findAll() {
        Query query = em.createQuery("from FlightEntity f");
        
        return query.getResultList();
    }

    public void merge(Flight flight) {
        em.merge(FlightConverter.from(flight));
    }

    public void persist(Flight flight) {
        em.persist(FlightConverter.from(flight));
    }

    public void remove(Flight flight) {
        em.remove(FlightConverter.from(flight));
    }

}
