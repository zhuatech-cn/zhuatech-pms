/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pms.repository;
import cn.zhuatech.pms.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectCode(String projectCode);
    long countByHealth(String health);
    List<Project> findAllByOrderByPlannedEndAsc();
}
