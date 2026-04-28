package com.blog.blog_login_module.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.blog_login_module.entity.UserRead;
import com.blog.blog_login_module.entity.UserWrite;
import com.blog.blog_login_module.repository.UserReadRepository;
import com.blog.blog_login_module.repository.UserWriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginFailureService {

	private UserWriteRepository userWriteRepository;
	private UserReadRepository userReadRepository;
	@Transactional
    public void processFailedLogin(UserWrite user) {
        user.setFailedAttempts(user.getFailedAttempts() + 1);

        if (user.getFailedAttempts() >= 3) {
            user.setLocked(true);
            user.setLockTime(LocalDateTime.now().plusHours(24));
        }

        userWriteRepository.save(user);
        syncReadModel(user);
    }

    @Transactional
    public void resetAttempts(UserWrite user) {
        user.setFailedAttempts(0);
        user.setLocked(false);
        user.setLockTime(null);

        userWriteRepository.save(user);
        syncReadModel(user);
    }

    private void syncReadModel(UserWrite user) {
        UserRead read = userReadRepository.findById(user.getId())
                .orElse(new UserRead());

        read.setId(user.getId());
        read.setUsername(user.getUsername());
        read.setEmail(user.getEmail());
        read.setEnabled(user.isEnabled());
        read.setLocked(user.isLocked());

        userReadRepository.save(read);
    }
	 
}
