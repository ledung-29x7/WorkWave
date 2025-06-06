package com.Aptech.userservice.Repositorys;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PermissionRepo {
    @PersistenceContext
    private EntityManager em;

    public List<String> getPermissionCodesByUserAndProject(String userId, String projectId) {
        return em.createNativeQuery("CALL sp_get_permission_codes_by_user_project(:userId, :projectId)")
                .setParameter("userId", userId)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public List<String> getGlobalPermissionCodesByUserId(String userId) {
        return em.createNativeQuery("CALL sp_get_permission_codes_by_user_global(:userId)")
                .setParameter("userId", userId)
                .getResultList();
    }

}
