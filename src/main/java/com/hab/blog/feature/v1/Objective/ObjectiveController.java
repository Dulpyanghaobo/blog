package com.hab.blog.feature.v1.Objective;

import com.hab.blog.feature.v1.entities.Objective.Objective;
import com.hab.blog.feature.v1.entities.repository.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/objectives")
public class ObjectiveController {

    @Autowired
    private ObjectiveRepository objectiveRepository;

    // 创建Objective
    @PostMapping
    public ResponseEntity<Objective> createObjective(@RequestBody Objective objective) {
        Objective savedObjective = objectiveRepository.save(objective);
        return new ResponseEntity<>(savedObjective, HttpStatus.CREATED);
    }

    // 获取所有Objective
    @GetMapping
    public List<Objective> getAllObjectives() {
        return objectiveRepository.findAll();
    }

    // 根据id获取单个Objective
    @GetMapping("/{id}")
    public ResponseEntity<Objective> getObjectiveById(@PathVariable Long id) {
        Optional<Objective> objective = objectiveRepository.findById(id);
        if (objective.isPresent()) {
            return new ResponseEntity<>(objective.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 更新Objective
    @PutMapping("/{id}")
    public ResponseEntity<Objective> updateObjective(@PathVariable Long id, @RequestBody Objective updatedObjective) {
        Optional<Objective> optionalObjective = objectiveRepository.findById(id);
        if (optionalObjective.isPresent()) {
            Objective objective = optionalObjective.get();
            objective.setTitle(updatedObjective.getTitle());
            objective.setDescription(updatedObjective.getDescription());
            objective.setStartDate(updatedObjective.getStartDate());
            objective.setEndDate(updatedObjective.getEndDate());
            objective.setProgress(updatedObjective.getProgress());
            objective.setStatus(updatedObjective.getStatus());
            objective.setUser(updatedObjective.getUser());
            objective.setComplexity(updatedObjective.getComplexity());
            objective.setPriority(updatedObjective.getPriority());
            objective.setImportance(updatedObjective.getImportance());

            Objective savedObjective = objectiveRepository.save(objective);
            return new ResponseEntity<>(savedObjective, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 删除Objective
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjective(@PathVariable Long id) {
        Optional<Objective> optionalObjective = objectiveRepository.findById(id);
        if (optionalObjective.isPresent()) {
            objectiveRepository.delete(optionalObjective.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
