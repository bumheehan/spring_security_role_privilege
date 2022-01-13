package xyz.bumbing.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

//@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

    }


}