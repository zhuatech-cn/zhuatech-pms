/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pms.repository;
import cn.zhuatech.pms.model.ProjectRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProjectRiskRepository extends JpaRepository<ProjectRisk, Long> {
    long countByImpactAndStatusNot(String impact, String status);
    List<ProjectRisk> findAllByOrderByIdDesc();
}
