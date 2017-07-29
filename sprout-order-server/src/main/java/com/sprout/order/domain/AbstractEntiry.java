package com.sprout.order.domain;

import javax.persistence.*;
import java.time.Instant;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
public class AbstractEntiry {

    @Id
    private Long id;

    @Version
    private Integer version;

    @Column(updatable = false,insertable = false,nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createAt;

}
