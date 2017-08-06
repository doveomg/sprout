package com.sprout.order.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * Created by fengshuaiju on 2017/7/29 0029.
 */
public class AbstractEntity implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Version
    public Integer version;

    @Column(updatable = false,insertable = false,nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Instant createAt;

}
