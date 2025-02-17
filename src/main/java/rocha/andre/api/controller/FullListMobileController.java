package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.utils.fullList.useCase.GetListFullInfoService;
import rocha.andre.api.domain.utils.fullList.useCase.GetPermissionedListFullService;
import rocha.andre.api.domain.utils.fullList.useCase.GetPublicListFullService;

@RestController
@RequestMapping("/listfull")
@Tag(name = "App List with Full Info Routes Mapped on Controller")
public class FullListMobileController {
    @Autowired
    private GetListFullInfoService getListFullInfoService;
    @Autowired
    private GetPublicListFullService getPublicListFullService;
    @Autowired
    private GetPermissionedListFullService getPermissionedListFullService;

    @GetMapping("/all/{userId}")
    public ResponseEntity getAllListsPageable ( @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "12") int size,
                                                @RequestParam(defaultValue = "name") String sortField,
                                                @RequestParam(defaultValue = "asc") String sortOrder,
                                                @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var listsPageable = getListFullInfoService.getAllListsByUserId(userId, pageable);
        return ResponseEntity.ok(listsPageable);
    }

    @GetMapping("/public/{userId}")
    public ResponseEntity getPublicListsPageable (  @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "12") int size,
                                                    @RequestParam(defaultValue = "name") String sortField,
                                                    @RequestParam(defaultValue = "asc") String sortOrder,
                                                    @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var listsPageable = getPublicListFullService.getPublicListsByUserId(userId, pageable);
        return ResponseEntity.ok(listsPageable);
    }

    @GetMapping("/permissioned/{userId}")
    public ResponseEntity getPermissionedListsPageable (    @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "12") int size,
                                                            @RequestParam(defaultValue = "name") String sortField,
                                                            @RequestParam(defaultValue = "asc") String sortOrder,
                                                            @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var listsPageable = getPermissionedListFullService.getPermissionedListsByUserId(userId, pageable);
        return ResponseEntity.ok(listsPageable);
    }
}
