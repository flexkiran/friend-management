package rais.friendmanagement.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import lombok.Data;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Entity
@Data
public class ErrorLog implements Serializable {

    private static final long serialVersionUID = -2457378874143310172L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private String stackTrace;

    @Column
    private Timestamp logTimestamp;

    @PrePersist
    void prePersist() {
        setLogTimestamp(Timestamp.from(Instant.now()));
    }
}
