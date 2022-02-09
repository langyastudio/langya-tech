package com.langyastudio.cloud.dubbo.bean;

import java.io.Serializable;

/**
 * User Entity.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
public class User implements Serializable
{
    private Long id;

    private String name;

    private Integer age;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + '}';
    }
}