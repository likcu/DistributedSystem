package neu.edu.cs6650.proj_2_server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StepByDayRepository extends JpaRepository<StepByDay, String> {
  StepByDay findByName(String name);
}
