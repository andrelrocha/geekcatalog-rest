package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.fullList_mobile.useCase.GetListFullInfoService;

@RestController
@RequestMapping("/listfull")
public class FullListMobileController {
    @Autowired
    private GetListFullInfoService getListFullInfoService;

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
}
