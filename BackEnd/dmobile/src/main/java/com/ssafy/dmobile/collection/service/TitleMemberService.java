package com.ssafy.dmobile.collection.service;

import com.ssafy.dmobile.collection.entity.title.TitleMember;
import com.ssafy.dmobile.collection.entity.title.Title;
import com.ssafy.dmobile.collection.repository.TitleMemberRepository;
import com.ssafy.dmobile.collection.repository.TitleRepository;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TitleMemberService {

    private final TitleRepository titleRepository;
    private final TitleMemberRepository titleMemberRepository;


    public List<Title> findTitleList(Long memberId) {

        List<TitleMember> titleMembers = titleMemberRepository.findMemberTitlesByKeyMemberId(memberId);

        List<Title> titles = new ArrayList<>();
        for (TitleMember info : titleMembers) {

            Title title = titleRepository.getReferenceById(info.getKey().getTitleId());
            titles.add(title);
        }

        return titles;
    }

    public Title getTitleInfo(Long titleId) {

        Optional<Title> optionalTitle = titleRepository.findById(titleId);
        if (optionalTitle.isEmpty()) {
            throw new CustomException(ExceptionType.BOARD_NOT_FOUND);
        }

        return optionalTitle.get();
    }
}
