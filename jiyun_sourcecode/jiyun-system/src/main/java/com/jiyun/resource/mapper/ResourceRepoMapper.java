package com.ruoyi.resource.mapper;

import com.ruoyi.common.core.domain.entity.ResourceRepo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源管理 数据层
 * 
 * @author ruoyi
 */
public interface ResourceRepoMapper
{
    /**
     * 查询资源管理数据
     * 
     * @param repo 资源信息
     * @return 资源信息集合
     */
    public List<ResourceRepo> selectRepoList(ResourceRepo repo);

    /**
     * 根据角色ID查询资源树信息
     * 
     * @param roleId 角色ID
     * @param repoCheckStrictly 资源树选择项是否关联显示
     * @return 选中资源列表
     */
    public List<Long> selectRepoListByRoleId(@Param("roleId") Long roleId, @Param("repoCheckStrictly") boolean repoCheckStrictly);

    /**
     * 根据资源ID查询信息
     * 
     * @param repoId 资源ID
     * @return 资源信息
     */
    public ResourceRepo selectRepoById(Long repoId);

    /**
     * 根据ID查询所有子资源
     * 
     * @param repoId 资源ID
     * @return 资源列表
     */
    public List<ResourceRepo> selectChildrenRepoById(Long repoId);

    /**
     * 根据ID查询所有子资源（正常状态）
     * 
     * @param repoId 资源ID
     * @return 子资源数
     */
    public int selectNormalChildrenRepoById(Long repoId);

    /**
     * 是否存在子节点
     * 
     * @param repoId 资源ID
     * @return 结果
     */
    public int hasChildByRepoId(Long repoId);

    /**
     * 查询资源是否存在用户
     * 
     * @param repoId 资源ID
     * @return 结果
     */
    public int checkRepoExistUser(Long repoId);

    /**
     * 校验资源名称是否唯一
     * 
     * @param repoName 资源名称
     * @param parentId 父资源ID
     * @return 结果
     */
    public ResourceRepo checkRepoNameUnique(@Param("repoName") String repoName, @Param("parentId") Long parentId);

    /**
     * 新增资源信息
     * 
     * @param repo 资源信息
     * @return 结果
     */
    public int insertRepo(ResourceRepo repo);

    /**
     * 修改资源信息
     * 
     * @param repo 资源信息
     * @return 结果
     */
    public int updateRepo(ResourceRepo repo);

    /**
     * 修改所在资源正常状态
     * 
     * @param repoIds 资源ID组
     */
    public void updateRepoStatusNormal(Long[] repoIds);

    /**
     * 修改子元素关系
     * 
     * @param repos 子元素
     * @return 结果
     */
    public int updateRepoChildren(@Param("repos") List<ResourceRepo> repos);

    /**
     * 删除资源管理信息
     * 
     * @param repoId 资源ID
     * @return 结果
     */
    public int deleteRepoById(Long repoId);
}
