package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.CompanyDao;
import dao.DebtDao;
import dao.StatusDao;
import models.Company;
import models.Status;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import pojo.TopPojo;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CompanyController {

    public static final String STATUS_SEPARATOR = ",";

    @Inject
    CompanyDao dao;

    @Inject
    StatusDao statusDao;

    @Inject
    DebtDao debtDao;

    public Result info(@PathParam("cif") Long cif) {
        Company company = dao.getCompany(cif);

        if (company == null) {
            // todo
            return Results.redirectTemporary("/");
        }

        List<String> status = getStatus(company.stari);

        Result result = Results.html();

        result.render("cif", company.cif);
        result.render("name", company.denumire);
        result.render("cod", company.cod);
        result.render("stari", status);
        result.render("judet", company.judet);
        result.render("localitate", company.localitate);
        result.render("datorii", debtDao.getDebt(company.cif));

        return result;
    }

    private List<String> getStatus(String stari) {
        List<String> statuses = new ArrayList<>();

        if (stari == null || stari.trim().isEmpty()) {
            return statuses;
        }

        if (stari.contains(STATUS_SEPARATOR)) {
            String[] split = stari.split(STATUS_SEPARATOR);
            for (String status : split) {
                Status statusEntity = statusDao.getStatus(Integer.valueOf(status));

                if (statusEntity != null) {
                    statuses.add(statusEntity.denumire);
                }
            }
        } else {
            statuses.add(statusDao.getStatus(Integer.valueOf(stari)).denumire);
        }

        return statuses;
    }

    public Result companies() {
        Result result = Results.html();

        List<TopPojo> top = debtDao.getTop(1000);
        result.render("topCompanies", top);

        return result;
    }

}
