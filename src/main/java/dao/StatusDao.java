/**
 * Copyright (C) 2012-2016 the original author or authors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import models.Status;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class StatusDao {

    @Inject
    Provider<EntityManager> emp;

    @UnitOfWork
    public Status getStatus(Integer cod) {
        try {
            EntityManager entityManager = emp.get();

            Query q = entityManager.createQuery("SELECT x FROM Status x WHERE x.cod = :cod");
            Status entity = (Status) q.setParameter("cod", cod).getSingleResult();

            return entity;
        } catch (NoResultException e) {
            return null;
        }
    }
}
