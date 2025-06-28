package pe.edu.vallegrande.movilsurbackend.domain.model.master;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "FIREBASE_ID", length = 50)
    private String firebaseId;

    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100)
    private String lastName;

    @Column(name = "DOCUMENT_TYPE", length = 3)
    private String documentType;

    @Column(name = "DOCUMENT_NUMBER", length = 20, unique = true)
    private String documentNumber;

    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @Column(name = "PHONE", length = 15)
    private String phone;

    @Column(name = "ADDRESS", length = 100)
    private String address;

    @Column(name = "USER_PHOTO")
    private String userPhoto;

    @Column(name = "USERNAME", length = 100)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "USER_TYPE", length = 20, nullable = false)
    private String userType;

    @Column(name = "STATUS", length = 10, nullable = false)
    private String status;
}
