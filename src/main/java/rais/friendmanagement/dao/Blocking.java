package rais.friendmanagement.dao;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.Data;

/**
 * .
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Entity
@Data
public class Blocking implements Serializable {

    private static final long serialVersionUID = 6180210400731762562L;

    @Embeddable
    @Data
    public static class BlockPk implements Serializable {

        private static final long serialVersionUID = -7231723877769628539L;
        private Long targetId, requestorId;
    }
    @EmbeddedId
    private BlockPk id = new BlockPk();

    /**
     * target is the person who is blocked by the requestor
     */
    @JoinColumn(name = "TARGET_ID")
    @MapsId("targetId")
    @ManyToOne
    private Person target;

    /**
     * requestor is the person who block the target
     */
    @JoinColumn(name = "REQUESTOR_ID")
    @MapsId("requestorId")
    @ManyToOne
    private Person requestor;

}
