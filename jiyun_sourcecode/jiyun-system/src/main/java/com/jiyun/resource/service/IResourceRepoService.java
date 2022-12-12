package com.ruoyi.resource.service;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.ResourceRepo;

import java.util.List;

/**
 * 部门管理 服务层
 * 
 * @author ruoyi
 */
public interface IResourceRepoService
{
    /**
     * 查询部门管理数据
     * 
     * @param repo 部门信息
     * @return 部门信息集合
     */
    public List<ResourceRepo> selectRepoList(ResourceRepo repo);

    /**
     * 构建前端所需要树结构
     * 
     * @param repos 部门列表
     * @return 树结构列表
     */
    public List<ResourceRepo> buildRepoTree(List<ResourceRepo> repos);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param repos 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildRepoTreeSelect(List<ResourceRepo> repos);

    /**
     * 根据角色ID查询部门树信息
     * 
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    public List<Long> selectRepoListByRoleId(Long roleId);

    /**
     * 根据部门ID查询信息
     * 
     * @param repoId 部门ID
     * @return 部门信息
     */
    public ResourceRepo selectRepoById(Long repoId);

    /**
     * 根据ID查询所有子部门（正常状态）
     * 
     * @param repoId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenRepoById(Long repoId);

    /**
     * 是否存在部门子节点
     * 
     * @param repoId 部门ID
     * @return 结果
     */
    public boolean hasChildByRepoId(Long repoId);

    /**
     * 查询部门是否存在用户
     * 
     * @param repoId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkRepoExistUser(Long repoId);

    /**
     * 校验部门名称是否唯一
     * 
     * @param repo 部门信息
     * @return 结果
     */
    public String checkRepoNameUnique(ResourceRepo repo);

    /**
     * 校验部门是否有数据权限
     * 
     * @param repoId 部门id
     */
    public void checkRepoDataScope(Long repoId);

    /**
     * 新增保存部门信息
     * 
     * @param repo 部门信息
     * @return 结果
     */
    public int insertRepo(ResourceRepo repo);

    /**
     * 修改保存部门信息
     * 
     * @param repo 部门信息
     * @return 结果
     */
    public int updateRepo(ResourceRepo repo);

    /**
     * 删除部门管理信息
     * 
     * @param repoId 部门ID
     * @return 结果
     */
    public int deleteRepoById(Long repoId);
}
