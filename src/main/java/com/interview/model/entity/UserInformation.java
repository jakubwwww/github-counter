package com.interview.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation {
    @Id
    private String login;
    private Integer requestCount;

    public void addRequestCount() {
        requestCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserInformation that = (UserInformation) o;
        return login != null && Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
