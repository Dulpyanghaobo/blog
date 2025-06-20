package com.hab.blog.api.v1.moods;

import com.hab.blog.api.v1.moods.Entity.Mood;
import com.hab.blog.api.v1.moods.Entity.MoodRequest;
import com.hab.blog.api.v1.moods.Service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mood")
public class MoodController {
    @Autowired
    private MoodService moodService;
    @PostMapping
    public Mood createMood(@RequestBody MoodRequest moodRequest) {
        return moodService.createMood(moodRequest.getUserId(), moodRequest.getDate(), moodRequest.getMoodLevel());
    }

    @PutMapping
    public Mood updateMood(@RequestBody MoodRequest moodRequest) {
        return moodService.updateMood(moodRequest.getUserId(), moodRequest.getDate(), moodRequest.getMoodLevel());
    }

    @GetMapping("/{userId}")
    public List<Mood> getAllMoods(@PathVariable Long userId) {
        return moodService.getAllMoods(userId);
    }

    @GetMapping("/{userId}/{date}")
    public Mood getMoodByDate(@PathVariable Long userId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return moodService.getMoodByDate(userId, date).orElse(null);
    }
}