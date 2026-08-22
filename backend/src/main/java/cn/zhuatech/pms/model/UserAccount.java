/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.model;
import jakarta.persistence.*;
@Entity @Table(name="sys_user") public class UserAccount extends BaseEntity {
    public enum Role {ADMIN,PROJECT_MANAGER,MEMBER}
    @Column(nullable=false,unique=true,length=40) private String username;
    @Column(nullable=false,length=100) private String password;
    @Column(nullable=false,length=40) private String displayName;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Role role;
    @Column(nullable=false) private boolean enabled=true;
    protected UserAccount(){} public UserAccount(String username,String password,String displayName,Role role){this.username=username;this.password=password;this.displayName=displayName;this.role=role;}
    public String getUsername(){return username;} public String getPassword(){return password;} public String getDisplayName(){return displayName;} public Role getRole(){return role;} public boolean isEnabled(){return enabled;}
}
