/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.repository;
import cn.zhuatech.pms.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    long countByPlannedDateLessThanEqualAndStatusNot(LocalDate date, String status);
    List<Milestone> findAllByOrderByPlannedDateAsc();
}
