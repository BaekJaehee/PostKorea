package com.ssafy.dmobile.collection.repository.title;

import com.ssafy.dmobile.collection.entity.title.TitleMember;
import com.ssafy.dmobile.collection.entity.title.TitleMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleMemberRepository extends JpaRepository<TitleMember, TitleMemberKey> {

    List<TitleMember> findMemberTitlesByKeyMemberId(Long memberId);
}
