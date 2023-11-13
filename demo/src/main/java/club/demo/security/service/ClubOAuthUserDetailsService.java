package club.demo.security.service;

import club.demo.entity.ClubMember;
import club.demo.entity.ClubMemberRole;
import club.demo.repository.ClubMemberRepository;
import club.demo.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubOAuthUserDetailsService extends DefaultOAuth2UserService {

    private final ClubMemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("----------------------------------------");
        log.info("userRequest: {}" + userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName {}",clientName);
        log.info("client Id {}", userRequest.getClientRegistration().getClientId());
        log.info("registrationId {}",userRequest.getClientRegistration().getRegistrationId());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        oAuth2User.getAttributes().forEach((key, value) -> {
            log.info("key: {}, value: {}", key, value);
        });

        log.info("----------------------------------------");

        String email = null;

        if(clientName.equals("Google" )){
            email = oAuth2User.getAttribute("email");
        }
        log.info("email: {}",email);
        ClubMember member = saveSocialMember(email);
//        return oAuth2User;
        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(member.getEmail()
                , member.getPassword(), true, member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList()), oAuth2User.getAttributes());
        clubAuthMember.setName(member.getName());
        clubAuthMember.setPassword(member.getPassword());
        return clubAuthMember;
    }

    private ClubMember saveSocialMember(String email) {
        Optional<ClubMember> byEmail = repository.findByEmail(email, true);
        if(byEmail.isPresent()){
            return byEmail.get();
        }
        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        clubMember.addMemberRole(ClubMemberRole.USER);
        return repository.save(clubMember);

    }
}
