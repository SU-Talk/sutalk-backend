package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "channel")
public class Channel {

    @Id
    private String channelid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemid")
    private Item item;

    private String channelType;

    private String alarmYn;

    private Long regdate;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<ChannelUser> channelUsers;
}
