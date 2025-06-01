package com.Aptech.userservice.Repositorys;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String createUserByProcedure(String userName, String email, String password) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_create_user");

        query.registerStoredProcedureParameter("userName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("password", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("userId", String.class, ParameterMode.OUT);

        query.setParameter("userName", userName);
        query.setParameter("email", email);
        query.setParameter("password", password);

        query.execute();

        return (String) query.getOutputParameterValue("userId");
    }
}
