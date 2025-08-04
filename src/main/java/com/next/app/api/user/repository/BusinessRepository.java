package com.next.app.api.user.repository;

import com.next.app.api.user.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    // 필요한 경우 추가 메서드 정의 가능
}
