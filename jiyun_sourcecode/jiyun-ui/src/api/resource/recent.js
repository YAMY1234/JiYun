import request from '@/utils/request'

// 查询部门列表
export function listRecent(query, username) {
  return request({
    url: '/resource/repo/recent/' + username,
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
export function listRecentExcludeChild(repoId) {
  return request({
    url: '/resource/repo/recent/exclude/' + repoId,
    method: 'get'
  })
}

// 查询部门详细
export function getRecent(repoId) {
  return request({
    url: '/resource/repo/' + repoId,
    method: 'get'
  })
}

// 查询部门下拉树结构
export function treeselect() {
  return request({
    url: '/resource/repo/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询部门树结构
export function roleRecentTreeselect(roleId) {
  return request({
    url: '/resource/repo/roleRecentTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增部门
export function addRecent(data) {
  return request({
    url: '/resource/repo',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateRecent(data) {
  return request({
    url: '/resource/repo',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delRecent(repoId) {
  return request({
    url: '/resource/repo/' + repoId,
    method: 'delete'
  })
}
