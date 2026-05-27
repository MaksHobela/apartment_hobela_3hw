package org.example.app.item;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class ApartmentReportRepository {

    private final JdbcTemplate jdbc;

    public ApartmentReportRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> findApartmentsWithLocation() {
        String sql = """
            SELECT a.apartmentid, a.apartmentname, a.propertytype,
                   a.pricepernight, a.dateofReg,
                   l.city, l.country, l.postalCode
            FROM apartment a
            JOIN location l ON a.postalcode = l.postalCode
            ORDER BY a.pricepernight ASC
            """;
        return jdbc.queryForList(sql);
    }

    public List<Map<String, Object>> findBookingsWithDetails() {
        String sql = """
            SELECT b.booking_id,
                   u.first_name, u.last_name,
                   a.apartmentname, a.propertytype,
                   a.pricepernight,
                   l.city
            FROM booking b
            JOIN guest g ON b.guest_id = g.user_id
            JOIN user u ON g.user_id = u.user_id
            JOIN apartment a ON b.apartment_id = a.apartmentid
            JOIN location l ON a.postalcode = l.postalCode
            ORDER BY b.booking_id ASC
            """;
        return jdbc.queryForList(sql);
    }

    public List<Map<String, Object>> findPriceSummaryByType() {
        String sql = """
            SELECT propertytype,
                   COUNT(*) AS total_apartments,
                   MIN(pricepernight) AS min_price,
                   MAX(pricepernight) AS max_price,
                   AVG(pricepernight) AS avg_price
            FROM apartment
            GROUP BY propertytype
            ORDER BY avg_price DESC
            """;
        return jdbc.queryForList(sql);
    }
}