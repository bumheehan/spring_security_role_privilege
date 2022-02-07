package xyz.bumbing.api.service.impl;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import xyz.bumbing.domain.entity.User;
import xyz.bumbing.domain.repo.UserRepository;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("UserDetailsServiceImpl");

        User user = userRepository.findOneWithPrivilegeByEmail(username).orElseThrow(() -> new UsernameNotFoundException("유저 없음"));
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getUserRoles().stream()
                        .flatMap(s -> s.getRole().getRolePrivileges().stream())
                        .map(s -> (GrantedAuthority) () -> s.getPrivilege().getName()).distinct().collect(Collectors.toList()))
                .build();
        userDetails.setUid(user.getId());
        return userDetails;
    }

    public static class CustomUserDetails implements UserDetails, CredentialsContainer, Serializable {

        private Long uid;

        private String password;

        private final String username;

        private final Set<GrantedAuthority> authorities;

        private final boolean accountNonExpired;

        private final boolean accountNonLocked;

        private final boolean credentialsNonExpired;

        private final boolean enabled;

        @Builder
        public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
            this(username, password, true, true, true, true, authorities);
        }

        public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                                 boolean credentialsNonExpired, boolean accountNonLocked,
                                 Collection<? extends GrantedAuthority> authorities) {
            Assert.isTrue(username != null && !"".equals(username) && password != null,
                    "Cannot pass null or empty values to constructor");
            this.username = username;
            this.password = password;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        }

        @Override
        public Collection<GrantedAuthority> getAuthorities() {
            return this.authorities;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isEnabled() {
            return this.enabled;
        }

        @Override
        public boolean isAccountNonExpired() {
            return this.accountNonExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
            return this.accountNonLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return this.credentialsNonExpired;
        }

        @Override
        public void eraseCredentials() {
            this.password = null;
        }

        private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
            Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
            SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
            for (GrantedAuthority grantedAuthority : authorities) {
                Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
                sortedAuthorities.add(grantedAuthority);
            }
            return sortedAuthorities;
        }

        public void setUid(Long uid) {
            this.uid = uid;
        }

        public Long getUid() {
            return uid;
        }
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }

    }
}
