package com.ssafy.dmobile.member.repository;

import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.shop.dto.ShopMemberDto;
import com.ssafy.dmobile.shop.entity.Shop;
import com.ssafy.dmobile.shop.entity.ShopMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

//    @Modifying
//    @Query("update Member m set m.profileImg = :path where m.id = :id")
//    void updateProfileImage(Long id, String path);
}
