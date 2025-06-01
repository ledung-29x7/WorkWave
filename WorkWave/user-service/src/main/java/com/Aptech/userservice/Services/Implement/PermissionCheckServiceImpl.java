package com.Aptech.userservice.Services.Implement;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.Aptech.userservice.Services.Interfaces.IPermissionCheckService;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionCheckServiceImpl implements IPermissionCheckService {

    private final EntityManager em;
    private final CacheManager cacheManager;

    @Cacheable(value = "permissionCheckCache", key = "#userId + ':' + #projectId + ':' + #permissionCode")
    @Override
    public boolean checkPermission(String userId, String projectId, String permissionCode) {
        Object result = em.createNativeQuery("CALL sp_check_user_permission(:userId, :projectId, :code)")
                .setParameter("userId", userId)
                .setParameter("projectId", projectId)
                .setParameter("code", permissionCode)
                .getSingleResult();

        return ((Number) result).intValue() > 0;
    }

    public void invalidatePermission(String userId, String projectId, String permissionCode) {
        String key = userId + ":" + projectId + ":" + permissionCode;
        cacheManager.getCache("permissionCheckCache").evict(key);
    }
}
