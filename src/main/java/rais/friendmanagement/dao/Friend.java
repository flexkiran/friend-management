package rais.friendmanagement.dao;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.Data;
import org.hibernate.annotations.Check;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Entity
@Check(constraints = "PID1 < PID2")
@Data
public class Friend implements Serializable {

    private static final long serialVersionUID = -7763935318374238204L;

    /**
     * Constraint Check PID1 lower than PID2 to make them unique pair.
     */
    @Embeddable
    @Data
    public static class FriendPk implements Serializable {

        private static final long serialVersionUID = -7231723877769628539L;
        private Long pid1, pid2;
    }
    @EmbeddedId
    private FriendPk id = new FriendPk();

    @JoinColumn(name = "PID1")
    @MapsId("pid1")
    @ManyToOne
    private Person person1;

    @JoinColumn(name = "PID2")
    @MapsId("pid2")
    @ManyToOne
    private Person person2;

}
