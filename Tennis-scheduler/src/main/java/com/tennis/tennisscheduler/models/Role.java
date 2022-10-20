package com.tennis.tennisscheduler.models;

import com.tennis.tennisscheduler.models.enumes.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private UserType roleName;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY,cascade =  CascadeType.PERSIST)
    private List<Person> persons;

    @Override
    public String getAuthority() {
        return roleName.toString();
    }
}
