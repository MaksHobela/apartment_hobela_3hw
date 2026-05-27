package org.example.app.item;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("apartment")
public record Apartment(
    @Id @Column("apartmentid") Long apartmentid,
    @Column("apartmentname") String apartmentname,
    @Column("description") String description,
    @Column("pricepernight") BigDecimal pricepernight,
    @Column("propertytype") String propertytype,
    @Column("dateofReg") LocalDate dateofReg,
    @Column("postalcode") String postalcode,
    @Column("hostid") Long hostid,
    @Column("admin_id") Long admin_id
) {
    public static Apartment of(String name, String desc, BigDecimal price,
                                String type, String postal, Long hostId, Long adminId) {
        return new Apartment(null, name, desc, price, type, LocalDate.now(), postal, hostId, adminId);
    }
}