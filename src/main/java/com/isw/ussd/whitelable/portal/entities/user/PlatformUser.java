package com.isw.ussd.whitelable.portal.entities.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class PlatformUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private long userId;

    @Column(name = "username", unique = true, length = 20)
    private String username;

    @Column(name = "cust_no", unique = true, length = 20)
    private String custNo;

    @Column(name = "password")
    private String password;

    @Column(nullable = false, name = "msisdn")
    private String phoneNumber;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(name = "primary_account_number", unique = true)
    private String primaryAccountNumber;

    @Column(name = "transaction_pin")
    private String transactionPin;

    @Column(unique = true, name = "bvn")
    private String bvn;

    @Column(unique = true, name = "nin")
    private String nin;

     @Column(name = "onboard_mode")
    private String onboardMode; // Identifies the onboard type this user used to register based on the authentication type

    @Column(name = "bankid", unique = true)
    private String bankID; // ID that identifies the customer at the bank

    @Column(unique = true, name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "counter")
    private int counter;

    @Column(name = "can_transact")
    private boolean canTransact;

    @Column(name = "kyc_level")
    private int kycLevel;

    @Column(name = "profile_json")
    private String profileJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    @Column(name = "enabled")
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
