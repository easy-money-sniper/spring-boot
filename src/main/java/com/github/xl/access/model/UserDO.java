package com.github.xl.access.model;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/05 15:42
 */
public class UserDO {
    private Long id;
    private Long parentId;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", age=" + age +
                '}';
    }
}
