package com.ssafy.dmobile.collection.repository.achieve;

import com.ssafy.dmobile.collection.entity.achievement.AchieveMember;
import com.ssafy.dmobile.collection.entity.achievement.AchieveMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchieveMemberRepository extends JpaRepository<AchieveMember, AchieveMemberKey> {

}