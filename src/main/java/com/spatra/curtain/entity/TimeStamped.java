package com.spatra.curtain.entity;

<<<<<<< HEAD
import jakarta.persistence.*;
=======
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TemporalType;
>>>>>>> 4b18485fca46468f7eddf3fc7b8184ac069c6886
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
<<<<<<< HEAD
=======
import org.springframework.data.jpa.repository.Temporal;
>>>>>>> 4b18485fca46468f7eddf3fc7b8184ac069c6886

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeStamped {
<<<<<<< HEAD

=======
>>>>>>> 4b18485fca46468f7eddf3fc7b8184ac069c6886
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}