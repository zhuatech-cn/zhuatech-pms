/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pms.repository;
import cn.zhuatech.pms.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findAllByOrderByWorkDateDesc();
}
