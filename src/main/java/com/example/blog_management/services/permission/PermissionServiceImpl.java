package com.example.blog_management.services.permission;

import com.example.blog_management.dtos.requests.permissions.ReqPermission;
import com.example.blog_management.dtos.requests.permissions.ReqPermissionId;
import com.example.blog_management.dtos.responses.RestResponse;
import com.example.blog_management.dtos.responses.permissions.ResPermission;
import com.example.blog_management.entities.Permission;
import com.example.blog_management.repositories.PermissionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    PermissionRepo permissionRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestResponse findAll() {
        RestResponse restResponse = new RestResponse();

        try {
            List<Permission> permissions = permissionRepo.findAll();
            List<ResPermission> result = new ArrayList<>();

            for(Permission p : permissions){
                ResPermission resPermission = modelMapper.map(p, ResPermission.class);

                result.add(resPermission);
            }

            restResponse.setMessage("Find all permission success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get all permissions: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse findById(ReqPermissionId permissionId) {
        RestResponse restResponse = new RestResponse();

        try {
            Permission permission = permissionRepo.findById(permissionId.getId()).orElse(null);
            ResPermission result = modelMapper.map(permission, ResPermission.class);

            restResponse.setMessage("Find permission success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to get permissions: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse createOrUpdate(ReqPermission req) {
        RestResponse restResponse = new RestResponse();
        boolean update = false;

        try {
            if(req.getId() != null && permissionRepo.existsById(req.getId())){
                Permission permission = permissionRepo.findById(req.getId()).orElse(null);
                req.setCreateAt(permission.getCreateAt());
                req.setCreateBy(permission.getCreateBy());
                req.setUpdateAt(LocalDateTime.now());
                req.setUpdateBy("admin@gmail.com");
                update = true;
            } else{
                req.setCreateAt(LocalDateTime.now());
                req.setCreateBy("admin@gmail.com");
            }

            Permission permission = modelMapper.map(req, Permission.class);
            permissionRepo.save(permission);
            ResPermission result = modelMapper.map(permission, ResPermission.class);

            restResponse.setMessage(update ? "Update permission success" : "Create permission success");
            restResponse.setData(result);
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage(update ? ("Failed to update permissions: " + e.getMessage()) : ("Failed to create permissions: " + e.getMessage()));
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }

    @Override
    public RestResponse deleteById(ReqPermissionId permissionId) {
        RestResponse restResponse = new RestResponse();

        try {
            permissionRepo.deleteById(permissionId.getId());

            restResponse.setMessage("Delete permission success");
            restResponse.setSuccess(true);
            restResponse.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            restResponse.setMessage("Failed to delete permissions: " + e.getMessage());
            restResponse.setSuccess(false);
            restResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return restResponse;
    }
}
