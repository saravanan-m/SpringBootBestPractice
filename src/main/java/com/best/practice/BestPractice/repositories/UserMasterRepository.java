package com.best.practice.BestPractice.repositories;

import com.best.practice.BestPractice.entities.UserMasterEntity;
import com.best.practice.BestPractice.repositories.impl.UserMasterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMasterEntity,Long>, UserMasterRepositoryCustom {

    @Query(value = "select * from user_master where id =:inpId",nativeQuery = true)
    UserMasterEntity getUserById(@Param("inpId") Long id);

    UserMasterEntity getUserByName(String name);
}
