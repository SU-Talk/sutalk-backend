package org.example.sutalkbe.service;

import lombok.RequiredArgsConstructor;
import org.example.sutalkbe.entity.User;
import org.example.sutalkbe.entity.ChatRoom;
import org.example.sutalkbe.entity.Post;
import org.example.sutalkbe.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final PostService postService;
    private final UserService userService;

    public ChatRoom createChatRoom(Long postId, Long buyerId, Long sellerId) {
        Post post = postService.getPostById(postId);
        User buyer = (User) userService.getUserById(buyerId);
        User seller = (User) userService.getUserById(sellerId);

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setPost(post);
        chatRoom.setBuyer(buyer);
        chatRoom.setSeller(seller);
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
    }
}
