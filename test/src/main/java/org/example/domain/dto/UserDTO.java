package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Xjh
 * @since 2023-05-19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * person主键
     */
    private Integer pid;

    private Integer pbm;

    private Integer eid;

    private Integer ebm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPbm() {
        return pbm;
    }

    public void setPbm(Integer pbm) {
        this.pbm = pbm;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getEbm() {
        return ebm;
    }

    public void setEbm(Integer ebm) {
        this.ebm = ebm;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", email=" + email +
                ", pid=" + pid +
                ", pbm=" + pbm +
                ", eid=" + eid +
                ", ebm=" + ebm +
                "}";
    }
}
