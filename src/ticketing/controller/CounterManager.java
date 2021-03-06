
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package ticketing.controller;

import ticketing.dao.Counterr;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author melrose27
 */
public class CounterManager {

    public static Counterr getNumber(String counter_Lane,String uid) throws SQLException, IOException {
        Counterr bean = new Counterr();
        ResultSet resultSet = null;
        try {
            CallableStatement callableStatement = ConnectionManager.getInstance().getConnection().prepareCall("{call counter_number(?,?)}",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            callableStatement.setString(1, counter_Lane);
            callableStatement.setString(2, uid);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                bean.setCounter(resultSet.getString(1));
                bean.setType(resultSet.getString(4));
                bean.setDescription(resultSet.getString(6));
                bean.setDate(resultSet.getString(8));
                bean.setUserID(resultSet.getString(9));
                return bean;
            } else {
                System.err.println("No Rows Found...");
                return null;
            }
        } catch (SQLException sqlex) {
            System.err.println(sqlex.getLocalizedMessage());
            return null;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
