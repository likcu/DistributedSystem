package project2.server;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
  List<User> findAllByUserId(Integer id);
  List<User> findAllByUserIdAndDay(Integer id,Integer day);
}
