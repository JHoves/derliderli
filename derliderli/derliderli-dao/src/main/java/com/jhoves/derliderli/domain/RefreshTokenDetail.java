package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-01-30 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDetail {
    private Long id;

    private String refreshToken;

    private Long userId;

    private Date createTime;
}
