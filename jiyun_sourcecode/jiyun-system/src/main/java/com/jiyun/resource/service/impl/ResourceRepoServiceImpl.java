package com.ruoyi.resource.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.ResourceRepo;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.resource.mapper.ResourceRepoMapper;
import com.ruoyi.resource.service.IResourceRepoService;
import com.ruoyi.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源管理 服务实现
 * 
 * @author ruoyi
 */
@Service
public class ResourceRepoServiceImpl implements IResourceRepoService
{
    @Autowired
    private ResourceRepoMapper repoMapper;

//    @Autowired
//    private SysRoleMapper roleMapper;

    /**
     * 查询资源管理数据
     * 
     * @param repo 资源信息
     * @return 资源信息集合
     */
    @Override
    public List<ResourceRepo> selectRepoList(ResourceRepo repo)
    {
        return repoMapper.selectRepoList(repo);
    }

    /**
     * 构建前端所需要树结构
     * 
     * @param repos 资源列表
     * @return 树结构列表
     */
    @Override
    public List<ResourceRepo> buildRepoTree(List<ResourceRepo> repos)
    {
        List<ResourceRepo> returnList = new ArrayList<ResourceRepo>();
        List<Long> tempList = new ArrayList<Long>();
        for (ResourceRepo repo : repos)
        {
            tempList.add(repo.getRepoId());
        }
        for (ResourceRepo repo : repos)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(repo.getParentId()))
            {
                recursionFn(repos, repo);
                returnList.add(repo);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = repos;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param repos 资源列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildRepoTreeSelect(List<ResourceRepo> repos)
    {
//        List<ResourceRepo> repoTrees = buildRepoTree(repos);
//        return repoTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
        return null;
    }

    /**
     * 根据角色ID查询资源树信息
     * 
     * @param roleId 角色ID
     * @return 选中资源列表
     */
    @Override
    public List<Long> selectRepoListByRoleId(Long roleId)
    {
//        SysRole role = roleMapper.selectRoleById(roleId);
//        return repoMapper.selectRepoListByRoleId(roleId, role.isRepoCheckStrictly());
        return null;
    }

    /**
     * 根据资源ID查询信息
     * 
     * @param repoId 资源ID
     * @return 资源信息
     */
    @Override
    public ResourceRepo selectRepoById(Long repoId)
    {
        return repoMapper.selectRepoById(repoId);
    }

    /**
     * 根据ID查询所有子资源（正常状态）
     * 
     * @param repoId 资源ID
     * @return 子资源数
     */
    @Override
    public int selectNormalChildrenRepoById(Long repoId)
    {
        return repoMapper.selectNormalChildrenRepoById(repoId);
    }

    /**
     * 是否存在子节点
     * 
     * @param repoId 资源ID
     * @return 结果
     */
    @Override
    public boolean hasChildByRepoId(Long repoId)
    {
        int result = repoMapper.hasChildByRepoId(repoId);
        return result > 0;
    }

    /**
     * 查询资源是否存在用户
     * 
     * @param repoId 资源ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkRepoExistUser(Long repoId)
    {
        int result = repoMapper.checkRepoExistUser(repoId);
        return result > 0;
    }

    /**
     * 校验资源名称是否唯一
     * 
     * @param repo 资源信息
     * @return 结果
     */
    @Override
    public String checkRepoNameUnique(ResourceRepo repo)
    {
        Long repoId = StringUtils.isNull(repo.getRepoId()) ? -1L : repo.getRepoId();
        ResourceRepo info = repoMapper.checkRepoNameUnique(repo.getRepoName(), repo.getParentId());
        if (StringUtils.isNotNull(info) && info.getRepoId().longValue() != repoId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验资源是否有数据权限
     * 
     * @param repoId 资源id
     */
    @Override
    public void checkRepoDataScope(Long repoId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            ResourceRepo repo = new ResourceRepo();
            repo.setRepoId(repoId);
            List<ResourceRepo> repos = SpringUtils.getAopProxy(this).selectRepoList(repo);
            if (StringUtils.isEmpty(repos))
            {
                throw new ServiceException("没有权限访问资源数据！");
            }
        }
    }

    /**
     * 新增保存资源信息
     * 
     * @param repo 资源信息
     * @return 结果
     */
    @Override
    public int insertRepo(ResourceRepo repo)
    {
        ResourceRepo info = repoMapper.selectRepoById(repo.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new ServiceException("资源停用，不允许新增");
        }
        repo.setAncestors(info.getAncestors() + "," + repo.getParentId());
        return repoMapper.insertRepo(repo);
    }

    /**
     * 修改保存资源信息
     * 
     * @param repo 资源信息
     * @return 结果
     */
    @Override
    public int updateRepo(ResourceRepo repo)
    {
        ResourceRepo newParentRepo = repoMapper.selectRepoById(repo.getParentId());
        ResourceRepo oldRepo = repoMapper.selectRepoById(repo.getRepoId());
        if (StringUtils.isNotNull(newParentRepo) && StringUtils.isNotNull(oldRepo))
        {
            String newAncestors = newParentRepo.getAncestors() + "," + newParentRepo.getRepoId();
            String oldAncestors = oldRepo.getAncestors();
            repo.setAncestors(newAncestors);
            updateRepoChildren(repo.getRepoId(), newAncestors, oldAncestors);
        }
        int result = repoMapper.updateRepo(repo);
        if (UserConstants.DEPT_NORMAL.equals(repo.getStatus()) && StringUtils.isNotEmpty(repo.getAncestors())
                && !StringUtils.equals("0", repo.getAncestors()))
        {
            // 如果该资源是启用状态，则启用该资源的所有上级资源
            updateParentRepoStatusNormal(repo);
        }
        return result;
    }

    /**
     * 修改该资源的父级资源状态
     * 
     * @param repo 当前资源
     */
    private void updateParentRepoStatusNormal(ResourceRepo repo)
    {
        String ancestors = repo.getAncestors();
        Long[] repoIds = Convert.toLongArray(ancestors);
        repoMapper.updateRepoStatusNormal(repoIds);
    }

    /**
     * 修改子元素关系
     * 
     * @param repoId 被修改的资源ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateRepoChildren(Long repoId, String newAncestors, String oldAncestors)
    {
        List<ResourceRepo> children = repoMapper.selectChildrenRepoById(repoId);
        for (ResourceRepo child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            repoMapper.updateRepoChildren(children);
        }
    }

    /**
     * 删除资源管理信息
     * 
     * @param repoId 资源ID
     * @return 结果
     */
    @Override
    public int deleteRepoById(Long repoId)
    {
        return repoMapper.deleteRepoById(repoId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ResourceRepo> list, ResourceRepo t)
    {
        // 得到子节点列表
        List<ResourceRepo> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ResourceRepo tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ResourceRepo> getChildList(List<ResourceRepo> list, ResourceRepo t)
    {
        List<ResourceRepo> tlist = new ArrayList<ResourceRepo>();
        Iterator<ResourceRepo> it = list.iterator();
        while (it.hasNext())
        {
            ResourceRepo n = (ResourceRepo) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getRepoId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ResourceRepo> list, ResourceRepo t)
    {
        return getChildList(list, t).size() > 0;
    }
}
