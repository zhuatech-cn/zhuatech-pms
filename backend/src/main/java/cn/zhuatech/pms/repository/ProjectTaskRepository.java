/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.repository;
import cn.zhuatech.pms.model.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
    long countByStatusNot(String status);
    long countByDueDateBeforeAndStatusNot(LocalDate date, String status);
    List<ProjectTask> findAllByOrderByDueDateAsc();
}
