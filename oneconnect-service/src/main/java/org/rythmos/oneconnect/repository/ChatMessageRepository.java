package org.rythmos.oneconnect.repository;

import java.util.List;

import org.rythmos.oneconnect.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	@Query("from ChatMessage where room=:room and id=:id")
	ChatMessage findChatMessageByRoomAndId(@Param("room") String room, @Param("id") Long id);

	@Query("from ChatMessage where id=:id")
	ChatMessage findChatMessageById(@Param("id") Long id);

	@Query("from ChatMessage where room=:room order by createdDate desc")
	List<ChatMessage> findChatMessageByRoom(@Param("room") String room);

	@Query("from ChatMessage where id=(select max(id) from ChatMessage where room=:room)")
	ChatMessage findLastChatMessageByRoom(@Param("room") String room);
}
