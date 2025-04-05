package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ChannelUserId.class)
@Table(name = "channel_user")
public class ChannelUser {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channelid")
    private Channel channel;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    private String lastreadMessage;
}
