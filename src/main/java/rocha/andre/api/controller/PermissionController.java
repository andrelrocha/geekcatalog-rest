package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.permission.useCase.GetAllPermissions;
import rocha.andre.api.service.CountryService;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private GetAllPermissions getAllPermissions;

    @GetMapping("/getall")
    public ResponseEntity getAllPermissionsPageable (@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size,
                                                   @RequestParam(defaultValue = "permission") String sortField,
                                                   @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var permissionsPageable = getAllPermissions.getAllPermissions(pageable);
        return ResponseEntity.ok(permissionsPageable);
    }
}
