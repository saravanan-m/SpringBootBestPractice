package com.best.practice.BestPractice.repositories;

import com.best.practice.BestPractice.entities.AccountEntity;
import com.best.practice.BestPractice.entities.UserMasterEntity;
import com.best.practice.BestPractice.repositories.impl.UserMasterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
}
