package com.ruoyi.common.core.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 目录表 resource_repo
 *
 * @author ruoyi
 */
public class ResourceRepo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 目录ID */
    private Long repoId;

    /** 父目录ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 目录名称 */
    private String repoName;

    /** 显示顺序 */
    private Integer orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String type;

    /** 邮箱 */
    private String email;

    /** 目录状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父目录名称 */
    private String parentName;

    /** 子目录 */
    private List<ResourceRepo> children = new ArrayList<ResourceRepo>();

    public Long getRepoId()
    {
        return repoId;
    }

    public void setRepoId(Long repoId)
    {
        this.repoId = repoId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    @NotBlank(message = "目录名称不能为空")
    @Size(min = 0, max = 30, message = "目录名称长度不能超过30个字符")
    public String getRepoName()
    {
        return repoName;
    }

    public void setRepoName(String repoName)
    {
        this.repoName = repoName;
    }

    @NotNull(message = "显示顺序不能为空")
    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

//    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public List<ResourceRepo> getChildren()
    {
        return children;
    }

    public void setChildren(List<ResourceRepo> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("repoId", getRepoId())
                .append("parentId", getParentId())
                .append("ancestors", getAncestors())
                .append("repoName", getRepoName())
                .append("orderNum", getOrderNum())
                .append("leader", getLeader())
                .append("type", getType())
                .append("email", getEmail())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
