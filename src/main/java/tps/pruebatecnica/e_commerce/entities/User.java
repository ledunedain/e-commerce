package tps.pruebatecnica.e_commerce.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

/**
 * User
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "username")
})
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname", nullable = true)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username", nullable = true)
    private String usename;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    @Column(name = "status", nullable = true)
    private boolean status = true;

    @CreatedDate
    @JsonIgnore
    @Column(updatable = false)
    private LocalDateTime createdAt; 

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime updatedAt; 

    @CreatedBy
    @JsonIgnore
    @Column(updatable = false)
    private String createdBy; 

    @LastModifiedBy
    @JsonIgnore
    private String updatedBy; 

    @PrePersist
    @PreUpdate
    private void encryptPassword() {
        if ( this.password != null && !password.startsWith("$2a$")) {
            this.password = new BCryptPasswordEncoder().encode(password);
        }
    }
}
