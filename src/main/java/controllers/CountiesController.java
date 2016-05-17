package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.DebtDao;
import ninja.Result;
import ninja.Results;

@Singleton
public class CountiesController {

    @Inject
    DebtDao debtDao;

    public Result counties() {
        Result result = Results.html();

        result.render("totals", debtDao.getDebtTotals());

        return result;
    }
}
