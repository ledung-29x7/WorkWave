DROP DATABASE IF EXISTS UserServiceFix;
CREATE DATABASE UserServiceFix;
USE UserServiceFix;
create table Permission
(
    PermissionId int auto_increment
        primary key,
    Code         varchar(100)                  not null,
    Description  text                          null,
    Scope        varchar(20) default 'PROJECT' not null,
    constraint Code
        unique (Code)
);

create table ProjectStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) not null
);

create table Role
(
    RoleId   int auto_increment
        primary key,
    RoleName varchar(50)      not null,
    IsGlobal bit default b'0' null,
    constraint RoleName
        unique (RoleName)
);

create table RolePermission
(
    RoleId       int not null,
    PermissionId int not null,
    primary key (RoleId, PermissionId),
    constraint FK_RolePermission_Role
        foreign key (RoleId) references Role (RoleId),
    constraint RolePermission_ibfk_1
        foreign key (RoleId) references Role (RoleId),
    constraint RolePermission_ibfk_2
        foreign key (PermissionId) references Permission (PermissionId),
    constraint FK_RolePermission_Permission
        foreign key (PermissionId) references Permission (PermissionId)
);

create table Users
(
    UserId    varchar(36)                          not null
        primary key,
    UserName  varchar(255)                         not null,
    Email     varchar(255)                         not null,
    Password  varchar(255)                         not null,
    IsActive  tinyint(1) default 1                 null,
    CreatedAt datetime   default CURRENT_TIMESTAMP null,
    UpdatedAt datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint Email
        unique (Email)
);

create table Project
(
    ProjectId   varchar(36)                        not null
        primary key,
    Name        varchar(255)                       not null,
    Description text                               null,
    CreatedBy   varchar(36)                        null,
    CreatedAt   datetime default CURRENT_TIMESTAMP null,
    StartDate   date                               null,
    EndDate     date                               null,
    StatusId    int                                null,
    UpdatedAt   datetime                           null,
    UpdatedBy   varchar(36)                        null,
    constraint FK_Project_CreatedBy
        foreign key (CreatedBy) references Users (UserId),
    constraint FK_Project_Status
        foreign key (StatusId) references ProjectStatus (StatusId)
);

create table ProjectRoleAssignment
(
    AssignmentId varchar(36)                        not null
        primary key,
    UserId       varchar(36)                        null,
    ProjectId    varchar(36)                        null,
    RoleId       int                                not null,
    AssignedAt   datetime default CURRENT_TIMESTAMP null,
    constraint UserId
        unique (UserId, ProjectId, RoleId),
    constraint FK_PRA_Project
        foreign key (ProjectId) references Project (ProjectId),
    constraint FK_PRA_Role
        foreign key (RoleId) references Role (RoleId),
    constraint FK_PRA_User
        foreign key (UserId) references Users (UserId)
);

create table UserGlobalRole
(
    UserId     varchar(36)                        not null,
    RoleId     int                                not null,
    AssignedAt datetime default CURRENT_TIMESTAMP null,
    primary key (UserId, RoleId),
    constraint UserGlobalRole_ibfk_1
        foreign key (UserId) references Users (UserId),
    constraint UserGlobalRole_ibfk_2
        foreign key (RoleId) references Role (RoleId)
);

create index RoleId
    on UserGlobalRole (RoleId);

create
    definer = root@`%` procedure existsByEmail(IN p_email varchar(255))
begin
    SELECT EXISTS(SELECT 1 FROM `Users`  WHERE Users.Email = p_email);
end;

create
    definer = root@`%` procedure findByEmail(IN p_email varchar(255))
BEGIN
    SELECT
        u.UserId,
        u.UserName,
        u.Email,
        u.Password,
        u.IsActive,
        u.CreatedAt,
        u.UpdatedAt
    FROM `Users` u
    WHERE u.Email = p_email;
END;

create
    definer = root@`%` procedure sp_add_permission_to_role(IN p_roleId int, IN p_permissionId int)
BEGIN
    INSERT INTO RolePermission(RoleId, PermissionId)
    VALUES (p_roleId, p_permissionId);
END;

create
    definer = root@`%` procedure sp_assign_user_to_project(IN p_assignmentId char(36), IN p_userId varchar(36),
                                                           IN p_projectId varchar(36), IN p_roleId int)
BEGIN
    INSERT INTO ProjectRoleAssignment(AssignmentId, UserId, ProjectId, RoleId, AssignedAt)
    VALUES (p_assignmentId, p_userId, p_projectId, p_roleId, CURRENT_TIMESTAMP);
END;

create
    definer = root@`%` procedure sp_check_user_permission(IN p_userId varchar(36), IN p_projectId varchar(36),
                                                          IN p_permissionCode varchar(100))
BEGIN
    SELECT COUNT(*) AS PermissionCount
    FROM ProjectRoleAssignment pra
    JOIN RolePermission rp ON pra.RoleId = rp.RoleId
    JOIN Permission p ON rp.PermissionId = p.PermissionId
    WHERE pra.UserId = p_userId
      AND pra.ProjectId = p_projectId
      AND p.Code = p_permissionCode;
END;

create
    definer = root@`%` procedure sp_create_permission(IN p_code varchar(100), IN p_description text)
BEGIN
    INSERT INTO Permission (Code, Description) VALUES (p_code, p_description);
END;

create
    definer = root@`%` procedure sp_create_project(IN id varchar(36), IN p_name varchar(255), IN p_description text,
                                                   IN p_createdBy char(36), IN p_startDate date, IN p_endDate date,
                                                   IN p_statusId int)
BEGIN
    INSERT INTO Project(ProjectId, Name, Description, CreatedBy, CreatedAt,StartDate,EndDate,StatusId)
    VALUES (id, p_name, p_description, p_createdBy, CURRENT_TIMESTAMP,p_startDate, p_endDate, p_statusId);
END;

create
    definer = root@`%` procedure sp_create_role(IN roleName varchar(50))
BEGIN
    INSERT INTO Role (RoleName) VALUES (roleName);
END;

create
    definer = root@`%` procedure sp_create_user(IN p_userName varchar(255), IN p_email varchar(255),
                                                IN p_password varchar(255), OUT userId varchar(36))
BEGIN
    DECLARE uid varchar(36);
    SET uid = UUID();

    INSERT INTO Users (UserId, UserName, Email, Password, IsActive, CreatedAt, UpdatedAt)
    VALUES (uid, userName, email, password, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

    SET userId = uid;
END;

create
    definer = root@`%` procedure sp_delete_permission(IN p_id int)
BEGIN
    DELETE FROM Permission WHERE PermissionId = p_id;
END;

create
    definer = root@`%` procedure sp_delete_project(IN p_projectId varchar(36))
BEGIN
    DELETE FROM Project WHERE ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_delete_role(IN p_roleId int)
BEGIN
    DECLARE userCount INT;
    SELECT COUNT(*) INTO userCount FROM ProjectRoleAssignment WHERE RoleId = p_roleId;

    IF userCount = 0 THEN
        DELETE FROM Role WHERE RoleId = p_roleId;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Role is in use and cannot be deleted';
    END IF;
END;

create
    definer = root@`%` procedure sp_delete_user(IN p_userId varchar(36))
BEGIN
    DELETE FROM Users WHERE UserId = p_userId;
END;

create
    definer = root@`%` procedure sp_find_projects_by_user(IN p_userId varchar(36))
BEGIN
    SELECT p.*
    FROM Project p
    JOIN ProjectRoleAssignment pra ON pra.ProjectId = p.ProjectId
    WHERE pra.UserId = p_userId
    ORDER BY p.CreatedAt DESC;
END;

create
    definer = root@`%` procedure sp_get_permission_codes_by_user_global(IN userId varchar(36))
BEGIN
    SELECT DISTINCT p.Code
    FROM UserGlobalRole ugr
    JOIN Role r ON ugr.RoleId = r.RoleId
    JOIN RolePermission rp ON r.RoleId = rp.RoleId
    JOIN Permission p ON rp.PermissionId = p.PermissionId
    WHERE ugr.UserId = userId
      AND r.IsGlobal = 1
      AND p.Scope = 'GLOBAL';
END;

create
    definer = root@`%` procedure sp_get_permission_codes_by_user_project(IN UserId varchar(36), IN ProjectId varchar(36))
BEGIN
    SELECT p.Code
    FROM ProjectRoleAssignment pra
    JOIN RolePermission rp ON pra.RoleId = rp.RoleId
    JOIN Permission p ON rp.PermissionId = p.PermissionId
    WHERE pra.UserId = UserId AND pra.ProjectId = ProjectId;
END;

create
    definer = root@`%` procedure sp_get_permissions_by_role(IN p_roleId int)
BEGIN
    SELECT p.PermissionId, p.Code, p.Description
    FROM Permission p
    JOIN RolePermission rp ON rp.PermissionId = p.PermissionId
    WHERE rp.RoleId = p_roleId;
END;

create
    definer = root@`%` procedure sp_get_project_members(IN p_projectId varchar(36))
BEGIN
    SELECT u.UserId, u.UserName, r.RoleId, r.RoleName
    FROM ProjectRoleAssignment pra
    JOIN Users u ON pra.UserId = u.UserId
    JOIN Role r ON pra.RoleId = r.RoleId
    WHERE pra.ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_get_projects_by_user(IN p_userId varchar(36))
BEGIN
    SELECT p.ProjectId, p.Name, p.Description, p.CreatedBy, p.CreatedAt
    FROM Project p
    JOIN ProjectRoleAssignment pra ON p.ProjectId = pra.ProjectId
    WHERE pra.UserId = p_userId;
END;

create
    definer = root@`%` procedure sp_remove_permission_from_role(IN p_roleId int, IN p_permissionId int)
BEGIN
    DELETE FROM RolePermission
    WHERE RoleId = p_roleId AND PermissionId = p_permissionId;
END;

create
    definer = root@`%` procedure sp_remove_user_from_project(IN p_userId varchar(36), IN p_projectId varchar(36))
BEGIN
    DELETE FROM ProjectRoleAssignment
    WHERE UserId = p_userId AND ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_search_users_with_paging(IN keyword varchar(255), IN pageNumber int, IN pageSize int)
BEGIN
    -- Tính offset
    DECLARE offsetValue INT;
    SET offsetValue = (pageNumber - 1) * pageSize;

    -- Tổng số dòng phù hợp
    SELECT COUNT(*) INTO @TotalItems
    FROM Users
    WHERE UserName LIKE CONCAT('%', keyword, '%')
       OR Email LIKE CONCAT('%', keyword, '%');

    -- Tính số trang
    SET @TotalPages = CEIL(@TotalItems / pageSize);

    -- Trả kết quả tìm kiếm phân trang
    SELECT
        u.UserId,
        u.UserName,
        u.Email,
        u.IsActive,
        @TotalItems AS totalItems,
        pageNumber AS pageNumber,
        pageSize AS pageSize,
        @TotalPages AS totalPages
    FROM Users u
    WHERE u.UserName LIKE CONCAT('%', keyword, '%')
       OR u.Email LIKE CONCAT('%', keyword, '%')
    ORDER BY u.CreatedAt DESC
    LIMIT pageSize OFFSET offsetValue;
END;

create
    definer = root@`%` procedure sp_update_permission(IN p_id int, IN p_description text)
BEGIN
    UPDATE Permission SET Description = p_description WHERE PermissionId = p_id;
END;

create
    definer = root@`%` procedure sp_update_project(IN p_projectId varchar(36), IN p_name varchar(255),
                                                   IN p_description text, IN p_startDate date, IN p_endDate date,
                                                   IN p_statusId int, IN p_updatedBy varchar(36))
BEGIN
    UPDATE Project
    SET Name = p_name,
        Description = p_description,
        StartDate = p_startDate,
        EndDate = p_endDate,
        StatusId = p_statusId,
        UpdatedBy = p_updatedBy,
        UpdatedAt = CURRENT_TIMESTAMP
    WHERE ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_update_role(IN p_roleId int, IN p_roleName varchar(50))
BEGIN
    UPDATE Role SET RoleName = p_roleName WHERE RoleId = p_roleId;
END;

create
    definer = root@`%` procedure sp_update_user(IN p_userId varchar(36), IN p_userName varchar(255),
                                                IN p_email varchar(255))
BEGIN
    UPDATE Users
    SET UserName = p_userName,
        Email = p_email,
        UpdatedAt = CURRENT_TIMESTAMP
    WHERE UserId = p_userId;
END;

create
    definer = root@`%` procedure sp_update_user_role_in_project(IN p_userId varchar(36), IN p_projectId varchar(36),
                                                                IN p_newRoleId int)
BEGIN
    UPDATE ProjectRoleAssignment
    SET RoleId = p_newRoleId, AssignedAt = CURRENT_TIMESTAMP
    WHERE UserId = p_userId AND ProjectId = p_projectId;
END;

INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (1, 'PERMISSION_CREATE', 'Tạo quyền mới', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (2, 'PERMISSION_EDIT', 'Sửa quyền', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (3, 'PERMISSION_DELETE', 'Xoá quyền', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (4, 'PROJECT_CREATE', 'Tạo dự án', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (5, 'PROJECT_EDIT', 'Sửa thông tin dự án', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (6, 'PROJECT_DELETE', 'Xoá dự án', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (7, 'PROJECT_VIEW_MEMBER', 'Xem thành viên dự án', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (8, 'PROJECT_ASSIGN_MEMBER', 'Gán user vào dự án', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (9, 'PROJECT_UPDATE_ROLE', 'Thay đổi vai trò của user trong dự án', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (10, 'PROJECT_REMOVE_MEMBER', 'Xoá user khỏi dự án', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (11, 'ROLE_CREATE', 'Tạo vai trò', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (12, 'ROLE_EDIT', 'Sửa vai trò', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (13, 'ROLE_DELETE', 'Xoá vai trò', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (14, 'ROLE_VIEW_PERMISSION', 'Xem quyền trong vai trò', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (15, 'ROLE_ASSIGN_PERMISSION', 'Gán quyền vào vai trò', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (16, 'ROLE_REMOVE_PERMISSION', 'Gỡ quyền khỏi vai trò', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (17, 'USER_CREATE', 'Tạo user', 'GLOBAL');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (18, 'USER_VIEW', 'Xem thông tin user', 'GLOBAL');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (19, 'USER_EDIT', 'Sửa user', 'GLOBAL');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (20, 'USER_DELETE', 'Xoá user', 'GLOBAL');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (21, 'USER_SEARCH', 'Tìm kiếm user', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (22, 'EPIC_CREATE', 'Tạo mới Epic', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (23, 'EPIC_VIEW', 'Xem Epic', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (24, 'EPIC_EDIT', 'Chỉnh sửa Epic', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (25, 'EPIC_DELETE', 'Xoá Epic', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (26, 'SPRINT_CREATE', 'Tạo mới Sprint', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (27, 'SPRINT_VIEW', 'Xem Sprint', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (28, 'SPRINT_EDIT', 'Chỉnh sửa Sprint', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (29, 'SPRINT_DELETE', 'Xoá Sprint', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (30, 'STORY_CREATE', 'Tạo mới User Story', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (31, 'STORY_VIEW', 'Xem User Story', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (32, 'STORY_EDIT', 'Chỉnh sửa User Story', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (33, 'STORY_DELETE', 'Xoá User Story', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (34, 'TASK_CREATE', 'Tạo mới Task', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (35, 'TASK_VIEW', 'Xem Task', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (36, 'TASK_EDIT', 'Chỉnh sửa Task', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (37, 'TASK_DELETE', 'Xoá Task', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (38, 'TESTCASE_CREATE', 'Tạo Test Case', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (39, 'TESTCASE_VIEW', 'Xem Test Case', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (40, 'TESTCASE_UPDATE', 'Cập nhật Test Case', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (41, 'TESTCASE_DELETE', 'Xoá Test Case', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (42, 'BUG_CREATE', 'Tạo Bug', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (43, 'BUG_UPDATE', 'Cập nhật Bug', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (44, 'BUG_VIEW', 'Xem Bug', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (45, 'BUG_DELETE', 'Xóa Bug', 'PROJECT');
INSERT INTO UserServiceFix.Permission (PermissionId, Code, Description, Scope) VALUES (46, 'USER_VIEW_ALL', 'Xem tất cả user', 'GLOBAL');

INSERT INTO UserServiceFix.ProjectStatus (StatusId, StatusName) VALUES (1, 'Not Started');
INSERT INTO UserServiceFix.ProjectStatus (StatusId, StatusName) VALUES (2, 'In Progress');
INSERT INTO UserServiceFix.ProjectStatus (StatusId, StatusName) VALUES (3, 'Completed');
INSERT INTO UserServiceFix.ProjectStatus (StatusId, StatusName) VALUES (4, 'On Hold');
INSERT INTO UserServiceFix.ProjectStatus (StatusId, StatusName) VALUES (5, 'Cancelled');
INSERT INTO UserServiceFix.Role (RoleId, RoleName, IsGlobal) VALUES (1, 'Admin', false);
INSERT INTO UserServiceFix.Role (RoleId, RoleName, IsGlobal) VALUES (2, 'Product Owner', false);
INSERT INTO UserServiceFix.Role (RoleId, RoleName, IsGlobal) VALUES (3, 'Scrum Master', false);
INSERT INTO UserServiceFix.Role (RoleId, RoleName, IsGlobal) VALUES (4, 'Developer', false);
INSERT INTO UserServiceFix.Role (RoleId, RoleName, IsGlobal) VALUES (5, 'Tester', false);
INSERT INTO UserServiceFix.Role (RoleId, RoleName, IsGlobal) VALUES (6, 'Viewer', false);
INSERT INTO UserServiceFix.Role (RoleId, RoleName, IsGlobal) VALUES (7, 'Super Admin', true);
INSERT INTO UserServiceFix.Role (RoleId, RoleName, IsGlobal) VALUES (8, 'General User', true);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 1);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 1);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 1);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 1);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 2);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 2);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 2);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 2);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 3);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 3);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 3);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 4);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 4);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 4);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 4);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 5);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 5);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 5);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 5);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 6);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 6);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 6);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 7);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 7);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (6, 7);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 7);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 7);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 8);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 8);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 8);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 8);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 9);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 9);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 9);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 9);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 10);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 10);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 10);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 11);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 11);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 11);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 11);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 12);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 12);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 12);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 12);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 13);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 13);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 13);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 14);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 14);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 14);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 14);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 15);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 15);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 15);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 15);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 16);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 16);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 16);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 16);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 17);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 17);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (6, 18);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 18);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 18);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 19);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 19);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 20);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 21);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 21);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 21);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 21);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 22);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 22);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 22);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 22);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 23);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 23);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (6, 23);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 23);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 23);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 24);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 24);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 24);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 24);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 25);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 25);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 25);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 25);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 26);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 26);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 26);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 26);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 27);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 27);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 27);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (6, 27);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 27);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 27);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 28);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 28);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 28);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 28);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 28);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 29);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 29);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 29);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 29);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 29);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 30);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 30);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 30);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 30);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 30);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 30);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 31);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 31);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 31);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 31);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (6, 31);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 31);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 31);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 32);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 32);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 32);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 32);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 32);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 32);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 33);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 33);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 33);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 33);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 33);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 34);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 34);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 34);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 34);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 34);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 34);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 35);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 35);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 35);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 35);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (6, 35);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 35);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 35);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 36);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 36);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 36);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 36);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 36);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 36);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 37);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 37);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (3, 37);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 37);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 37);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 38);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 38);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (5, 38);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 38);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 38);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 39);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 39);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (5, 39);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (6, 39);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 39);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 39);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 40);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 40);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (5, 40);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 40);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 40);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 41);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 41);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (5, 41);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 41);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 41);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 42);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 42);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 42);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (5, 42);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 42);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 42);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 43);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 43);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 43);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (5, 43);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 43);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 43);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 44);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 44);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (4, 44);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (5, 44);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (6, 44);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 44);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 44);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (1, 45);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (2, 45);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (5, 45);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 45);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (8, 45);
INSERT INTO UserServiceFix.RolePermission (RoleId, PermissionId) VALUES (7, 46);
INSERT INTO UserServiceFix.Users (UserId, UserName, Email, Password, IsActive, CreatedAt, UpdatedAt) VALUES ('bc23f2d5-2845-464a-a537-f763944b3122', 'SuperAdmin', 'superadmin@gmail.com', '$2a$10$3i79gQdYmufezTJQEsEszOw1jDIiiccs8L2Z5CxDd6hfknjpuncUe', 1, '2025-06-05 14:22:16', null);
INSERT INTO UserServiceFix.UserGlobalRole (UserId, RoleId, AssignedAt) VALUES ('bc23f2d5-2845-464a-a537-f763944b3122', 7, '2025-06-05 14:22:16');



DROP DATABASE IF EXISTS project_service_db;

CREATE DATABASE project_service_db;
USE project_service_db;
create table EpicStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) charset utf8mb3 not null,
    constraint StatusName
        unique (StatusName)
);

create table Epic
(
    EpicId      int auto_increment
        primary key,
    ProjectId   varchar(36)                        not null,
    Name        varchar(255) charset utf8mb3       not null,
    Description text                               null,
    StatusId    int                                null,
    StartDate   date                               null,
    EndDate     date                               null,
    CreatedAt   datetime default CURRENT_TIMESTAMP null,
    UpdatedAt   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    CreatedBy   varchar(36)                        null,
    UpdatedBy   varchar(36)                        null,
    constraint Epic_ibfk_1
        foreign key (StatusId) references EpicStatus (StatusId)
);

create index StatusId
    on Epic (StatusId);

create table Priority
(
    PriorityId   int auto_increment
        primary key,
    PriorityName varchar(50) charset utf8mb3 not null,
    constraint PriorityName
        unique (PriorityName)
);

create table ProjectStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) charset utf8mb3 not null,
    constraint StatusName
        unique (StatusName)
);

create table Project
(
    ProjectId   varchar(36)                        not null
        primary key,
    Name        varchar(255) charset utf8mb3       not null,
    Description text                               null,
    StartDate   date                               not null,
    EndDate     date                               null,
    StatusId    int                                null,
    CreatedAt   datetime default CURRENT_TIMESTAMP null,
    UpdatedAt   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    CreatedBy   varchar(36)                        null,
    UpdatedBy   varchar(36)                        null,
    constraint Project_ibfk_1
        foreign key (StatusId) references ProjectStatus (StatusId)
);

create index StatusId
    on Project (StatusId);

create table SprintStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) charset utf8mb3 not null,
    constraint StatusName
        unique (StatusName)
);

create table Sprint
(
    SprintId  int auto_increment
        primary key,
    ProjectId varchar(36)                        not null,
    Name      varchar(255) charset utf8mb3       not null,
    StartDate date                               not null,
    EndDate   date                               not null,
    StatusId  int                                null,
    Goal      text                               null,
    CreatedAt datetime default CURRENT_TIMESTAMP null,
    UpdatedAt datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    CreatedBy varchar(36)                        null,
    UpdatedBy varchar(36)                        null,
    constraint Sprint_ibfk_1
        foreign key (StatusId) references SprintStatus (StatusId)
);

create index StatusId
    on Sprint (StatusId);

create table TaskStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) charset utf8mb3 not null,
    constraint StatusName
        unique (StatusName)
);

create table UserLookup
(
    userId    varchar(36)                        not null
        primary key,
    userName  varchar(255) charset utf8mb3       not null,
    email     varchar(255)                       not null,
    createdAt datetime default CURRENT_TIMESTAMP null
);

create table UserStoryStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) charset utf8mb3 not null,
    constraint StatusName
        unique (StatusName)
);

create table UserStory
(
    StoryId     int auto_increment
        primary key,
    EpicId      int                                null,
    SprintId    int                                null,
    Name        varchar(255) charset utf8mb3       not null,
    Description text                               null,
    PriorityId  int                                null,
    AssignedTo  varchar(36)                        null,
    StatusId    int                                null,
    CreatedAt   datetime default CURRENT_TIMESTAMP null,
    UpdatedAt   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    CreatedBy   varchar(36)                        null,
    UpdatedBy   varchar(36)                        null,
    ProjectId   varchar(36)                        null,
    constraint UserStory_ibfk_1
        foreign key (EpicId) references Epic (EpicId)
            on delete cascade,
    constraint UserStory_ibfk_2
        foreign key (SprintId) references Sprint (SprintId)
            on delete set null,
    constraint UserStory_ibfk_3
        foreign key (PriorityId) references Priority (PriorityId),
    constraint UserStory_ibfk_4
        foreign key (StatusId) references UserStoryStatus (StatusId)
);

create table Task
(
    TaskId         int auto_increment
        primary key,
    StoryId        int                                not null,
    AssignedTo     varchar(36)                        null,
    Name           varchar(255) charset utf8mb3       not null,
    Description    text                               null,
    StatusId       int                                null,
    EstimatedHours int                                null,
    LoggedHours    int      default 0                 null,
    RemainingHours int                                null,
    CreatedAt      datetime default CURRENT_TIMESTAMP null,
    UpdatedAt      datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    CreatedBy      varchar(36)                        null,
    UpdatedBy      varchar(36)                        null,
    constraint Task_ibfk_1
        foreign key (StoryId) references UserStory (StoryId)
            on delete cascade,
    constraint Task_ibfk_2
        foreign key (StatusId) references TaskStatus (StatusId),
    check (`EstimatedHours` >= 0),
    check (`LoggedHours` >= 0),
    check (`RemainingHours` >= 0)
);

create index StatusId
    on Task (StatusId);

create index StoryId
    on Task (StoryId);

create index EpicId
    on UserStory (EpicId);

create index PriorityId
    on UserStory (PriorityId);

create index SprintId
    on UserStory (SprintId);

create index StatusId
    on UserStory (StatusId);

create
    definer = root@`%` procedure CreateEpic(IN projectId varchar(36), IN epicName varchar(255), IN description text,
                                            IN status int, IN startDate date, IN endDate date, IN createdBy varchar(36))
BEGIN
    INSERT INTO Epic (ProjectId, Name, Description, StatusId, StartDate, EndDate, CreatedAt, UpdatedAt, CreatedBy, UpdatedBy)
    VALUES (projectId, epicName, description, status, startDate, endDate, NOW(), NOW(), createdBy, createdBy);
END;

create
    definer = root@`%` procedure CreateProject(IN p_ProjectId varchar(36), IN p_Name varchar(255),
                                               IN p_Description text, IN p_StartDate date, IN p_EndDate date,
                                               IN p_StatusId int, IN p_CreatedBy varchar(36))
BEGIN
    INSERT INTO Project (ProjectId, Name, Description, StartDate, EndDate, StatusId, CreatedBy, UpdatedBy)
    VALUES (uuid(), p_Name, p_Description, p_StartDate, p_EndDate, p_StatusId, p_CreatedBy, p_CreatedBy);
END;

create
    definer = root@`%` procedure CreateSprint(IN projectId varchar(36), IN sprintName varchar(255), IN startDate date,
                                              IN endDate date, IN status int, IN goal text, IN createdBy varchar(36))
BEGIN
    INSERT INTO Sprint (ProjectId, Name, StartDate, EndDate, StatusId, Goal, CreatedAt, UpdatedAt, CreatedBy, UpdatedBy)
    VALUES (projectId, sprintName, startDate, endDate, status, goal, NOW(), NOW(), createdBy, createdBy);
END;

create
    definer = root@`%` procedure CreateTask(IN storyId int, IN assignedTo varchar(36), IN taskName varchar(255),
                                            IN description text, IN statusId int, IN estimatedHours int,
                                            IN createdBy varchar(36))
BEGIN
    INSERT INTO Task (StoryId, AssignedTo, Name, Description, StatusId, EstimatedHours, CreatedBy)
    VALUES (storyId, assignedTo, taskName, description, statusId, estimatedHours, createdBy);
END;

create
    definer = root@`%` procedure CreateUserLockup(IN p_userId varchar(36), IN p_username varchar(255),
                                                  IN p_email varchar(255))
begin
    INSERT INTO UserLookup (userId, userName, email)
    VALUES (p_userId,p_username,p_email);
end;

create
    definer = root@`%` procedure CreateUserStory(IN p_EpicId int, IN p_SprintId int, IN p_Name varchar(255),
                                                 IN p_Description text, IN p_PriorityId int,
                                                 IN p_AssignedTo varchar(36), IN p_StatusId int,
                                                 IN p_CreatedBy varchar(36), IN p_ProjectId varchar(36))
BEGIN
    INSERT INTO UserStory (EpicId, SprintId, Name, Description, PriorityId,AssignedTo, StatusId, CreatedBy,ProjectId)
    VALUES (p_EpicId, p_SprintId, p_Name, p_Description, p_PriorityId,p_AssignedTo, p_StatusId, p_CreatedBy, p_ProjectId);
END;

create
    definer = root@`%` procedure DeleteEpic(IN epicId int)
BEGIN
    DELETE FROM Epic WHERE EpicId = epicId;
END;

create
    definer = root@`%` procedure DeleteProject(IN projectId_param varchar(36))
BEGIN
    DELETE FROM Project WHERE ProjectId = projectId_param;
END;

create
    definer = root@`%` procedure DeleteSprint(IN sprintId int)
BEGIN
    DELETE FROM Sprint WHERE SprintId = sprintId;
END;

create
    definer = root@`%` procedure DeleteTask(IN p_taskId int)
BEGIN
    DELETE FROM Task as T WHERE T.TaskId = p_taskId;
END;

create
    definer = root@`%` procedure DeleteUserStory(IN p_StoryId int)
BEGIN
    DELETE FROM UserStory WHERE StoryId = p_StoryId;
END;

create
    definer = root@`%` procedure ExistsByProjectName(IN ProjectName_param varchar(255))
BEGIN
    SELECT COUNT(*) FROM Project WHERE Name = ProjectName_param;
END;

create
    definer = root@`%` procedure ExistsByUserId(IN p_userId varchar(36))
begin
    SELECT EXISTS(SELECT 1 FROM `UserLookup` WHERE userId = p_userId );
end;

create
    definer = root@`%` procedure GetAllUserStories()
BEGIN
    SELECT * FROM UserStory;
END;

create
    definer = root@`%` procedure GetAllUserStoryBySprintId(IN p_SprintId int)
BEGIN
    SELECT
        us.StoryId,
        us.EpicId,
        us.SprintId,
        us.Name,
        us.Description,
        p.PriorityName AS Priority,
        uss.StatusName AS Status,
        us.CreatedAt,
        us.UpdatedAt,
        us.CreatedBy,
        us.UpdatedBy
    FROM UserStory us
    LEFT JOIN Priority p ON us.PriorityId = p.PriorityId
    JOIN UserStoryStatus uss ON us.StatusId = uss.StatusId
    WHERE us.SprintId = p_SprintId;
END;

create
    definer = root@`%` procedure GetEpicById(IN epicId int)
BEGIN
    SELECT
    EpicId AS epic_id,
    Epic.ProjectId AS project_id,
    Epic.Name AS name,
    Epic.Description AS description,
    Epic.StatusId AS status_id,
    StartDate AS start_date,
    EndDate AS end_date,
    CreatedAt AS created_at,
    UpdatedAt AS updated_at,
    CreatedBy AS created_by,
    UpdatedBy AS updated_by
    FROM Epic WHERE Epic.EpicId = epicId;
END;

create
    definer = root@`%` procedure GetEpicsByProject(IN projectId varchar(36))
BEGIN
    SELECT
    EpicId AS epic_id,
    Epic.ProjectId AS project_id,
    Epic.Name AS name,
    Epic.Description AS description,
    Epic.StatusId AS status_id,
    StartDate AS start_date,
    EndDate AS end_date,
    CreatedAt AS created_at,
    UpdatedAt AS updated_at,
    CreatedBy AS created_by,
    UpdatedBy AS updated_by
FROM Epic
WHERE Epic.ProjectId = projectId;
END;

create
    definer = root@`%` procedure GetProjectById(IN projectId_param varchar(36))
BEGIN
    SELECT * FROM Project WHERE ProjectId = projectId_param;
END;

create
    definer = root@`%` procedure GetProjectByName(IN projectName_param varchar(255))
BEGIN
    SELECT * FROM Project WHERE Name = projectName_param;
END;

create
    definer = root@`%` procedure GetSprintById(IN p_sprintId int)
BEGIN
    SELECT
        SprintId,
        projectid,
        name,
        startdate,
        enddate,
        statusid,
        goal,
        createdat,
        updatedat,
        createdby,
        updatedby
    FROM Sprint WHERE SprintId = p_sprintId;
END;

create
    definer = root@`%` procedure GetSprintsByProject(IN projectId varchar(36))
BEGIN
    SELECT
        SprintId AS sprint_id,
        ProjectId AS project_id,
        Name AS name,
        StartDate AS start_date,
        EndDate AS end_date,
        StatusId AS status_id,
        Goal AS  goal,
        CreatedAt AS created_at,
    UpdatedAt AS updated_at,
    CreatedBy AS created_by,
    UpdatedBy AS updated_by
        FROM Sprint WHERE Sprint.ProjectId = projectId;
END;

create
    definer = root@`%` procedure GetTaskByAssignedToAndProjectId(IN p_assignedTo varchar(36), IN p_projectId varchar(36))
BEGIN
    SELECT
    t.taskid AS task_id,
    t.storyid AS story_id,
    t.assignedto AS assigned_to,
    t.name AS name,
    t.description AS description,
    t.statusid AS status,
    t.estimatedhours AS estimated_hours,
    t.loggedhours AS logged_hours,
    t.remaininghours AS remaining_hours,
    t.createdat AS created_at,
    t.updatedat AS updated_at,
    t.createdby AS created_by,
    t.updatedby AS updated_by
    FROM Task t
    INNER JOIN UserStory us ON t.StoryId = us.StoryId
    INNER JOIN Epic e ON us.EpicId = e.EpicId
    INNER JOIN Project p ON e.ProjectId = p.ProjectId
    WHERE
        (p_assignedTo IS NULL OR p_assignedTo = '' OR t.AssignedTo = p_assignedTo)
        AND (p_projectId IS NULL OR p_projectId = '' OR p.ProjectId = p_projectId);
END;

create
    definer = root@`%` procedure GetTaskById(IN p_taskId int)
BEGIN
    SELECT
    taskid AS task_id,
    storyid AS story_id,
    assignedto AS assigned_to,
    name AS name,
    description AS description,
    statusid AS status,
    estimatedhours AS estimated_hours,
    loggedhours AS logged_hours,
    remaininghours AS remaining_hours,
    createdat AS created_at,
    updatedat AS updated_at,
    createdby AS created_by,
    updatedby AS updated_by
    FROM Task WHERE TaskId = p_taskId;
END;

create
    definer = root@`%` procedure GetTasksByStoryId(IN storyId int)
BEGIN
    SELECT
        TaskId AS task_id,
        Task.StoryId AS story_id,
        AssignedTo AS assigned_To,
        Name AS name,
        Description AS description,
        StatusId AS status,
        EstimatedHours AS estimated_Hours,
        LoggedHours AS logged_Hours,
        RemainingHours AS remaining_Hours,
        CreatedAt AS created_At,
        UpdatedAt AS update_At,
        CreatedBy AS created_By,
        UpdatedBy AS updated_By
    FROM Task WHERE  Task.StoryId = storyId;
END;

create
    definer = root@`%` procedure GetUserStoriesByEpicId(IN p_EpicId int)
BEGIN
    SELECT
        StoryId AS story_id,
        EpicId AS epic_id,
        SprintId AS sprint_id,
        Name AS name,
        Description AS description,
        PriorityId AS priority,
        AssignedTo AS assigned_to,
        StatusId AS status,
        CreatedAt AS created_At,
        UpdatedAt AS updated_At,
        CreatedBy AS created_By,
        UpdatedBy AS updated_By,
        ProjectId AS project_id
    FROM UserStory WHERE UserStory.EpicId = p_EpicId;
END;

create
    definer = root@`%` procedure GetUserStoriesByProjectId(IN p_ProjectId varchar(36))
BEGIN
    SELECT
        StoryId AS story_id,
        EpicId AS epic_id,
        SprintId AS sprint_id,
        Name AS name,
        Description AS description,
        PriorityId AS priority,
        AssignedTo AS assigned_to,
        StatusId AS status,
        CreatedAt AS created_At,
        UpdatedAt AS updated_At,
        CreatedBy AS created_By,
        UpdatedBy AS updated_By,
        ProjectId AS project_id
    FROM UserStory WHERE UserStory.ProjectId = p_ProjectId;
END;

create
    definer = root@`%` procedure GetUserStoriesBySprintId(IN p_sprintId int)
BEGIN
    SELECT
        StoryId AS story_id,
        EpicId AS epic_id,
        SprintId AS sprint_id,
        Name AS name,
        Description AS description,
        PriorityId AS priority,
        AssignedTo AS assigned_to,
        StatusId AS status,
        CreatedAt AS created_At,
        UpdatedAt AS updated_At,
        CreatedBy AS created_By,
        UpdatedBy AS updated_By,
        ProjectId AS project_id
    FROM UserStory WHERE UserStory.SprintId = p_sprintId;
END;

create
    definer = root@`%` procedure GetUserStoriesByUserId(IN p_AssignedTo varchar(36))
BEGIN
    SELECT
        StoryId AS story_id,
        EpicId AS epic_id,
        SprintId AS sprint_id,
        Name AS name,
        Description AS description,
        PriorityId AS priority,
        AssignedTo AS assigned_to,
        StatusId AS status,
        CreatedAt AS created_At,
        UpdatedAt AS updated_At,
        CreatedBy AS created_By,
        UpdatedBy AS updated_By,
        ProjectId AS project_id
    FROM UserStory WHERE UserStory.AssignedTo = p_AssignedTo;
END;

create
    definer = root@`%` procedure GetUserStoryById(IN p_StoryId int)
BEGIN
    SELECT
        StoryId AS story_id,
        EpicId AS epic_id,
        SprintId AS sprint_id,
        Name AS name,
        Description AS description,
        PriorityId AS priority,
        StatusId AS status,
        CreatedAt AS created_At,
        UpdatedAt AS updated_At,
        CreatedBy AS created_By,
        UpdatedBy AS updated_By
    FROM UserStory WHERE StoryId = p_StoryId;
END;

create
    definer = root@`%` procedure UpdateEpic(IN p_epicId int, IN epicName varchar(255), IN description text,
                                            IN status int, IN startDate date, IN endDate date, IN updatedBy varchar(36))
BEGIN
    UPDATE Epic
    SET Name = epicName,
        Description = description,
        StatusId = status,
        StartDate = startDate,
        EndDate = endDate,
        UpdatedAt = NOW(),
        UpdatedBy = updatedBy
    WHERE EpicId = p_epicId;
END;

create
    definer = root@`%` procedure UpdateProject(IN p_ProjectId varchar(36), IN p_Name varchar(255),
                                               IN p_Description text, IN p_StartDate date, IN p_EndDate date,
                                               IN p_StatusId int, IN p_UpdatedBy varchar(36))
BEGIN
    UPDATE Project
    SET Name = p_Name,
        Description = p_Description,
        StartDate = p_StartDate,
        EndDate = p_EndDate,
        StatusId = p_StatusId,
        UpdatedBy = p_UpdatedBy
    WHERE ProjectId = p_ProjectId;
END;

create
    definer = root@`%` procedure UpdateSprint(IN p_sprintId int, IN sprintName varchar(255), IN startDate date,
                                              IN endDate date, IN status int, IN goal text, IN updatedBy varchar(36))
BEGIN
    UPDATE Sprint
    SET Name = sprintName,
        StartDate = startDate,
        EndDate = endDate,
        StatusId = status,
        Goal = goal,
        UpdatedAt = NOW(),
        UpdatedBy = updatedBy
    WHERE SprintId = p_sprintId;
END;

create
    definer = root@`%` procedure UpdateTask(IN p_taskId int, IN assignedTo varchar(36), IN taskName varchar(255),
                                            IN description text, IN statusId int, IN estimatedHours int,
                                            IN loggedHours int, IN remainingHours int, IN updatedBy varchar(36))
BEGIN
    UPDATE Task
    SET AssignedTo = assignedTo,
        Name = taskName,
        Description = description,
        StatusId = statusId,
        EstimatedHours = estimatedHours,
        LoggedHours = loggedHours,
        RemainingHours = remainingHours,
        UpdatedBy = updatedBy,
        UpdatedAt = NOW()
    WHERE TaskId = p_taskId;
END;

create
    definer = root@`%` procedure UpdateUserStory(IN p_StoryId int, IN p_EpicId int, IN p_SprintId int,
                                                 IN p_Name varchar(255), IN p_Description text, IN p_PriorityId int,
                                                 IN p_AssignedTo varchar(36), IN p_StatusId int,
                                                 IN p_UpdatedBy varchar(36))
BEGIN
    UPDATE UserStory
    SET EpicId = p_EpicId,
        SprintId = p_SprintId,
        Name = p_Name,
        Description = p_Description,
        PriorityId = p_PriorityId,
        AssignedTo = p_AssignedTo,
        StatusId = p_StatusId,
        UpdatedBy = p_UpdatedBy
    WHERE StoryId = p_StoryId;
END;

create
    definer = root@`%` procedure deleteUserLookup(IN p_userId varchar(36))
BEGIN
    DELETE FROM UserLookup WHERE userId = p_userId;
END;

create
    definer = root@`%` procedure existProjectById(IN p_projectId varchar(36))
BEGIN
    SELECT EXISTS(SELECT 1 FROM `Project` WHERE ProjectId = p_projectId);
END;

create
    definer = root@`%` procedure getLatestCreatedStoryId(IN p_createdBy varchar(36))
BEGIN
    SELECT StoryId
    FROM UserStory
    WHERE CreatedBy = p_createdBy
    ORDER BY CreatedAt DESC
    LIMIT 1;
END;

create
    definer = root@`%` procedure getLatestCreatedTaskId(IN p_storyId int, IN p_createdBy varchar(36))
BEGIN
    SELECT TaskId
    FROM Task
    WHERE StoryId = p_storyId AND CreatedBy = p_createdBy
    ORDER BY CreatedAt DESC
    LIMIT 1;
END;

create
    definer = root@`%` procedure sp_create_project(IN p_projectId varchar(36), IN p_name varchar(255),
                                                   IN p_description text, IN p_startDate date, IN p_endDate date,
                                                   IN p_statusId int, IN p_createdBy varchar(36))
BEGIN
    INSERT INTO Project (
        ProjectId, Name, Description, StartDate, EndDate, StatusId, CreatedBy
    ) VALUES (
        p_projectId, p_name, p_description, p_startDate, p_endDate, p_statusId, p_createdBy
    );
END;

create
    definer = root@`%` procedure sp_delete_project(IN p_projectId varchar(36))
BEGIN
    DELETE FROM Project WHERE ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_get_all_projects()
BEGIN
    SELECT
        ProjectId AS project_id,
        Name AS name,
        Description AS description,
        StartDate AS start_date,
        EndDate AS end_date,
        StatusId AS status_id,
        CreatedAt AS created_at,
        UpdatedAt AS updated_at,
        CreatedBy AS created_by,
        UpdatedBy AS updated_by
    FROM Project;
END;

create
    definer = root@`%` procedure sp_get_project_by_id(IN p_projectId varchar(36))
BEGIN
    SELECT 
        ProjectId AS project_id,
        Name AS name,
        Description AS description,
        StartDate AS start_date,
        EndDate AS end_date,
        StatusId AS status_id,
        CreatedAt AS created_at,
        UpdatedAt AS updated_at,
        CreatedBy AS created_by,
        UpdatedBy AS updated_by
    FROM Project
    WHERE ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_update_project(IN p_projectId varchar(36), IN p_name varchar(255),
                                                   IN p_description text, IN p_startDate date, IN p_endDate date,
                                                   IN p_statusId int, IN p_updatedBy varchar(36))
BEGIN
    UPDATE Project
    SET
        Name = p_name,
        Description = p_description,
        StartDate = p_startDate,
        EndDate = p_endDate,
        StatusId = p_statusId,
        UpdatedBy = p_updatedBy,
        UpdatedAt = CURRENT_TIMESTAMP
    WHERE ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_update_user(IN p_userId varchar(36), IN p_userName varchar(255),
                                                IN p_email varchar(255))
BEGIN
    UPDATE UserLookup
    SET UserName = p_userName,
        Email = p_email
    WHERE UserId = p_userId;
END;



INSERT INTO project_service_db.EpicStatus (StatusId, StatusName) VALUES (3, 'Completed');
INSERT INTO project_service_db.EpicStatus (StatusId, StatusName) VALUES (2, 'In Progress');
INSERT INTO project_service_db.EpicStatus (StatusId, StatusName) VALUES (1, 'Not Started');
INSERT INTO project_service_db.Priority (PriorityId, PriorityName) VALUES (3, 'High');
INSERT INTO project_service_db.Priority (PriorityId, PriorityName) VALUES (4, 'Highest');
INSERT INTO project_service_db.Priority (PriorityId, PriorityName) VALUES (1, 'Low');
INSERT INTO project_service_db.Priority (PriorityId, PriorityName) VALUES (2, 'Medium');
INSERT INTO ProjectStatus (StatusId, StatusName) VALUES (1, 'Not Started');
INSERT INTO ProjectStatus (StatusId, StatusName) VALUES (2, 'In Progress');
INSERT INTO ProjectStatus (StatusId, StatusName) VALUES (3, 'Completed');
INSERT INTO ProjectStatus (StatusId, StatusName) VALUES (4, 'On Hold');
INSERT INTO ProjectStatus (StatusId, StatusName) VALUES (5, 'Cancelled');
INSERT INTO project_service_db.SprintStatus (StatusId, StatusName) VALUES (2, 'Active');
INSERT INTO project_service_db.SprintStatus (StatusId, StatusName) VALUES (4, 'Cancelled');
INSERT INTO project_service_db.SprintStatus (StatusId, StatusName) VALUES (3, 'Completed');
INSERT INTO project_service_db.SprintStatus (StatusId, StatusName) VALUES (1, 'Planned');
INSERT INTO project_service_db.TaskStatus (StatusId, StatusName) VALUES (3, 'Blocked');
INSERT INTO project_service_db.TaskStatus (StatusId, StatusName) VALUES (5, 'Done');
INSERT INTO project_service_db.TaskStatus (StatusId, StatusName) VALUES (2, 'In Progress');
INSERT INTO project_service_db.TaskStatus (StatusId, StatusName) VALUES (4, 'In Review');
INSERT INTO project_service_db.TaskStatus (StatusId, StatusName) VALUES (1, 'To Do');
INSERT INTO project_service_db.UserStoryStatus (StatusId, StatusName) VALUES (4, 'Completed');
INSERT INTO project_service_db.UserStoryStatus (StatusId, StatusName) VALUES (2, 'In Progress');
INSERT INTO project_service_db.UserStoryStatus (StatusId, StatusName) VALUES (3, 'In Review');
INSERT INTO project_service_db.UserStoryStatus (StatusId, StatusName) VALUES (1, 'To Do');

DROP DATABASE IF EXISTS test_service_db;

CREATE DATABASE test_service_db;
USE test_service_db;
create table ProjectLookup
(
    projectId   varchar(36)                        not null
        primary key,
    name        varchar(255) charset utf8mb3       not null,
    description text                               null,
    createdBy   varchar(36)                        null,
    createdAt   datetime default CURRENT_TIMESTAMP null,
    StartDate   date                               null,
    EndDate     date                               null,
    StatusId    int                                null,
    UpdatedAt   datetime                           null,
    UpdatedBy   varchar(36)                        null
);

create table TestStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) charset utf8mb3 not null,
    constraint StatusName
        unique (StatusName)
);

create table TestCase
(
    TestCaseId     int auto_increment
        primary key,
    ProjectId      varchar(36)                        not null,
    StoryId        int                                null,
    TestName       varchar(255) charset utf8mb3       not null,
    Description    text                               null,
    ExpectedResult text                               not null,
    ActualResult   text                               null,
    StatusId       int                                null,
    CreatedBy      varchar(36)                        not null,
    ExecutedBy     varchar(36)                        null,
    CreatedAt      datetime default CURRENT_TIMESTAMP null,
    UpdatedAt      datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint TestCase_ibfk_1
        foreign key (StatusId) references TestStatus (StatusId)
);

create index StatusId
    on TestCase (StatusId);

create table TestExecution
(
    TestExecutionId int auto_increment
        primary key,
    TestCaseId      int                                not null,
    ExecutedBy      varchar(36)                        not null,
    ExecutionDate   datetime default CURRENT_TIMESTAMP null,
    StatusId        int                                null,
    Comment         text                               null,
    constraint TestExecution_ibfk_1
        foreign key (TestCaseId) references TestCase (TestCaseId)
            on delete cascade,
    constraint TestExecution_ibfk_2
        foreign key (StatusId) references TestStatus (StatusId)
);

create index StatusId
    on TestExecution (StatusId);

create index TestCaseId
    on TestExecution (TestCaseId);

create table UserLookup
(
    userId    varchar(36)                        not null
        primary key,
    userName  varchar(255) charset utf8mb3       not null,
    email     varchar(255)                       not null,
    createdAt datetime default CURRENT_TIMESTAMP null
);

create table UserStoryLookup
(
    storyId     int                                not null
        primary key,
    name        varchar(255) charset utf8mb3       not null,
    description text                               null,
    epicId      int                                null,
    priorityId  int                                null,
    AssignedTo  varchar(36)                        null,
    statusId    int                                null,
    createdBy   varchar(36)                        null,
    createdAt   datetime default CURRENT_TIMESTAMP null,
    ProjectId   varchar(36)                        null
);

create table TaskLookup
(
    taskId         int                                not null
        primary key,
    storyId        int                                not null,
    name           varchar(255) charset utf8mb3       not null,
    description    text                               null,
    statusId       int                                null,
    estimatedHours int                                null,
    loggedHours    int      default 0                 null,
    remainingHours int                                null,
    assignedTo     varchar(36)                        null,
    createdBy      varchar(36)                        null,
    createdAt      datetime default CURRENT_TIMESTAMP null,
    updatedBy      varchar(36)                        null,
    updatedAt      datetime                           null on update CURRENT_TIMESTAMP,
    constraint fk_story
        foreign key (storyId) references UserStoryLookup (storyId)
            on delete cascade
);

create
    definer = root@`%` procedure CreateUserLockup(IN p_userId varchar(36), IN p_username varchar(255),
                                                  IN p_email varchar(255))
begin
    INSERT INTO UserLookup (userId, userName, email)
    VALUES (p_userId,p_username,p_email);
end;

create
    definer = root@`%` procedure ExistsByProjectId(IN p_projectId varchar(36))
begin
    SELECT EXISTS(SELECT 1 FROM `ProjectLookup` WHERE projectId = p_projectId );
end;

create
    definer = root@`%` procedure ExistsByTaskLookupId(IN p_taskId int)
begin
    SELECT EXISTS(SELECT 1 FROM `TaskLookup`  AS TL  WHERE TL.taskId = p_taskId );
end;

create
    definer = root@`%` procedure ExistsByUserId(IN p_userId varchar(36))
begin
    SELECT EXISTS(SELECT 1 FROM `UserLookup` WHERE userId = p_userId );
end;

create
    definer = root@`%` procedure ExistsByUserStoryLookupId(IN p_storyId int)
begin
    SELECT EXISTS(SELECT 1 FROM `UserStoryLookup`  AS US  WHERE US.storyId = p_storyId );
end;

create
    definer = root@`%` procedure GetExecutionSummary(IN projectId varchar(36))
BEGIN
    SELECT
        COUNT(*)                                      AS total,
        SUM(IF(te.StatusId = 1, 1, 0))                   AS passed,
        SUM(IF(te.StatusId = 2, 1, 0))                   AS failed,
        SUM(IF(te.StatusId = 3, 1, 0)) AS inProgress
    FROM TestExecution te
    JOIN TestCase tc ON te.TestCaseId = tc.TestCaseId
    WHERE tc.ProjectId = projectId;
END;

create
    definer = root@`%` procedure SearchTestExecutions(IN executedBy varchar(36), IN statusId int)
BEGIN
    SELECT
        testexecutionid AS test_execution_id,
        testcaseid AS test_case_id,
        executedby AS executed_by,
        executiondate AS execution_date,
        statusid AS status_id,
        comment AS comment 
    FROM TestExecution
    WHERE (executedby = executedBy OR executedby IS NULL)
      AND (statusid = statusId OR statusid IS NULL);
END;

create
    definer = root@`%` procedure deleteProjectLookup(IN p_projectId varchar(36))
BEGIN
    DELETE FROM ProjectLookup WHERE projectId = p_projectId;
END;

create
    definer = root@`%` procedure deleteTaskLookup(IN p_taskId int)
BEGIN
    DELETE FROM TaskLookup WHERE taskId = p_taskId;
END;

create
    definer = root@`%` procedure deleteUserLookup(IN p_userId varchar(36))
BEGIN
    DELETE FROM UserLookup WHERE userId = p_userId;
END;

create
    definer = root@`%` procedure deleteUserStoryLookup(IN p_storyId int)
BEGIN
    DELETE FROM UserStoryLookup WHERE storyId = p_storyId;
END;

create
    definer = root@`%` procedure saveProjectLookup(IN p_projectId varchar(36), IN p_name varchar(255),
                                                   IN p_description text, IN p_createdBy varchar(36),
                                                   IN p_startDate date, IN p_endDate date, IN p_statusId int)
BEGIN
    INSERT INTO ProjectLookup (projectId, name, description, createdBy, createdAt,StartDate,EndDate,StatusId)
    VALUES (p_projectId, p_name, p_description, p_createdBy, CURRENT_TIMESTAMP,p_startDate, p_endDate, p_statusId);
END;

create
    definer = root@`%` procedure saveTaskLookup(IN p_taskId int, IN p_storyId int, IN p_name varchar(255),
                                                IN p_description text, IN p_statusId int, IN p_estimatedHours int,
                                                IN p_loggedHours int, IN p_remainingHours int,
                                                IN p_assignedTo varchar(36), IN p_createdBy varchar(36))
BEGIN
    INSERT INTO TaskLookup (
        taskId, storyId, name, description,
        statusId, estimatedHours, loggedHours, remainingHours,
        assignedTo, createdBy, createdAt
    )
    VALUES (
        p_taskId, p_storyId, p_name, p_description,
        p_statusId, p_estimatedHours, p_loggedHours, p_remainingHours,
        p_assignedTo, p_createdBy, CURRENT_TIMESTAMP
    );
END;

create
    definer = root@`%` procedure saveUserStoryLookup(IN p_storyId int, IN p_name varchar(255), IN p_description text,
                                                     IN p_epicId int, IN p_priorityId int, IN p_AssignedTo varchar(36),
                                                     IN p_statusId int, IN p_createdBy varchar(36),
                                                     IN p_ProjectId varchar(36))
BEGIN
    INSERT INTO UserStoryLookup (
        storyId, name, description,
        epicId, priorityId,AssignedTo, statusId, createdBy, createdAt, ProjectId
    ) VALUES (
        p_storyId, p_name, p_description,
        p_epicId, p_priorityId,p_AssignedTo, p_statusId, p_createdBy, CURRENT_TIMESTAMP, p_ProjectId
    );
END;

create
    definer = root@`%` procedure sp_create_test_case(IN p_projectId varchar(36), IN p_storyId int,
                                                     IN p_testName varchar(255), IN p_description text,
                                                     IN p_expectedResult text, IN p_actualResult text,
                                                     IN p_statusId int, IN p_createdBy varchar(36),
                                                     IN p_executedBy varchar(36))
BEGIN
    INSERT INTO TestCase (
        ProjectId, StoryId, TestName, Description,
        ExpectedResult, ActualResult, StatusId,
        CreatedBy, ExecutedBy
    ) VALUES (
        p_projectId, p_storyId, p_testName, p_description,
        p_expectedResult, p_actualResult, p_statusId,
        p_createdBy, p_executedBy
    );
END;

create
    definer = root@`%` procedure sp_create_test_execution(IN p_testCaseId int, IN p_executedBy varchar(36),
                                                          IN p_statusId int, IN p_comment text)
BEGIN
    INSERT INTO TestExecution (TestCaseId, ExecutedBy, StatusId, Comment)
    VALUES (p_testCaseId, p_executedBy, p_statusId, p_comment);
END;

create
    definer = root@`%` procedure sp_delete_test_case(IN p_testCaseId int)
BEGIN
    DELETE FROM TestCase
    WHERE TestCaseId = p_testCaseId;
END;

create
    definer = root@`%` procedure sp_delete_test_execution(IN p_testExecutionId int)
BEGIN
    DELETE FROM TestExecution
    WHERE TestExecutionId = p_testExecutionId;
END;

create
    definer = root@`%` procedure sp_get_executions_by_testcase(IN p_testCaseId int)
BEGIN
    SELECT
        testexecutionid AS test_execution_id,
        testcaseid AS test_case_id,
        executedby AS executed_by,
        executiondate AS execution_date,
        statusid AS status_id,
        comment AS comment
    FROM TestExecution
    WHERE TestCaseId = p_testCaseId
    ORDER BY ExecutionDate DESC;
END;

create
    definer = root@`%` procedure sp_get_test_case_by_id(IN p_testCaseId int)
BEGIN
    SELECT
        testcaseid AS test_case_id,
        projectid As project_id,
        storyid AS story_id,
        testname AS test_name,
        description AS description,
        expectedresult AS expected_result,
        actualresult AS actual_result,
        statusid AS status_id,
        createdby AS created_by,
        executedby AS executed_by,
        createdat AS created_at,
        updatedat AS updated_at
    FROM TestCase
    WHERE TestCaseId = p_testCaseId;
END;

create
    definer = root@`%` procedure sp_get_test_cases_by_project(IN p_projectId varchar(36))
BEGIN
    SELECT 
         testcaseid AS test_case_id,
        projectid As project_id,
        storyid AS story_id,
        testname AS test_name,
        description AS description,
        expectedresult AS expected_result,
        actualresult AS actual_result,
        statusid AS status_id,
        createdby AS created_by,
        executedby AS executed_by,
        createdat AS created_at,
        updatedat AS updated_at
    FROM TestCase
    WHERE ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_get_test_execution_by_id(IN p_testExecutionId int)
BEGIN
    SELECT
        testexecutionid AS test_execution_id,
        testcaseid AS test_case_id,
        executedby AS executed_by,
        executiondate AS execution_date,
        statusid AS status_id,
        comment AS comment  
    FROM TestExecution
    WHERE TestExecutionId = p_testExecutionId;
END;

create
    definer = root@`%` procedure sp_update_test_case(IN p_testCaseId int, IN p_testName varchar(255),
                                                     IN p_description text, IN p_expectedResult text,
                                                     IN p_actualResult text, IN p_statusId int,
                                                     IN p_executedBy varchar(36))
BEGIN
    UPDATE TestCase
    SET
        TestName = p_testName,
        Description = p_description,
        ExpectedResult = p_expectedResult,
        ActualResult = p_actualResult,
        StatusId = p_statusId,
        ExecutedBy = p_executedBy,
        UpdatedAt = CURRENT_TIMESTAMP
    WHERE TestCaseId = p_testCaseId;
END;

create
    definer = root@`%` procedure sp_update_test_execution(IN p_testExecutionId int, IN p_executedBy varchar(36),
                                                          IN p_statusId int, IN p_comment text)
BEGIN
    UPDATE TestExecution
    SET ExecutedBy = p_executedBy,
        StatusId = p_statusId,
        Comment = p_comment,
        ExecutionDate = CURRENT_TIMESTAMP
    WHERE TestExecutionId = p_testExecutionId;
END;

create
    definer = root@`%` procedure sp_update_user(IN p_userId varchar(36), IN p_userName varchar(255),
                                                IN p_email varchar(255))
BEGIN
    UPDATE UserLookup
    SET UserName = p_userName,
        Email = p_email
    WHERE UserId = p_userId;
END;

create
    definer = root@`%` procedure updateProjectLookup(IN p_projectId varchar(36), IN p_name varchar(255),
                                                     IN p_description text, IN p_startDate date, IN p_endDate date,
                                                     IN p_statusId int, IN p_updatedBy varchar(36))
BEGIN
    UPDATE ProjectLookup
    SET
        name = p_name,
        description = p_description,
        StartDate = p_startDate,
        EndDate = p_endDate,
        StatusId = p_statusId,
        UpdatedBy = p_updatedBy,
        UpdatedAt = CURRENT_TIMESTAMP
    WHERE projectId = p_projectId;
END;

create
    definer = root@`%` procedure updateTaskLookup(IN p_taskId int, IN p_name varchar(255), IN p_description text,
                                                  IN p_statusId int, IN p_estimatedHours int, IN p_loggedHours int,
                                                  IN p_remainingHours int, IN p_assignedTo varchar(36),
                                                  IN p_updatedBy varchar(36))
BEGIN
    UPDATE TaskLookup
    SET
        name = p_name,
        description = p_description,
        statusId = p_statusId,
        estimatedHours = p_estimatedHours,
        loggedHours = p_loggedHours,
        remainingHours = p_remainingHours,
        assignedTo = p_assignedTo,
        updatedBy = p_updatedBy,
        updatedAt = CURRENT_TIMESTAMP
    WHERE taskId = p_taskId;
END;

create
    definer = root@`%` procedure updateUserStoryLookup(IN p_storyId int, IN p_name varchar(255), IN p_description text,
                                                       IN p_epicId int, IN p_priorityId int,
                                                       IN p_AssignedTo varchar(36), IN p_statusId int)
BEGIN
    UPDATE UserStoryLookup
    SET
        name = p_name,
        description = p_description,
        epicId = p_epicId,
        priorityId = p_priorityId,
        AssignedTo = p_AssignedTo,
        statusId = p_statusId
    WHERE storyId = p_storyId;
END;

INSERT INTO test_service_db.TestStatus (StatusId, StatusName) VALUES (4, 'Blocked');
INSERT INTO test_service_db.TestStatus (StatusId, StatusName) VALUES (6, 'Deferred');
INSERT INTO test_service_db.TestStatus (StatusId, StatusName) VALUES (3, 'Failed');
INSERT INTO test_service_db.TestStatus (StatusId, StatusName) VALUES (5, 'In Progress');
INSERT INTO test_service_db.TestStatus (StatusId, StatusName) VALUES (1, 'Not Executed');
INSERT INTO test_service_db.TestStatus (StatusId, StatusName) VALUES (2, 'Passed');


DROP DATABASE IF EXISTS bug_tracking_service_db;
CREATE DATABASE bug_tracking_service_db;
USE bug_tracking_service_db;

create table BugStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) charset utf8mb3 not null,
    constraint StatusName
        unique (StatusName)
);

create table Priority
(
    PriorityId   int auto_increment
        primary key,
    PriorityName varchar(50) charset utf8mb3 not null,
    constraint PriorityName
        unique (PriorityName)
);

create table ProjectLookup
(
    projectId   varchar(36)                        not null
        primary key,
    name        varchar(255) charset utf8mb3       not null,
    description text                               null,
    createdBy   varchar(36)                        null,
    createdAt   datetime default CURRENT_TIMESTAMP null,
    StartDate   date                               null,
    EndDate     date                               null,
    StatusId    int                                null,
    UpdatedAt   datetime                           null,
    UpdatedBy   varchar(36)                        null
);

create table Severity
(
    SeverityId   int auto_increment
        primary key,
    SeverityName varchar(50) charset utf8mb3 not null,
    constraint SeverityName
        unique (SeverityName)
);

create table Bug
(
    BugId       int auto_increment
        primary key,
    ProjectId   varchar(36)                        not null,
    StoryId     int                                null,
    TaskId      int                                null,
    ReportedBy  varchar(36)                        not null,
    AssignedTo  varchar(36)                        null,
    Title       varchar(255) charset utf8mb3       not null,
    Description text                               null,
    SeverityId  int                                null,
    PriorityId  int                                null,
    StatusId    int                                null,
    CreatedAt   datetime default CURRENT_TIMESTAMP null,
    UpdatedAt   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    CreatedBy   varchar(36)                        null,
    UpdatedBy   varchar(36)                        null,
    constraint Bug_ibfk_1
        foreign key (SeverityId) references Severity (SeverityId),
    constraint Bug_ibfk_2
        foreign key (PriorityId) references Priority (PriorityId),
    constraint Bug_ibfk_3
        foreign key (StatusId) references BugStatus (StatusId)
);

create index PriorityId
    on Bug (PriorityId);

create index SeverityId
    on Bug (SeverityId);

create index StatusId
    on Bug (StatusId);

create table BugHistory
(
    BugHistoryId int auto_increment
        primary key,
    BugId        int                                not null,
    StatusId     int                                null,
    UpdatedBy    varchar(36)                        not null,
    UpdatedAt    datetime default CURRENT_TIMESTAMP null,
    Comment      text                               null,
    constraint BugHistory_ibfk_1
        foreign key (BugId) references Bug (BugId),
    constraint BugHistory_ibfk_2
        foreign key (StatusId) references BugStatus (StatusId)
);

create index BugId
    on BugHistory (BugId);

create index StatusId
    on BugHistory (StatusId);

create table UserLookup
(
    userId    varchar(36)                        not null
        primary key,
    userName  varchar(255) charset utf8mb3       not null,
    email     varchar(255)                       not null,
    createdAt datetime default CURRENT_TIMESTAMP null
);

create table UserStoryLookup
(
    storyId     int                                not null
        primary key,
    name        varchar(255) charset utf8mb3       not null,
    description text                               null,
    epicId      int                                null,
    priorityId  int                                null,
    AssignedTo  varchar(36)                        null,
    statusId    int                                null,
    createdBy   varchar(36)                        null,
    createdAt   datetime default CURRENT_TIMESTAMP null,
    ProjectId   varchar(36)                        null
);

create table TaskLookup
(
    taskId         int                                not null
        primary key,
    storyId        int                                not null,
    name           varchar(255) charset utf8mb3       not null,
    description    text                               null,
    statusId       int                                null,
    estimatedHours int                                null,
    loggedHours    int      default 0                 null,
    remainingHours int                                null,
    assignedTo     varchar(36)                        null,
    createdBy      varchar(36)                        null,
    createdAt      datetime default CURRENT_TIMESTAMP null,
    updatedBy      varchar(36)                        null,
    updatedAt      datetime                           null on update CURRENT_TIMESTAMP,
    constraint fk_story
        foreign key (storyId) references UserStoryLookup (storyId)
            on delete cascade
);

create
    definer = root@`%` procedure CreateUserLockup(IN p_userId varchar(36), IN p_username varchar(255),
                                                  IN p_email varchar(255))
begin
    INSERT INTO UserLookup (userId, userName, email)
    VALUES (p_userId,p_username,p_email);
end;

create
    definer = root@`%` procedure ExistsByProjectId(IN p_projectId varchar(36))
begin
    SELECT EXISTS(SELECT 1 FROM `ProjectLookup` WHERE projectId = p_projectId );
end;

create
    definer = root@`%` procedure ExistsByTaskLookupId(IN p_taskId int)
begin
    SELECT EXISTS(SELECT 1 FROM `TaskLookup`  AS TL  WHERE TL.taskId = p_taskId );
end;

create
    definer = root@`%` procedure ExistsByUserId(IN p_userId varchar(36))
begin
    SELECT EXISTS(SELECT 1 FROM `UserLookup` WHERE userId = p_userId );
end;

create
    definer = root@`%` procedure ExistsByUserStoryLookupId(IN p_storyId int)
begin
    SELECT EXISTS(SELECT 1 FROM `UserStoryLookup`  AS US  WHERE US.storyId = p_storyId );
end;

create
    definer = root@`%` procedure deleteProjectLookup(IN p_projectId varchar(36))
BEGIN
    DELETE FROM ProjectLookup WHERE projectId = p_projectId;
END;

create
    definer = root@`%` procedure deleteTaskLookup(IN p_taskId int)
BEGIN
    DELETE FROM TaskLookup WHERE taskId = p_taskId;
END;

create
    definer = root@`%` procedure deleteUserLookup(IN p_userId varchar(36))
BEGIN
    DELETE FROM UserLookup WHERE userId = p_userId;
END;

create
    definer = root@`%` procedure deleteUserStoryLookup(IN p_storyId int)
BEGIN
    DELETE FROM UserStoryLookup WHERE storyId = p_storyId;
END;

create
    definer = root@`%` procedure saveProjectLookup(IN p_projectId varchar(36), IN p_name varchar(255),
                                                   IN p_description text, IN p_createdBy varchar(36),
                                                   IN p_startDate date, IN p_endDate date, IN p_statusId int)
BEGIN
    INSERT INTO ProjectLookup (projectId, name, description, createdBy, createdAt,StartDate,EndDate,StatusId)
    VALUES (p_projectId, p_name, p_description, p_createdBy, CURRENT_TIMESTAMP,p_startDate, p_endDate, p_statusId);
END;

create
    definer = root@`%` procedure saveTaskLookup(IN p_taskId int, IN p_storyId int, IN p_name varchar(255),
                                                IN p_description text, IN p_statusId int, IN p_estimatedHours int,
                                                IN p_loggedHours int, IN p_remainingHours int,
                                                IN p_assignedTo varchar(36), IN p_createdBy varchar(36))
BEGIN
    INSERT INTO TaskLookup (
        taskId, storyId, name, description,
        statusId, estimatedHours, loggedHours, remainingHours,
        assignedTo, createdBy, createdAt
    )
    VALUES (
        p_taskId, p_storyId, p_name, p_description,
        p_statusId, p_estimatedHours, p_loggedHours, p_remainingHours,
        p_assignedTo, p_createdBy, CURRENT_TIMESTAMP
    );
END;

create
    definer = root@`%` procedure saveUserStoryLookup(IN p_storyId int, IN p_name varchar(255), IN p_description text,
                                                     IN p_epicId int, IN p_priorityId int, IN p_AssignedTo varchar(36),
                                                     IN p_statusId int, IN p_createdBy varchar(36),
                                                     IN p_ProjectId varchar(36))
BEGIN
    INSERT INTO UserStoryLookup (
        storyId, name, description,
        epicId, priorityId,AssignedTo, statusId, createdBy, createdAt, ProjectId
    ) VALUES (
        p_storyId, p_name, p_description,
        p_epicId, p_priorityId,p_AssignedTo, p_statusId, p_createdBy, CURRENT_TIMESTAMP, p_ProjectId
    );
END;

create
    definer = root@`%` procedure sp_create_bug(IN p_projectId varchar(36), IN p_storyId int, IN p_taskId int,
                                               IN p_title varchar(255), IN p_description text,
                                               IN p_reportedBy varchar(36), IN p_assignedTo varchar(36),
                                               IN p_severityId int, IN p_priorityId int, IN p_statusId int,
                                               IN p_createdBy varchar(36))
BEGIN
    INSERT INTO Bug (
        ProjectId, StoryId, TaskId, Title, Description, ReportedBy, AssignedTo,
        SeverityId, PriorityId, StatusId,
        CreatedBy, UpdatedBy, CreatedAt, UpdatedAt
    )
    VALUES (
        p_projectId, p_storyId, p_taskId, p_title, p_description, p_reportedBy, p_assignedTo,
        p_severityId, p_priorityId, p_statusId,
        p_createdBy, p_createdBy, NOW(), NOW()
    );
END;

create
    definer = root@`%` procedure sp_create_bug_history(IN p_bugId int, IN p_statusId int, IN p_updatedBy varchar(36),
                                                       IN p_comment text)
BEGIN
    INSERT INTO BugHistory (
        BugId, StatusId, UpdatedBy, Comment, UpdatedAt
    )
    VALUES (
        p_bugId, p_statusId, p_updatedBy, p_comment, NOW()
    );
END;

create
    definer = root@`%` procedure sp_delete_bug(IN p_bugId int)
BEGIN
    DELETE FROM BugHistory WHERE BugId = p_bugId;
    DELETE FROM Bug WHERE BugId = p_bugId;
END;

create
    definer = root@`%` procedure sp_get_bug_details_by_id(IN p_bugId int)
begin
SELECT
        b.BugId,
        b.ProjectId,
        b.StoryId,
        b.TaskId,
        b.ReportedBy,
        b.AssignedTo,
        b.Title,
        b.Description,
        b.SeverityId,
        s.SeverityName,
        b.PriorityId,
        p.PriorityName,
        b.StatusId,
        bs.StatusName,
        b.CreatedAt,
        b.UpdatedAt,
        b.CreatedBy,
        b.UpdatedBy
    FROM Bug b
    LEFT JOIN Severity s ON b.SeverityId = s.SeverityId
    LEFT JOIN Priority p ON b.PriorityId = p.PriorityId
    LEFT JOIN BugStatus bs ON b.StatusId = bs.StatusId
    WHERE b.BugId = p_bugId;
end;

create
    definer = root@`%` procedure sp_get_bug_history(IN p_bugId int)
BEGIN
    SELECT
        bh.BugHistoryId,
        bh.BugId,
        bh.StatusId,
        bs.StatusName,
        bh.UpdatedBy,
        bh.UpdatedAt,
        bh.Comment
    FROM BugHistory bh
    LEFT JOIN BugStatus bs ON bh.StatusId = bs.StatusId
    WHERE bh.BugId = p_bugId
    ORDER BY bh.UpdatedAt DESC;
END;

create
    definer = root@`%` procedure sp_get_bugs_by_project(IN p_projectId varchar(36))
BEGIN
    SELECT 
        bugid AS bug_id,
        projectid AS project_id,
        storyid AS story_id,
        taskid AS task_id,
        reportedby AS reported_by,
        assignedto AS assigned_to,
        title AS title,
        description AS description,
        severityid AS severity_id,
        priorityid AS priority_id,
        statusid AS status_id,
        createdat AS created_at,
        updatedat AS updated_at,
        createdby AS created_by,
        updatedby AS updated_by
    FROM Bug
    WHERE ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_get_bugs_by_project_full(IN p_projectId varchar(36))
BEGIN
    SELECT
        b.BugId,
        b.ProjectId,
        b.StoryId,
        b.TaskId,
        b.ReportedBy,
        b.AssignedTo,
        b.Title,
        b.Description,
        b.SeverityId,
        s.SeverityName,
        b.PriorityId,
        p.PriorityName,
        b.StatusId,
        bs.StatusName,
        b.CreatedAt,
        b.UpdatedAt,
        b.CreatedBy,
        b.UpdatedBy
    FROM Bug b
    LEFT JOIN Severity s ON b.SeverityId = s.SeverityId
    LEFT JOIN Priority p ON b.PriorityId = p.PriorityId
    LEFT JOIN BugStatus bs ON b.StatusId = bs.StatusId
    WHERE b.ProjectId = p_projectId;
END;

create
    definer = root@`%` procedure sp_update_bug(IN p_bugId int, IN p_title varchar(255), IN p_description text,
                                               IN p_assignedTo varchar(36), IN p_severityId int, IN p_priorityId int,
                                               IN p_statusId int, IN p_updatedBy varchar(36))
BEGIN
    UPDATE Bug
    SET
        Title = p_title,
        Description = p_description,
        AssignedTo = p_assignedTo,
        SeverityId = p_severityId,
        PriorityId = p_priorityId,
        StatusId = p_statusId,
        UpdatedBy = p_updatedBy,
        UpdatedAt = NOW()
    WHERE BugId = p_bugId;
END;

create
    definer = root@`%` procedure sp_update_bug_with_history(IN p_bugId int, IN p_title varchar(255),
                                                            IN p_description text, IN p_assignedTo varchar(36),
                                                            IN p_severityId int, IN p_priorityId int, IN p_statusId int,
                                                            IN p_updatedBy varchar(36), IN p_comment text)
BEGIN
    DECLARE old_status_id INT;

    START TRANSACTION;

    -- Lấy Status cũ
    SELECT StatusId INTO old_status_id FROM Bug WHERE BugId = p_bugId;

    -- Cập nhật Bug
    UPDATE Bug
    SET
        Title = p_title,
        Description = p_description,
        AssignedTo = p_assignedTo,
        SeverityId = p_severityId,
        PriorityId = p_priorityId,
        StatusId = p_statusId,
        UpdatedBy = p_updatedBy,
        UpdatedAt = NOW()
    WHERE BugId = p_bugId;

    -- Nếu có thay đổi Status thì lưu vào History
    IF old_status_id <> p_statusId THEN
        INSERT INTO BugHistory (
            BugId, StatusId, UpdatedBy, Comment, UpdatedAt
        )
        VALUES (
            p_bugId, p_statusId, p_updatedBy, p_comment, NOW()
        );
    END IF;

    COMMIT;
END;

create
    definer = root@`%` procedure sp_update_user(IN p_userId varchar(36), IN p_userName varchar(255),
                                                IN p_email varchar(255))
BEGIN
    UPDATE UserLookup
    SET UserName = p_userName,
        Email = p_email
    WHERE UserId = p_userId;
END;

create
    definer = root@`%` procedure updateProjectLookup(IN p_projectId varchar(36), IN p_name varchar(255),
                                                     IN p_description text, IN p_startDate date, IN p_endDate date,
                                                     IN p_statusId int, IN p_updatedBy varchar(36))
BEGIN
    UPDATE ProjectLookup
    SET
        name = p_name,
        description = p_description,
        StartDate = p_startDate,
        EndDate = p_endDate,
        StatusId = p_statusId,
        UpdatedBy = p_updatedBy,
        UpdatedAt = CURRENT_TIMESTAMP
    WHERE projectId = p_projectId;
END;

create
    definer = root@`%` procedure updateTaskLookup(IN p_taskId int, IN p_name varchar(255), IN p_description text,
                                                  IN p_statusId int, IN p_estimatedHours int, IN p_loggedHours int,
                                                  IN p_remainingHours int, IN p_assignedTo varchar(36),
                                                  IN p_updatedBy varchar(36))
BEGIN
    UPDATE TaskLookup
    SET
        name = p_name,
        description = p_description,
        statusId = p_statusId,
        estimatedHours = p_estimatedHours,
        loggedHours = p_loggedHours,
        remainingHours = p_remainingHours,
        assignedTo = p_assignedTo,
        updatedBy = p_updatedBy,
        updatedAt = CURRENT_TIMESTAMP
    WHERE taskId = p_taskId;
END;

create
    definer = root@`%` procedure updateUserStoryLookup(IN p_storyId int, IN p_name varchar(255), IN p_description text,
                                                       IN p_epicId int, IN p_priorityId int,
                                                       IN p_AssignedTo varchar(36), IN p_statusId int)
BEGIN
    UPDATE UserStoryLookup
    SET
        name = p_name,
        description = p_description,
        epicId = p_epicId,
        priorityId = p_priorityId,
        AssignedTo = p_AssignedTo,
        statusId = p_statusId
    WHERE storyId = p_storyId;
END;

INSERT INTO bug_tracking_service_db.BugStatus (StatusId, StatusName) VALUES (4, 'Closed');
INSERT INTO bug_tracking_service_db.BugStatus (StatusId, StatusName) VALUES (2, 'In Progress');
INSERT INTO bug_tracking_service_db.BugStatus (StatusId, StatusName) VALUES (1, 'Open');
INSERT INTO bug_tracking_service_db.BugStatus (StatusId, StatusName) VALUES (5, 'Reopened');
INSERT INTO bug_tracking_service_db.BugStatus (StatusId, StatusName) VALUES (3, 'Resolved');
INSERT INTO Priority (PriorityId, PriorityName) VALUES (3, 'High');
INSERT INTO Priority (PriorityId, PriorityName) VALUES (4, 'Highest');
INSERT INTO Priority (PriorityId, PriorityName) VALUES (1, 'Low');
INSERT INTO Priority (PriorityId, PriorityName) VALUES (2, 'Medium');
INSERT INTO bug_tracking_service_db.Severity (SeverityId, SeverityName) VALUES (4, 'Blocker');
INSERT INTO bug_tracking_service_db.Severity (SeverityId, SeverityName) VALUES (3, 'Critical');
INSERT INTO bug_tracking_service_db.Severity (SeverityId, SeverityName) VALUES (2, 'Major');
INSERT INTO bug_tracking_service_db.Severity (SeverityId, SeverityName) VALUES (1, 'Minor');
DROP DATABASE IF EXISTS release_service_db;

CREATE DATABASE release_service_db;
USE release_service_db;

create table ProjectLookup
(
    projectId   varchar(36)                        not null
        primary key,
    name        varchar(255) charset utf8mb3       not null,
    description text                               null,
    createdBy   varchar(36)                        null,
    createdAt   datetime default CURRENT_TIMESTAMP null,
    StartDate   date                               null,
    EndDate     date                               null,
    StatusId    int                                null,
    UpdatedAt   datetime                           null,
    UpdatedBy   varchar(36)                        null
);

create table ReleaseStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) charset utf8mb3 not null,
    constraint StatusName
        unique (StatusName)
);

create table ReleaseManagement
(
    ReleaseId   int auto_increment
        primary key,
    ProjectId   varchar(36)                        not null,
    Version     varchar(50)                        not null,
    Description text                               null,
    ReleaseDate date                               not null,
    StatusId    int                                null,
    CreatedAt   datetime default CURRENT_TIMESTAMP null,
    UpdatedAt   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    CreatedBy   varchar(36)                        null,
    UpdatedBy   varchar(36)                        null,
    constraint ReleaseManagement_ibfk_1
        foreign key (StatusId) references ReleaseStatus (StatusId)
);

create index StatusId
    on ReleaseManagement (StatusId);

create table UserLookup
(
    userId    varchar(36)                        not null
        primary key,
    userName  varchar(255) charset utf8mb3       not null,
    email     varchar(255)                       not null,
    createdAt datetime default CURRENT_TIMESTAMP null
);

create
    definer = root@`%` procedure CreateUserLockup(IN p_userId varchar(36), IN p_username varchar(255),
                                                  IN p_email varchar(255))
begin
    INSERT INTO UserLookup (userId, userName, email)
    VALUES (p_userId,p_username,p_email);
end;

create
    definer = root@`%` procedure ExistsByProjectId(IN p_projectId varchar(36))
begin
    SELECT EXISTS(SELECT 1 FROM `ProjectLookup` WHERE projectId = p_projectId );
end;

create
    definer = root@`%` procedure ExistsByUserId(IN p_userId varchar(36))
begin
    SELECT EXISTS(SELECT 1 FROM `UserLookup` WHERE userId = p_userId );
end;

create
    definer = root@`%` procedure deleteProjectLookup(IN p_projectId varchar(36))
BEGIN
    DELETE FROM ProjectLookup WHERE projectId = p_projectId;
END;

create
    definer = root@`%` procedure deleteUserLookup(IN p_userId varchar(36))
BEGIN
    DELETE FROM UserLookup WHERE userId = p_userId;
END;

create
    definer = root@`%` procedure saveProjectLookup(IN p_projectId varchar(36), IN p_name varchar(255),
                                                   IN p_description text, IN p_createdBy varchar(36),
                                                   IN p_startDate date, IN p_endDate date, IN p_statusId int)
BEGIN
    INSERT INTO ProjectLookup (projectId, name, description, createdBy, createdAt,StartDate,EndDate,StatusId)
    VALUES (p_projectId, p_name, p_description, p_createdBy, CURRENT_TIMESTAMP,p_startDate, p_endDate, p_statusId);
END;

create
    definer = root@`%` procedure sp_create_release(IN p_project_id varchar(36), IN p_version varchar(50),
                                                   IN p_description text, IN p_release_date date, IN p_status_id int,
                                                   IN p_created_by varchar(36))
BEGIN
    INSERT INTO ReleaseManagement (
        ProjectId, Version, Description, ReleaseDate,
        StatusId, CreatedBy, UpdatedBy
    )
    VALUES (
        p_project_id, p_version, p_description, p_release_date,
        p_status_id, p_created_by, p_created_by
    );
END;

create
    definer = root@`%` procedure sp_delete_release(IN p_release_id int)
BEGIN
    DELETE FROM ReleaseManagement
    WHERE ReleaseId = p_release_id;
END;

create
    definer = root@`%` procedure sp_get_release_by_id(IN p_release_id int)
BEGIN
    SELECT
        r.ReleaseId,
        r.ProjectId,
        r.Version,
        r.Description,
        r.ReleaseDate,
        s.StatusName,
        r.CreatedAt,
        r.UpdatedAt,
        r.CreatedBy,
        r.UpdatedBy
    FROM ReleaseManagement r
    LEFT JOIN ReleaseStatus s ON r.StatusId = s.StatusId
    WHERE r.ReleaseId = p_release_id;
END;

create
    definer = root@`%` procedure sp_get_releases_by_project_id(IN p_project_id varchar(36))
BEGIN
    SELECT
        r.ReleaseId,
        r.ProjectId,
        r.Version,
        r.Description,
        r.ReleaseDate,
        s.StatusName,
        r.CreatedAt,
        r.UpdatedAt,
        r.CreatedBy,
        r.UpdatedBy
    FROM ReleaseManagement r
    LEFT JOIN ReleaseStatus s ON r.StatusId = s.StatusId
    WHERE r.ProjectId = p_project_id;
END;

create
    definer = root@`%` procedure sp_update_release(IN p_release_id int, IN p_version varchar(50), IN p_description text,
                                                   IN p_release_date date, IN p_status_id int,
                                                   IN p_updated_by varchar(36))
BEGIN
    UPDATE ReleaseManagement
    SET
        Version = p_version,
        Description = p_description,
        ReleaseDate = p_release_date,
        StatusId = p_status_id,
        UpdatedBy = p_updated_by
    WHERE ReleaseId = p_release_id;
END;

create
    definer = root@`%` procedure sp_update_user(IN p_userId varchar(36), IN p_userName varchar(255),
                                                IN p_email varchar(255))
BEGIN
    UPDATE UserLookup
    SET UserName = p_userName,
        Email = p_email
    WHERE UserId = p_userId;
END;

create
    definer = root@`%` procedure updateProjectLookup(IN p_projectId varchar(36), IN p_name varchar(255),
                                                     IN p_description text, IN p_startDate date, IN p_endDate date,
                                                     IN p_statusId int, IN p_updatedBy varchar(36))
BEGIN
    UPDATE ProjectLookup
    SET
        name = p_name,
        description = p_description,
        StartDate = p_startDate,
        EndDate = p_endDate,
        StatusId = p_statusId,
        UpdatedBy = p_updatedBy,
        UpdatedAt = CURRENT_TIMESTAMP
    WHERE projectId = p_projectId;
END;

INSERT INTO release_service_db.ReleaseStatus (StatusId, StatusName) VALUES (2, 'In Progress');
INSERT INTO release_service_db.ReleaseStatus (StatusId, StatusName) VALUES (1, 'Planned');
INSERT INTO release_service_db.ReleaseStatus (StatusId, StatusName) VALUES (3, 'Released');

