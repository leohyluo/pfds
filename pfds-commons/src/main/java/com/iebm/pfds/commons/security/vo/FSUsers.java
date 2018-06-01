package com.iebm.pfds.commons.security.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 用户实体类
 * TODO 如果修改了该类 记得修改GATEWAY项目里面的配置
 * 
 * @author LiNing
 *
 */
public class FSUsers extends AbstractUserDetails  {

	private static final long serialVersionUID = 1L;

	/**
	 * Redis key前缀
	 */
	public static final String AUTHSERVER = "authServer:";

	/**
	 * 微信登录标识
	 */
	public static final String WECHATLOGIN = "wechatLogin";

	/**
	 * 内部登录
	 */
	public static final String INTERNALLOGIN = "InternalLogin";

	/**
	 * 注册时，存储短信验证码前缀
	 */
	public static final String REGISTER_CODE = AUTHSERVER + "register:";

	/**
	 * 登录失败次数记录
	 */
	public static final String LOCK_TIMES = AUTHSERVER + "lockTimes:";

	public static final String LOCK_USER_FLAG = AUTHSERVER + "lockFlag:";

	/**
	 * 忘记密码，短信验证码前缀
	 */
	public static final String FORGET_CODE = AUTHSERVER + "forget:";

	/**
	 * 绑定手机，短信验证码前缀
	 */
	public static final String BINDING_MOBILE_CODE = AUTHSERVER + "binding:mobile:";

	/**
	 * 绑定邮箱
	 */
	public static final String BINDING_EMAIL_CODE = AUTHSERVER + "binding:email:";
	/**
	 * 绑定邮箱的用户ID
	 */
	public static final String BINDING_EMAIL_USER_ID_CODE = AUTHSERVER + "binding:email:userId:";

	/**
	 * 用户统一掩码
	 */
	public static final String USER_MASK = "nJjIEf@LNl^WzcbhHG!a)X60";

	// ~ Instance fields
	// ================================================================================================
	private Long userId;// 用户ID
	private String username;// 用户姓名
	private String password;// 用户密码
	private String userAccount;// 用户帐号
	private String userMask;// 用户掩码
	private Integer userStatus;// 用户状态
	private String mobilePhone;// 手机号码
	private String wechatCode;// 用户微信号
	private String email;// 用户邮箱
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间

	/**
	 * spring security 用户属性
	 */
	private Set<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	/**
	 * 公司ID标识
	 */
	private Long companyId;
	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 角色名称,以逗号做角色名称拼接
	 */
	private transient String roleNames;

	/**
	 * 上次选取公司ID
	 */
	private Long selectCompanyId;

	/**
	 * 注册使用参数，确认密码
	 */
	private String confirmPwd;

	/**
	 * 验证码
	 */
	private String captcha;

	/**
	 * 代帐公司编号
	 */
	private String bkCompanyCode;

	/**
	 * 用户模式 1： 代理记账 2：中小企业
	 */
	private Integer userType;

	/**
	 * 用户组ID
	 */
	private Long groupId;

	/**
	 * 组织ID
	 */
	private String orgId;
	
	/**
	 * 代帐公司用户状态
	 */
	private Integer bkUserStatus;

	public FSUsers() {
	}

	// ~ Constructors
	// ===================================================================================================
	public FSUsers(Long userId, String username, String password, String userAccount, String userMask,
                   Integer userStatus, String mobilePhone, String wechatCode, String email, Date createTime, Date modifyTime,
                   Collection<? extends GrantedAuthority> authorities, Long companyId, String companyName,
                   Long selectCompanyId, String bkCompanyCode, Integer userType, Long groupId , String orgId , Integer bkUserStatus) {
		this(userId, username, password, userAccount, userMask, userStatus, mobilePhone, wechatCode, email, createTime,
				modifyTime, authorities, true, true, true, true, companyId, companyName, selectCompanyId, bkCompanyCode,
				userType, groupId , orgId , bkUserStatus);
	}

	// 生产使用
	public FSUsers(Long userId, String username, String password, String userAccount, String userMask,
                   Integer userStatus, String mobilePhone, String wechatCode, String email, Date createTime, Date modifyTime,
                   Collection<? extends GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked,
                   boolean credentialsNonExpired, boolean enabled, Long companyId, String companyName, Long selectCompanyId) {

		if (((username == null) || "".equals(username)) || (password == null)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userAccount = userAccount;
		this.userMask = userMask;
		this.userStatus = userStatus;
		this.mobilePhone = mobilePhone;
		this.wechatCode = wechatCode;
		this.email = email;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.companyId = companyId;
		this.companyName = companyName;
		this.selectCompanyId = selectCompanyId;
	}

	public FSUsers(Long userId, String username, String password, String userAccount, String userMask,
                   Integer userStatus, String mobilePhone, String wechatCode, String email, Date createTime, Date modifyTime,
                   Collection<? extends GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked,
                   boolean credentialsNonExpired, boolean enabled, Long companyId, String companyName, Long selectCompanyId,
                   String bkCompanyCode, Integer userType, Long groupId , String orgId, Integer bkUserStatus) {

		if (((username == null) || "".equals(username)) || (password == null)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userAccount = userAccount;
		this.userMask = userMask;
		this.userStatus = userStatus;
		this.mobilePhone = mobilePhone;
		this.wechatCode = wechatCode;
		this.email = email;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.companyId = companyId;
		this.companyName = companyName;
		this.selectCompanyId = selectCompanyId;
		this.bkCompanyCode = bkCompanyCode;
		this.userType = userType;
		this.groupId = groupId;
		this.orgId = orgId;
		this.bkUserStatus = bkUserStatus;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// ~ Methods
	// ========================================================================================================
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void eraseCredentials() {
		// password = getPassword();
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			// Neither should ever be null as each entry is checked before
			// adding it to the set.
			// If the authority is null, it is a custom authority and should
			// precede others.
			if (g2.getAuthority() == null) {
				return -1;
			}

			if (g1.getAuthority() == null) {
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserMask() {
		return userMask;
	}

	public void setUserMask(String userMask) {
		this.userMask = userMask;
	}

	/**
	 * Returns {@code true} if the supplied object is a {@code User} instance
	 * with the same {@code username} value.
	 * <p>
	 * In other words, the objects are equal if they have the same username,
	 * representing the same principal.
	 */
	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof FSUsers) {
			return username.equals(((FSUsers) rhs).username);
		}
		return false;
	}

	/**
	 * Returns the hashcode of the {@code username}.
	 */
	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("UserId: ").append(this.userId).append("; ");
		sb.append("UserAccount: ").append(this.userAccount).append("; ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("WechatCode: ").append(this.wechatCode).append("; ");
		sb.append("MobilePhone: ").append(this.mobilePhone).append("; ");
		sb.append("Email: ").append(this.email).append("; ");
		sb.append("CompanyName: ").append(this.companyName).append("; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");

		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

		if (authorities != null && !authorities.isEmpty()) {
			sb.append("Granted Authorities: ");

			boolean first = true;
			for (GrantedAuthority auth : authorities) {
				if (!first) {
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}
		return sb.toString();
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getWechatCode() {
		return wechatCode;
	}

	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public Long getSelectCompanyId() {
		return selectCompanyId;
	}

	public void setSelectCompanyId(Long selectCompanyId) {
		this.selectCompanyId = selectCompanyId;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getBkCompanyCode() {
		return bkCompanyCode;
	}

	public void setBkCompanyCode(String bkCompanyCode) {
		this.bkCompanyCode = bkCompanyCode;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getBkUserStatus() {
		return bkUserStatus;
	}

	public void setBkUserStatus(Integer bkUserStatus) {
		this.bkUserStatus = bkUserStatus;
	}

}