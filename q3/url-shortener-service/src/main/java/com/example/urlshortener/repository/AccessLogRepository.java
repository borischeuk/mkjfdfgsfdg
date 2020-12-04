package com.example.urlshortener.repository;

import com.example.urlshortener.repository.entity.AccessLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLogEntity, String> {

    @Query("select count(*) from AccessLogEntity a where a.ip = :sourceIp and a.createdAt >= :timeFrom and a.createdAt <= :timeTo")
    Integer getAccessLogByTimeRange(String sourceIp, LocalDateTime timeFrom, LocalDateTime timeTo);

    List<AccessLogEntity> findAllByIp(String ip);

//    @Query("select count(*) from AccessLogEntity a where a.ip = :sourceIp")
//    Integer getAccessLogByTimeRange(String sourceIp);

}
