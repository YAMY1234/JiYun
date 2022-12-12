package com.ruoyi.web.controller.resource;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.ResourceRepo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.resource.service.IResourceRepoService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.velocity.runtime.directive.contrib.For;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 资源信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/resource/repo")
public class ResourceRepoController extends BaseController
{
    @Autowired
    private IResourceRepoService repoService;

    /**
     * 获取资源列表
     */
//    @PreAuthorize("@ss.hasPermi('resource:repo:list')")
    @GetMapping("/list/{username}")
    public AjaxResult list(ResourceRepo repo, @PathVariable(value = "username", required = false) String username)
    {
        repo.setLeader(username);
        repo.setType("all");
        List<ResourceRepo> repos = repoService.selectRepoList(repo);
        return AjaxResult.success(repos);
    }

//    @PreAuthorize("@ss.hasPermi('resource:repo:docx')")
    @GetMapping("/docx/{username}")
    public AjaxResult docx(ResourceRepo repo, @PathVariable(value = "username", required = false) String username)
    {
        repo.setLeader(username);
        repo.setType("docx");
        List<ResourceRepo> repos = repoService.selectRepoList(repo);
        return AjaxResult.success(repos);
    }


//    @PreAuthorize("@ss.hasPermi('resource:repo:media')")
    @GetMapping("/media/{username}")
    public AjaxResult media(ResourceRepo repo, @PathVariable(value = "username", required = false) String username)
    {
        repo.setLeader(username);
        repo.setType("media");
        List<ResourceRepo> repos = repoService.selectRepoList(repo);
        return AjaxResult.success(repos);
    }


//    @PreAuthorize("@ss.hasPermi('resource:repo:recent')")
    @GetMapping("/recent/{username}")
    public AjaxResult recent(ResourceRepo repo, @PathVariable(value = "username", required = false) String username)
    {
        repo.setLeader(username);
        List<ResourceRepo> repos = repoService.selectRepoList(repo);
        List<ResourceRepo> new_repos = new ArrayList<>();
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        String strTime = "2019-06-10 17:32:05";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (ResourceRepo resourceRepo : repos){
            if(resourceRepo.getUpdateTime()!=null && resourceRepo.getUpdateTime().after(date)){
                new_repos.add(resourceRepo);
            }
        }
        return AjaxResult.success(new_repos);
    }

//    @PreAuthorize("@ss.hasPermi('resource:repo:teacher')")
    @GetMapping("/teacher/{username}")
    public AjaxResult teacher(ResourceRepo repo, @PathVariable(value = "username", required = false) String username)
    {
        repo.setLeader("all");
        List<ResourceRepo> repos = repoService.selectRepoList(repo);
        List<ResourceRepo> new_repos = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("计算机1班同学A");
        list.add("计算机1班同学B");
//        list.add("李洋旻");
//        list.add("晓茂茂");
//        list.add("黛图图");
//        list.add("信息安全班同学A");
        for (ResourceRepo resourceRepo : repos){
            if(list.contains(resourceRepo.getLeader())){
                new_repos.add(resourceRepo);
            }
        }
        return AjaxResult.success(new_repos);
    }

    /**
     * 查询资源列表（排除节点）
     */
//    @PreAuthorize("@ss.hasPermi('resource:repo:list')")
    @GetMapping("/list/exclude/{repoId}")
    public AjaxResult excludeChild(@PathVariable(value = "repoId", required = false) Long repoId)
    {
        List<ResourceRepo> repos = repoService.selectRepoList(new ResourceRepo());
        Iterator<ResourceRepo> it = repos.iterator();
        while (it.hasNext())
        {
            ResourceRepo d = (ResourceRepo) it.next();
            if (d.getRepoId().intValue() == repoId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), repoId + ""))
            {
                it.remove();
            }
        }
        return AjaxResult.success(repos);
    }

    /**
     * 根据资源编号获取详细信息
     */
//    @PreAuthorize("@ss.hasPermi('resource:repo:query')")
    @GetMapping(value = "/{repoId}")
    public AjaxResult getInfo(@PathVariable Long repoId)
    {
        repoService.checkRepoDataScope(repoId);
        return AjaxResult.success(repoService.selectRepoById(repoId));
    }

    /**
     * 获取资源下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(ResourceRepo repo)
    {
        List<ResourceRepo> repos = repoService.selectRepoList(repo);
        return AjaxResult.success(repoService.buildRepoTreeSelect(repos));
    }


    /**
     * 加载对应角色资源列表树
     */
    @GetMapping(value = "/roleRepoTreeselect/{roleId}")
    public AjaxResult roleRepoTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<ResourceRepo> repos = repoService.selectRepoList(new ResourceRepo());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", repoService.selectRepoListByRoleId(roleId));
        ajax.put("repos", repoService.buildRepoTreeSelect(repos));
        return ajax;
    }

    /**
     * 新增资源
     */
//    @PreAuthorize("@ss.hasPermi('resource:repo:add')")
    @Log(title = "资源管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ResourceRepo repo)
    {
        if (UserConstants.NOT_UNIQUE.equals(repoService.checkRepoNameUnique(repo)))
        {
            return AjaxResult.error("新增资源'" + repo.getRepoName() + "'失败，资源名称已存在");
        }
        repo.setCreateBy(getUsername());
        return toAjax(repoService.insertRepo(repo));
    }

    /**
     * 修改资源
     */
//    @PreAuthorize("@ss.hasPermi('resource:repo:edit')")
    @Log(title = "资源管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ResourceRepo repo)
    {
        Long repoId = repo.getRepoId();
        repoService.checkRepoDataScope(repoId);
        if (UserConstants.NOT_UNIQUE.equals(repoService.checkRepoNameUnique(repo)))
        {
            return AjaxResult.error("修改资源'" + repo.getRepoName() + "'失败，资源名称已存在");
        }
        else if (repo.getParentId().equals(repoId))
        {
            return AjaxResult.error("修改资源'" + repo.getRepoName() + "'失败，上级资源不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, repo.getStatus()) && repoService.selectNormalChildrenRepoById(repoId) > 0)
        {
            return AjaxResult.error("该资源包含未停用的子资源！");
        }
        repo.setUpdateBy(getUsername());
        return toAjax(repoService.updateRepo(repo));
    }

    /**
     * 删除资源
     */
//    @PreAuthorize("@ss.hasPermi('resource:repo:remove')")
    @Log(title = "资源管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{repoId}")
    public AjaxResult remove(@PathVariable Long repoId)
    {
        if (repoService.hasChildByRepoId(repoId))
        {
            return AjaxResult.error("存在下级资源,不允许删除");
        }
        if (repoService.checkRepoExistUser(repoId))
        {
            return AjaxResult.error("资源存在用户,不允许删除");
        }
        repoService.checkRepoDataScope(repoId);
        return toAjax(repoService.deleteRepoById(repoId));
    }
}
