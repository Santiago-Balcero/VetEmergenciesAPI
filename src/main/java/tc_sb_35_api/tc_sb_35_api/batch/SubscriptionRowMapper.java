package tc_sb_35_api.tc_sb_35_api.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tc_sb_35_api.tc_sb_35_api.model.Subscription;

public class SubscriptionRowMapper implements RowMapper<Subscription> {

    public static final String ID_COLUMN = "id_subscription";
    public static final String FINISH_DATE_COLUMN = "finish_date";
    public static final String IS_ACTIVE_COLUMN = "is_active";
    public static final String LAST_UPDATE_DATE_COLUMN = "last_update_date";
    public static final String LAST_UPDATE_ACTION_COLUMN = "last_update_action";


    public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Creates a Subscription instance for every row in result set
        Subscription subscription = new Subscription();
        subscription.setIdSubscription(rs.getLong(ID_COLUMN));
        subscription.setFinishDate(rs.getDate(FINISH_DATE_COLUMN).toLocalDate());
        subscription.setIsActive(rs.getInt(IS_ACTIVE_COLUMN));
        subscription.setLastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE_COLUMN).toLocalDateTime());
        subscription.setLastUpdateAction(rs.getString(LAST_UPDATE_ACTION_COLUMN));
        return subscription;
    }


    
}
