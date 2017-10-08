package rais.friendmanagement.dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Person implements Serializable {

    private static final long serialVersionUID = -6229869999865699202L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    @lombok.NonNull
    private String email;

}
