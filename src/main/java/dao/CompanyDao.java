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
import models.Company;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class CompanyDao {

    @Inject
    Provider<EntityManager> emp;

    @UnitOfWork
    public List<Company> getAllCompanies(String search, int first, int buffer) {
        EntityManager entityManager = emp.get();

        String sql = "SELECT x FROM Company x";

        if (search != null && !search.trim().isEmpty()) {
            sql += " WHERE lower(x.denumire) LIKE :search  ";
        }

        TypedQuery<Company> query = entityManager.createQuery(sql, Company.class);

        if (search != null && !search.trim().isEmpty()) {
            query.setParameter("search", "%" + search.toLowerCase() + "%");
        }

        query.setFirstResult(first);
        query.setMaxResults(buffer);

        List<Company> entities = query.getResultList();

        return entities;
    }

    @UnitOfWork
    public Long getCompaniesCount(String search) {
        EntityManager entityManager = emp.get();

        String sql = "SELECT COUNT(x) FROM Company x";

        if (search != null && !search.trim().isEmpty()) {
            sql += " WHERE lower(x.denumire) LIKE :search  ";
        }

        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);

        if (search != null && !search.trim().isEmpty()) {
            query.setParameter("search", "%" + search.toLowerCase() + "%");
        }

        Long count = (Long) query.getSingleResult();

        return count;
    }

    @UnitOfWork
    public Company getCompany(Long cif) {
        try {
            EntityManager entityManager = emp.get();

            Query q = entityManager.createQuery("SELECT x FROM Company x WHERE x.cif = :cif AND x.stari NOT LIKE '%1084%'");
            Company entity = (Company) q.setParameter("cif", cif).getSingleResult();

            return entity;
        } catch (NoResultException e) {
            return null;
        }
    }

}
