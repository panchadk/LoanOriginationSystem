package com.adminplatform.los_auth.authorize.controller;
import com.adminplatform.los_auth.authorize.dto.RoleDto;
import com.adminplatform.los_auth.authorize.repository.RoleRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(r -> new RoleDto(
                        r.getRoleId().toString(),
                        r.getRoleName(),
                        r.getTenant() != null ? r.getTenant().getTenantId().toString() : null // ✅ Add this
                ))
                .collect(Collectors.toList());
    }
}
