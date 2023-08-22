package com.winnow.bestchoice.domain;

import com.winnow.bestchoice.type.MemberStatus;
import com.winnow.bestchoice.type.Provider;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, name = "nickname")
  private String nickname;

  @Column(nullable = false, unique = true, name = "email")
  private String email;

  @Column(nullable = false, name = "social_id")
  private String socialId;

  @Column(nullable = false, name = "status")
  @Enumerated(EnumType.STRING)
  private MemberStatus status;

  @Column(nullable = false, name = "provider")
  @Enumerated(EnumType.STRING)
  private Provider provider;

  @CreatedDate
  @Column(nullable = false, name = "created_date")
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(nullable = false, name = "modified_date")
  private LocalDateTime modifiedDate;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
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
