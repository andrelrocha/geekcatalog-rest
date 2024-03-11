package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.finished.DTO.FinishedDTO;
import rocha.andre.api.domain.finished.DTO.FinishedReturnDTO;
import rocha.andre.api.domain.finished.DTO.FinishedUpdateDTO;
import rocha.andre.api.service.FinishedService;

@RestController
@RequestMapping("/opinions")
public class FinishedController {
    @Autowired
    private FinishedService finishedService;

    @PostMapping("/create")
    public ResponseEntity<FinishedReturnDTO> createFinished(@RequestBody FinishedDTO data) {
        var finished = finishedService.createFinished(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(finished);
    }

    @GetMapping
    public ResponseEntity<Page<FinishedReturnDTO>> getFinished(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "15") int size,
                                                               @RequestParam(defaultValue = "name") String sortField,
                                                               @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var finishedPageable = finishedService.getFinished(pageable);
        return ResponseEntity.ok(finishedPageable);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<FinishedReturnDTO> getFinishedById(@PathVariable long id) {
        var finishedById = finishedService.getFinishedById(id);
        return ResponseEntity.ok(finishedById);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FinishedReturnDTO> updateFinished(@RequestBody FinishedUpdateDTO data, @PathVariable long id) {
        var updatedFinished = finishedService.updateFinished(data, id);
        return ResponseEntity.ok(updatedFinished);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFinished(@PathVariable long id) {
        finishedService.deleteFinished(id);
        return ResponseEntity.noContent().build();
    }
}