package com.projects.hypertrophyapp.endpoints.registration;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<PrivateUserEntity, String> {
    
    Optional<PrivateUserEntity> findUserByUsername(String username); 
}
