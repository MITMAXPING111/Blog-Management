package com.example.blog_management.services.user;

import com.example.blog_management.dtos.requests.permissions.ReqPermissionId;
import com.example.blog_management.dtos.requests.roles.ReqRoleId;
import com.example.blog_management.dtos.requests.users.ReqUser;
import com.example.blog_management.dtos.requests.users.ReqUserId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.permissions.ResPermission;
import com.example.blog_management.dtos.responses.roles.ResRole;
import com.example.blog_management.dtos.responses.users.ResUser;
import com.example.blog_management.entities.Permission;
import com.example.blog_management.entities.Role;
import com.example.blog_management.entities.User;
import com.example.blog_management.repositories.RoleRepo;
import com.example.blog_management.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestResponse findAll() {
        RestResponse restResponse = new RestResponse();

        try {
            List<User> users = userRepo.findAll();
            List<ResUser> result = new ArrayList<>();

            for (User r : users) {
                ResUser resUser = modelMapper.map(r, ResUser.class);

                Set<ResRole> roles = new HashSet<>();

                for (Role role : r.getRoles()) {
                    Set<Permission> permissions = role.getPermissions();

                    Set<ResPermission> resPermissions = new HashSet<>();
                    for (Permission permission : permissions) {
                        ResPermission resPermission = modelMapper.map(permission, ResPermission.class);

                        resPermissions.add(resPermission);
                    }

                    ResRole resRole = modelMapper.map(role, ResRole.class);
                    resRole.setResPermissions(resPermissions);

                    roles.add(resRole);
                }

                resUser.setResRoles(roles);

                result.add(resUser);
            }

            restResponse.setMessage("Find all user success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all user: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqUserId reqUserId) {
        RestResponse restResponse = new RestResponse();

        try {
            User user = userRepo.findById(reqUserId.getId()).orElse(null);
            ResUser result = modelMapper.map(user, ResUser.class);

            Set<ResRole> roles = new HashSet<>();

            for (Role role : user.getRoles()) {
                Set<Permission> permissions = role.getPermissions();

                Set<ResPermission> resPermissions = new HashSet<>();
                for (Permission permission : permissions) {
                    ResPermission resPermission = modelMapper.map(permission, ResPermission.class);

                    resPermissions.add(resPermission);
                }

                ResRole resRole = modelMapper.map(role, ResRole.class);
                resRole.setResPermissions(resPermissions);

                roles.add(resRole);
            }

            result.setResRoles(roles);

            restResponse.setMessage("Find user success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get user: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse createOrUpdate(ReqUser req) {
        RestResponse restResponse = new RestResponse();
        boolean update = false;

        try {
            if (req.getId() != null && userRepo.existsById(req.getId())) {
                User user = userRepo.findById(req.getId()).orElse(null);
                req.setCreateAt(user.getCreateAt());
                req.setCreateBy(user.getCreateBy());
                req.setUpdateAt(LocalDateTime.now());
                req.setUpdateBy("admin@gmail.com");
                update = true;
            } else {
                req.setCreateAt(LocalDateTime.now());
                req.setCreateBy("admin@gmail.com");
            }

            Set<ReqRoleId> roleIds = req.getReqRoleIds();
            Set<Role> roles = new HashSet<>();

            for (ReqRoleId roleId : roleIds) {
                Role role = roleRepo.findById(roleId.getId()).orElse(null);
                if (role != null)
                    roles.add(role);
            }

            User user = modelMapper.map(req, User.class);

            user.setRoles(roles);

            userRepo.save(user);
            ResUser result = modelMapper.map(user, ResUser.class);

            Set<ResRole> resRoles = new HashSet<>();

            for (Role role : user.getRoles()) {
                ResRole resRole = modelMapper.map(role, ResRole.class);

                Set<ResPermission> resPermissions = new HashSet<>();
                for (Permission permission : role.getPermissions()) {
                    ResPermission resPermission = modelMapper.map(permission, ResPermission.class);
                    resPermissions.add(resPermission);
                }
                resRole.setResPermissions(resPermissions);

                resRoles.add(resRole);
            }

            result.setResRoles(resRoles);

            restResponse.setMessage(update ? "Update user success" : "Create user success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update user: " + e.getMessage())
                    : ("Failed to create user: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqUserId reqUserId) {
        RestResponse restResponse = new RestResponse();

        try {
            userRepo.deleteById(reqUserId.getId());

            restResponse.setMessage("Delete user success");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete user: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
