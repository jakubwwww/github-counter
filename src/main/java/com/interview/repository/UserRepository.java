package com.interview.repository;

import com.interview.model.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInformation, String> {

}
