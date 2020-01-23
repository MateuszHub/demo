package uj.mateusz.demo.repository;

import org.springframework.data.repository.CrudRepository;
import uj.mateusz.demo.entitiy.ChatMessage;


public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer> {

}
