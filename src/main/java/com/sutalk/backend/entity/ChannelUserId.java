package com.sutalk.backend.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelUserId implements Serializable {

    private String channel;
    private String user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChannelUserId that)) return false;
        return Objects.equals(channel, that.channel) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel, user);
    }
}
