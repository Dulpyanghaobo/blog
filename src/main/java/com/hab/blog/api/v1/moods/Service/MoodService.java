package com.hab.blog.api.v1.moods.Service;

import com.hab.blog.api.v1.auth.UserRepository;
import com.hab.blog.api.v1.moods.Entity.Mood;
import com.hab.blog.api.v1.moods.Repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MoodService {

    @Autowired
    private MoodRepository moodRepository;

    @Autowired
    private UserRepository userRepository;


    public Mood createMood(Long userId, LocalDate date, Integer moodLevel) {
        Mood mood = new Mood();
        mood.setUser(userRepository.findById(userId).orElseThrow());
        mood.setDate(date);
        mood.setMoodLevel(moodLevel);
        return moodRepository.save(mood);
    }

    public Mood updateMood(Long userId, LocalDate date, Integer moodLevel) {
        Optional<Mood> moodOpt = moodRepository.findByUserIdAndDate(userId, date);
        if(moodOpt.isPresent()) {
            Mood mood = moodOpt.get();
            mood.setMoodLevel(moodLevel);
            return moodRepository.save(mood);
        } else {
            throw new IllegalArgumentException("Mood record not found for user " + userId + " on " + date);
        }
    }

    public List<Mood> getAllMoods(Long userId) {
        return moodRepository.findByUserId(userId);
    }

    public Optional<Mood> getMoodByDate(Long userId, LocalDate date) {
        return moodRepository.findByUserIdAndDate(userId, date);
    }
}