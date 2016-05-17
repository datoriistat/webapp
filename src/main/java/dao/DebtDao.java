
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
import models.Debt;
import ninja.jpa.UnitOfWork;
import pojo.DebtCountyPojo;
import pojo.DebtPojo;
import pojo.DebtTotalsPojo;
import pojo.TopPojo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;


public class DebtDao {

    @Inject
    Provider<EntityManager> emp;

    @UnitOfWork
    public DebtPojo getDebt(Long cif) {
        EntityManager entityManager = emp.get();

        try {
            Query q = entityManager.createQuery("SELECT x FROM Debt x WHERE x.cif = :cif");
            Debt entity = (Debt) q.setParameter("cif", cif).getSingleResult();

            DebtPojo pojo = new DebtPojo();

            pojo.opStat = formatCurrency(entity.opStat);
            pojo.oaStat = formatCurrency(entity.oaStat);
            pojo.ocStat = formatCurrency(entity.ocStat);

            pojo.opSocial = formatCurrency(entity.opSocial);
            pojo.oaSocial = formatCurrency(entity.oaSocial);
            pojo.ocSocial = formatCurrency(entity.ocSocial);

            pojo.opSomaj = formatCurrency(entity.opSomaj);
            pojo.oaSomaj = formatCurrency(entity.oaSomaj);
            pojo.ocSomaj = formatCurrency(entity.ocSomaj);

            pojo.opSanatate = formatCurrency(entity.opSanatate);
            pojo.oaSanatate = formatCurrency(entity.oaSanatate);
            pojo.ocSanatate = formatCurrency(entity.ocSanatate);

            pojo.total = formatCurrency(entity.total);

            return pojo;
        } catch (NoResultException e) {
            return null;
        }
    }

    @UnitOfWork
    public DebtTotalsPojo getDebtTotals() {
        DebtTotalsPojo result = new DebtTotalsPojo();

        EntityManager entityManager = emp.get();

        Query q = entityManager.createQuery("SELECT COUNT(x) FROM Debt x");
        result.totalCompaniesWithDebt = (Long) q.getSingleResult();

        q = entityManager.createQuery("SELECT COUNT(x) FROM Company x");
        result.totalCompanies = (Long) q.getSingleResult();

        result.totalCompaniesWithoutDebt = result.totalCompanies - result.totalCompaniesWithDebt;

        result.counties = getDebtByCounties();

        return result;
    }

    @UnitOfWork
    public List<DebtCountyPojo> getDebtByCounties() {
        List<DebtCountyPojo> pojos = new ArrayList<>();

        EntityManager entityManager = emp.get();

        Query q = entityManager.createQuery("SELECT c.judet, sum(d.total) AS totals FROM Debt d, Company c WHERE d.cif = c.cif AND c.judet <> '' GROUP BY c.judet ORDER BY totals DESC");

        List<Object[]> results = q.getResultList();

        for (Object[] result : results) {
            DebtCountyPojo pojo = new DebtCountyPojo();
            pojos.add(pojo);

            pojo.name = (String) result[0];
            pojo.value = (Long) result[1];
            pojo.valueStr = formatCurrency(pojo.value);
        }

        return pojos;
    }

    @UnitOfWork
    public List<TopPojo> getTop(int count) {
        List<TopPojo> pojos = new ArrayList<>();

        EntityManager entityManager = emp.get();

        Query q = entityManager.createQuery("SELECT d.cif, d.denumire, d.total FROM Debt d ORDER BY total DESC");
        q.setMaxResults(count);

        List<Object[]> results = q.getResultList();

        for (Object[] result : results) {
            TopPojo pojo = new TopPojo();
            pojos.add(pojo);

            pojo.cif = String.valueOf(result[0]);
            pojo.name = (String) result[1];
            pojo.value = (Long) result[2];
            pojo.valueStr = formatCurrency(pojo.value);
        }

        return pojos;
    }

    public static String formatCurrency(double value) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("ro_RO"));
        currencyFormatter.setCurrency(Currency.getInstance("RON"));
        currencyFormatter.setMaximumFractionDigits(0);
        return currencyFormatter.format(value);
    }

    public static String formatCurrency(Long value) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("ro_RO"));
        currencyFormatter.setCurrency(Currency.getInstance("RON"));
        currencyFormatter.setMaximumFractionDigits(0);
        return currencyFormatter.format(value);
    }


}
