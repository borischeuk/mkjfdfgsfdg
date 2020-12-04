package com.example.urlshortener.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@With
@Table(name = "access_log")
public class AccessLogEntity {

    @Id
    private String id;

    private String ip;

    @Basic(optional = false)
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}
