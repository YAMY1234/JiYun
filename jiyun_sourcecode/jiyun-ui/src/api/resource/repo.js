import request from '@/utils/request'

// 查询资源列表
export function listRepo(query, username) {
  return request({
    url: '/resource/repo/list/' + username,
    method: 'get',
    params: query
  })
}

// 查询资源列表（排除节点）
export function listRepoExcludeChild(repoId) {
  return request({
    url: '/resource/repo/list/exclude/' + repoId,
    method: 'get'
  })
}

// 查询资源详细
export function getRepo(repoId) {
  return request({
    url: '/resource/repo/' + repoId,
    method: 'get'
  })
}

// 查询资源下拉树结构
export function treeselect() {
  return request({
    url: '/resource/repo/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询资源树结构
export function roleRepoTreeselect(roleId) {
  return request({
    url: '/resource/repo/roleRepoTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增资源
export function addRepo(data) {
  return request({
    url: '/resource/repo',
    method: 'post',
    data: data
  })
}

// 修改资源
export function updateRepo(data) {
  return request({
    url: '/resource/repo',
    method: 'put',
    data: data
  })
}

// 删除资源
export function delRepo(repoId) {
  return request({
    url: '/resource/repo/' + repoId,
    method: 'delete'
  })
}
