package com.haseeb.assetledger.Repository;

import com.haseeb.assetledger.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
