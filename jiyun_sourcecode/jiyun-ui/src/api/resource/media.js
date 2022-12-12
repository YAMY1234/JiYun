import request from '@/utils/request'

// 查询部门列表
export function listMedia(query, username) {
  return request({
    url: '/resource/repo/media/' + username,
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
export function listMediaExcludeChild(repoId) {
  return request({
    url: '/resource/repo/media/exclude/' + repoId,
    method: 'get'
  })
}

// 查询部门详细
export function getMedia(repoId) {
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
export function roleMediaTreeselect(roleId) {
  return request({
    url: '/resource/repo/roleMediaTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增部门
export function addMedia(data) {
  return request({
    url: '/resource/repo',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateMedia(data) {
  return request({
    url: '/resource/repo',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delMedia(repoId) {
  return request({
    url: '/resource/repo/' + repoId,
    method: 'delete'
  })
}
