package com.example.springredditclone.service;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.maper.SubredditMapper;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final AuthService authService;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Optional<Subreddit> checkSubreddit = subredditRepository.findByName(subredditDto.getName());
        if (checkSubreddit.isPresent())
            throw new SpringRedditException("Try another name. Already used by some one");
        User currentUser = authService.getCurrentUser();
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto, currentUser));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public Subreddit getSubredditByName(String subredditName) {
        return subredditRepository.findByName(subredditName)
                .orElseThrow(()-> new SpringRedditException("No found"));
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Optional<Subreddit> subreddit = subredditRepository.findById(id);
        subreddit.orElseThrow(() -> new SpringRedditException("No subreddit found with Id: " + id));
        return subredditMapper.mapSubredditToDto(subreddit.get());
    }

}
