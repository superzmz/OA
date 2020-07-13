package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Privilege;
import com.superzmz.www.bean.Role;
import com.superzmz.www.bean.User;
import com.superzmz.www.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl extends BaseDaoImpl<User> implements UserService {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public Class getEntityClass() {
		return User.class;
	}

	@Override
	public User findByNameAndPassword(String loginName,String password) {
		// 使用密码的MD5摘要进行对比
		String md5Digest = DigestUtils.md5Hex(password);
		return (User) getSession().createQuery(//
				"FROM User u WHERE u.loginName=? AND u.password=?")//
				.setParameter(0, loginName)//
				.setParameter(1, md5Digest)//
				.uniqueResult();

	}

	@Override
	public List<Privilege> preparePrivileges(User user, List<Privilege> topList) {
		List<Privilege> copyList = new ArrayList<Privilege>(); //注意这里的复制问题，否则影响application中的元素

		System.out.println("========= 准备用户"+user.getLoginName()+"权限 =========");
		for (Privilege top : topList) {
			if (hasPrivilegeByName(top.getName(), user)) {

				Privilege copy_top = new Privilege();
				copy_top.setId(top.getId());
				copy_top.setName(top.getName());
				copy_top.setUrl(top.getUrl());
				copy_top.setShow(true);
				Set<Privilege> copy_top_childs = new HashSet<Privilege>();

				for (Privilege child : top.getChildren()) {
					if (hasPrivilegeByName(child.getName(), user)) {
						Privilege copy_child = new Privilege();
						copy_child.setId(child.getId());
						copy_child.setName(child.getName());
						copy_child.setUrl(child.getUrl());
						copy_child.setShow(true);

						copy_top_childs.add(copy_child);
					}
				}
				copy_top.setChildren(copy_top_childs);
				copyList.add(copy_top);
			}
		}

		return copyList;
	}

	@Override
	public void changeWorkStatus(User user) {
		int i = 0;
		user.setHaswork(i);
		getCurrentSession().update(user);
	}

	@Override
	public boolean hasPrivilegeByName(String name,User user) {
		// 超级管理有所有的权限
		if ("admin".equals(user.getLoginName())) {
			return true;
		}

		// 普通用户要判断是否含有这个权限
		for (Role role : user.getRoles()) {
			for (Privilege priv : role.getPrivileges()) {
				if (priv.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
}
