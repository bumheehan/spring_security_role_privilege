package xyz.bumbing.api.service.impl;//package kr.co.everex.mora.auth.api.service.impl;
//
//import kr.co.everex.mora.auth.api.exception.ErrorCode;
//import kr.co.everex.mora.auth.api.exception.MoraException;
//import kr.co.everex.mora.auth.api.service.MedicalStaffService;
//import kr.co.everex.mora.member.domain.MedicalStaff;
//import kr.co.everex.mora.member.domain.Patient;
//import kr.co.everex.mora.member.dto.MedicalStaffDto;
//import kr.co.everex.mora.member.dto.MemberDto;
//import kr.co.everex.mora.member.repo.MedicalStaffRepository;
//import kr.co.everex.mora.member.repo.MemberRepository;
//import kr.co.everex.mora.member.repo.PatientsRepository;
//import kr.co.everex.mora.member.type.MemberStatusType;
//import kr.co.everex.mora.member.type.RoleType;
//import kr.co.everex.mora.security.SecurityUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class MemberServiceImpl implements MedicalStaffService {
//
//    private final MedicalStaffRepository medicalStaffRepository;
//    private final PatientsRepository patientsRepository;
//    private final MemberRepository memberRepository;
//
//    private final SecurityUtils securityUtils;
//
//    @Override
//    @Transactional
//    public void signUp(MedicalStaffDto member) {
//        this.validateMedicalStaff(member);
//        //엔티티 생성
//        MedicalStaff staff = MedicalStaff.createStaff(
//                member.getEmail(),
//                securityUtils.encode(member.getPassword()),
//                member.getName(),
//                member.getPhone(),
//                member.getLicenseNumber(),
//                member.getHospitalName(),
//                member.getHospitalAddress()
//        );
//        //권한 추가
//        member.getRoles().forEach(staff::appendRole);
//        this.medicalStaffRepository.save(staff);
//        //TODO: 인증 메일 필요
//    }
//
//    private void validateMedicalStaff(MedicalStaffDto member) {
//        //해당 메서드 동시 실행시 유저 중복될수 있기 때문에 Unique Key 필수
//        Optional<MedicalStaff> oldMember = this.medicalStaffRepository.findByEmail(member.getEmail());
//        if (oldMember.isPresent()) {
//            throw new MoraException(ErrorCode.EMAIL_DUPLICATION);
//        }
//    }
//
//    @Override
//    @Transactional
//    public void signUp(PatientsDto member) {
//        this.validatePatients(member);
//        //엔티티 생성
//        Patient patients = Patient.createPatients(
//                member.getEmail(),
//                securityUtils.encode(member.getPassword()),
//                member.getName(),
//                member.getPhone(),
//                member.getGender(),
//                member.getBirthday()
//        );
//        //권한 추가
//        member.getRoles().forEach(patients::appendRole);
//        //TODO: 인증 메일 필요
//        this.patientsRepository.save(patients);
//    }
//
//    private void validatePatients(PatientsDto member) {
//        //해당 메서드 동시 실행시 유저 중복될수 있기 때문에 Unique Key 필수
//        Optional<Patient> oldMember = this.patientsRepository.findByEmail(member.getEmail());
//        if (oldMember.isPresent()) {
//            throw new MoraException(ErrorCode.EMAIL_DUPLICATION);
//        }
//    }
//
//
//    @Override
//    @Transactional
//    public void activateMember(String email, RoleType role, String activateCode) {
//        //TODO: 코드 인증
//        if (isStaff(role)) {
//            medicalStaffRepository.findByEmailAndStatus(email, MemberStatusType.DEACTIVATION.getCode()).filter(s -> s.getAuthorities().stream().anyMatch(a -> a.getRole().equals(role))).orElseThrow(() ->
//                    new MoraException(ErrorCode.ENTITY_NOT_FOUND)).activate();
//        } else {
//            patientsRepository.findByEmailAndStatus(email, MemberStatusType.DEACTIVATION.getCode()).filter(s -> s.getAuthorities().stream().anyMatch(a -> a.getRole().equals(role))).orElseThrow(() ->
//                    new MoraException(ErrorCode.ENTITY_NOT_FOUND)).activate();
//        }
//
//    }
//
//    @Override
//    public List<MemberDto> getMembers() {
//        return memberRepository.findMembersWithAuthorities().stream().map(s -> MemberDto.of(s)).collect(Collectors.toList());
//    }
//
//    private boolean isStaff(RoleType role) {
//        return RoleType.DOCTOR.equals(role) || RoleType.EXERCISE_THERAPIST.equals(role) || RoleType.PHYSICAL_THERAPIST.equals(role);
//    }
//
//}
