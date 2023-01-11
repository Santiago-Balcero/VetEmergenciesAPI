package tc_sb_35_api.tc_sb_35_api.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tc_sb_35_api.tc_sb_35_api.model.Report;

@Repository
public class ReportRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    // Gets DataSource and avoids null pointer exception
    private final DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }

    public boolean countEmergencies() {
        try {
            Connection conn = getDataSource().getConnection();
            String call = "{call PKG_EMERGENCIES_REPORTS.PR_COUNT_MONTHLY_EMERGENCIES}";
            CallableStatement st = conn.prepareCall(call);
            st.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<Report> getReports(int year, int month, Long idSpecies, Long idMainSymptom) {
        ArrayList<Report> reports = new ArrayList<Report> ();
        try {
            Connection conn = getDataSource().getConnection();
            String call = "{call PKG_EMERGENCIES_REPORTS.PR_GET_REPORTS(?, ?, ?, ?, ?)}";
            CallableStatement st = conn.prepareCall(call);
            st.setInt(1, year);
            st.setInt(2, month);
            st.setLong(3, idSpecies);
            st.setLong(4, idMainSymptom);
            st.registerOutParameter(5, Types.REF_CURSOR);
            st.executeQuery();
            ResultSet result = (ResultSet)st.getObject(5);
            if(result != null) {
            while (result.next()) {
                Report report = new Report();
                report.setIdReport(result.getLong(1));
                report.setReportYear(result.getInt(2));
                report.setReportMonth(result.getInt(3));
                report.setSpecies(result.getString(4));
                report.setMainSymptom(result.getString(5));
                report.setNumberEmergencies(result.getLong(6));
                report.setReportExecutionDate(result.getTimestamp(7).toLocalDateTime());
                reports.add(report);
            }
            conn.close();
        }
            return reports;
        } catch (Exception e) {
            e.printStackTrace();
            return reports;
        }
    }

}
