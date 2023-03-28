package com.group4.miniproject.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

@ToString
@Getter
@Builder
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE account SET is_deleted = true, dewlted_at=now() WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String password;

    @Setter
    private String accountId;

    @Setter
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<AccountRole> roles = new HashSet<>();

    //@JsonIgnoreProperties({"account"})
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Schedule> schedules = new ArrayList<>();

    @OneToOne(mappedBy = "account")
    @ToString.Exclude
    private SuccessLogin successLogin;

    @Setter
    private String email;

    @Setter
    private String department;

    @Setter
    private String position;

    @Setter
    private Long yearly;

    @Setter
    @Builder.Default
    private Boolean duty = Boolean.FALSE;

    private LocalDateTime deletedAt;

    @Setter
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // 암호화용
    public Account hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }
}
