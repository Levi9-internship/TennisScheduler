package com.tennis.tennisscheduler.model;

import com.tennis.tennisscheduler.model.enumes.UserType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private UserType roleName;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY,cascade =  CascadeType.PERSIST)
    private List<Person> persons;

    @Override
    public String getAuthority() {
        return roleName.toString();
    }
}
