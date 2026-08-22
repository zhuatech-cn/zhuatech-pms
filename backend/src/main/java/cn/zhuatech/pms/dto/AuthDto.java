/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.dto;
import cn.zhuatech.pms.model.UserAccount;import jakarta.validation.constraints.NotBlank;
public final class AuthDto {private AuthDto(){}
    public record LoginRequest(@NotBlank(message="请输入账号") String username,@NotBlank(message="请输入密码") String password){}
    public record UserView(String username,String displayName,String role){public static UserView from(UserAccount u){return new UserView(u.getUsername(),u.getDisplayName(),u.getRole().name());}}
    public record LoginResponse(String token,UserView user){}
}
