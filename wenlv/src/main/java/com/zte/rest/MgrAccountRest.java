package com.zte.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.bean.*;
import com.zte.bean.vo.AdminVo;
import com.zte.bean.vo.RoleVo;
import com.zte.common.utils.*;
import com.zte.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @comment 管理员帐号接口类
 * @Author zhongyong 2020/7/16 9:05
 */
@Api(tags = "管理员用户权限操作类")
@RestController
@RequestMapping("/admin")
public class MgrAccountRest {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);


    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Autowired
    HttpServletRequest req;

    //查询用户列表
    @ApiOperation("查询管理员列表接口")
    @PostMapping("/findAdminList/{pageNum}/{pageSize}")
    public JsonResult findAll(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,@RequestBody(required = false) Admin admin){
        JsonResult result;
        PageHelper.startPage(pageNum,pageSize);
        List<Admin> admins = adminService.queryAll(admin);
        PageInfo resultPageInfo = new PageInfo<>(admins);

        List adminVos = new ArrayList();

        for (Admin admin1 : admins) {
            JsonResult singleAdmin = findSingleAdmin(admin1);
            adminVos.add(singleAdmin.getMessage());
        }
        resultPageInfo.setList(adminVos);

        result = JsonResult.getSuccess(resultPageInfo);
        return result;
    }

    //刷新用户信息
    @ApiOperation("刷新用户信息接口")
    @PostMapping("/flashAdmin")
    public JsonResult flashAdmin(){
            AdminVo adminVo = SystemUtils.getAdminInfo(req);
            //获取 用户角色 映射
            List<AdminRole> adminRoles = adminRoleService.queryAdminRoleByAdminId(adminVo.getAdminId());
            HashSet<Role> roleSet = new HashSet<>();
            HashSet<Menu> menuSet = null;

            for (AdminRole adminRole : adminRoles) {
                Integer roleId = adminRole.getRoleId();
                Role role = roleService.queryRoleById(roleId);
                if (role.getStatus()==1){
                    roleSet.add(role);
                    menuSet = (HashSet<Menu>) roleMenuService.queryMenusByRole(roleId);
                }
            }
            adminVo.setRoleSet(roleSet);
            adminVo.setMenuSet(menuSet);
            //不返回密码信息
            adminVo.setSalt(null);
            adminVo.setPassword(null);
           
           JSONObject param=JSONObject.parseObject(JSONObject.toJSONString(adminVo));
           //数据库中有对应账户，且缓存中无用户信息，则生成登录用户信息并保存
           SystemUtils.genAdminToken(param);

        return JsonResult.getSuccess(param);
    }

    //查询单个用户信息
    @ApiOperation("查询单个用户接口")
    @PostMapping("/findSingleAdmin")
    public JsonResult findSingleAdmin(@RequestBody Admin admin){
    
        //获取 用户角色 映射
        List<AdminRole> adminRoles = adminRoleService.queryAdminRoleByAdminId(admin.getAdminId());
        AdminVo adminVo =JSON.parseObject(JSON.toJSONString(admin),AdminVo.class);
        HashSet<Role> roleSet = new HashSet<>();
        HashSet<Menu> menuSet = null;

        for (AdminRole adminRole : adminRoles) {
            Integer roleId = adminRole.getRoleId();
            Role role = roleService.queryRoleById(roleId);
            if (role.getStatus()==1){
                roleSet.add(role);
                menuSet = (HashSet<Menu>) roleMenuService.queryMenusByRole(roleId);
            }
        }
        adminVo.setRoleSet(roleSet);
        adminVo.setMenuSet(menuSet);
        //不返回密码信息
        adminVo.setSalt(null);
        adminVo.setPassword(null);

        return JsonResult.getSuccess(adminVo);
    }





    @ApiOperation("管理员登录接口")
    @PostMapping("/login")
    public JsonResult login(@RequestBody Admin admin) {
        JsonResult result;
        if (admin!=null) {
            Admin queryAdminByAccount = adminService.queryAdminByAccount(admin.getAccount());
            String account = admin.getAccount();

            if (queryAdminByAccount==null){
                result = JsonResult.getFail("账号不存在");
                return result;
            }else {
                String salt = queryAdminByAccount.getSalt();
                String md5HashPwd = new Md5Hash(admin.getPassword(),salt).toString().substring(0,20);

                Admin loginAdmin = adminService.login(account, md5HashPwd);
                if (loginAdmin==null) {
                    result = JsonResult.getFail("账号或密码有误");
                }else{
                        AdminVo loginAdminVo =JSON.parseObject(JSON.toJSONString(loginAdmin),AdminVo.class);
                        //获取 用户角色 映射
                        List<AdminRole> adminRoles = adminRoleService.queryAdminRoleByAdminId(loginAdminVo.getAdminId());
                        HashSet<Role> roleSet = new HashSet<>();
                        HashSet<Menu> menuSet = null;

                        for (AdminRole adminRole : adminRoles) {
                            Integer roleId = adminRole.getRoleId();
                            Role role = roleService.queryRoleById(roleId);
                            if (role.getStatus()==1){
                                roleSet.add(role);
                                menuSet = (HashSet<Menu>) roleMenuService.queryMenusByRole(roleId);
                            }
                        }
                        loginAdminVo.setRoleSet(roleSet);
                        loginAdminVo.setMenuSet(menuSet);
                        //不返回密码信息
                        loginAdminVo.setSalt(null);
                        loginAdminVo.setPassword(null);

                        //JSONObject param=JSONObject.parseObject(JSONObject.toJSONString(loginAdminVo));
                        JSONObject param =new JSONObject();
                        param.put("adminVo",loginAdminVo);
                        //数据库中有对应账户，且缓存中无用户信息，则生成登录用户信息并保存
                        String adminToken = SystemUtils.genAdminToken(param);
                        param.put("adminToken",adminToken);

                        result = JsonResult.getSuccess(param);
                    }
                }
        } else {
            result = JsonResult.getFail("登录失败: account/password");
        }
        return result;
    }

    @ApiOperation("新增管理员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="adminVo",
                    value ="实例：{\"account\":\"test1\",\"password\":\"654321\",\"mobile\":\"654321\",\"roleSet\":[{\"id\":1}]}",
                    paramType="body",
                    dataType = "AdminVo",
                    required=true )
    })
    @PostMapping("/register")
    public JsonResult register(@RequestBody AdminVo adminVo){
        JsonResult result;
        if (adminVo!=null) {
            if(StringUtils.isNotBlank(adminVo.getAccount())&&StringUtils.isNotBlank(adminVo.getPassword())){
                String account = adminVo.getAccount();
                Admin adminByAccount = adminService.queryAdminByAccount(account);
                if (adminByAccount!=null){
                    result = JsonResult.getFail("账号已存在");
                }else {
                    //密码加密
                    String salt = SimpleUtil.getShortUuid();
                    String md5HashPwd = new Md5Hash(adminVo.getPassword(), salt).toString().substring(0,20);
                    adminVo.setSalt(salt);
                    adminVo.setPassword(md5HashPwd);
                    //获取当前时间
                    String currentTime = DateUtil.getDBDatetime();
                    adminVo.setUpdateTime(currentTime);
                    adminVo.setCreateTime(currentTime);
                    adminService.register(adminVo);
                    Integer adminId = adminService.queryAdminByAccount(account).getAdminId();
                    //新增管理员完成后新增角色映射关系
                    adminVo.setAdminId(adminId);
                    updateAdminRoles(adminVo);
                    result = JsonResult.getSuccess("success");
                }
            }else {
                result = JsonResult.getFail("账号或密码为空");
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation(value = "修改管理员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="adminVo",
                    value ="实例：{\"adminId\":\"3\",\"account\":\"test1\",\"password\":\"654321\",\"mobile\":\"654321\",\"roleSet\":[{\"id\":1}]}",
                    paramType="body",
                    dataType = "AdminVo",
                    required=true )
    })
    @PostMapping("/updateAdmin")
    public JsonResult updatePwd(@RequestBody AdminVo adminVo){
        JsonResult result;
        if (adminVo!=null) {
            String password = adminVo.getPassword();
            //如果密码不为空，则修改密码，若密码为空，则不修改密码
            if (StringUtils.isNotBlank(password)){
                //生成新密码
                String salt = SimpleUtil.getShortUuid();
                String md5HashPwd = new Md5Hash(adminVo.getPassword(), salt).toString().substring(0,20);
                adminVo.setSalt(salt);
                adminVo.setPassword(md5HashPwd);
            }
            //获取当前时间
            String currentTime = DateUtil.getDBDatetime();
            adminVo.setUpdateTime(currentTime);
            adminService.updateAdmin(adminVo);
            //更新用户角色
            updateAdminRoles(adminVo);
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    //注销用户账号，将status设为2
    @ApiOperation("删除管理员接口(逻辑删除，将statu=2)")
    @PostMapping("/delAdmin")
    public JsonResult cancel(@RequestBody Admin admin){
        JsonResult result;
        if (admin!=null){
            logoutOthers(admin);
            admin.setStatus(2);
            if(adminService.queryAdminByAccount(admin.getAccount())!=null){
                //获取当前时间
                String currentTime = DateUtil.getDBDatetime();
                admin.setUpdateTime(currentTime);
                adminService.delAdmin(admin);
                result = JsonResult.getSuccess("success");
            }else {
                result = JsonResult.getFail("账户不存在");
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }



    //当前用户自行登出，获取当前用户用户名和token后退出
    @ApiOperation("管理员退出登录接口")
    @PostMapping("/logout")
    public JsonResult logout(){

    	AdminVo admin = SystemUtils.getAdminInfo(req);
       
        SystemUtils.delAdminInfo(admin.getAdminId());

        logger.info("用户登录信息是否仍在缓存"+SystemUtils.checAdminToken(req)+"");
      
        return JsonResult.getSuccess("success");
    }

    //管理员强制踢出其它用户，只需要用户名即可将用户信息从缓存中删除
    @ApiOperation("管理员踢出其它用户接口")
    @PostMapping("/logoutOthers")
    public JsonResult logoutOthers(@RequestBody Admin admin){
        JsonResult result;
        Integer adminId = admin.getAdminId();
        if (admin!=null){
            SystemUtils.delAdminInfo(adminId);
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    /**
     * @author yinsiwei
     * @date 2020-07-27 15:01
              * 查询角色列表
    */
    @ApiOperation("查询角色列表接口")
    @PostMapping("/role/findRoleList/{pageNum}/{pageSize}")
    public JsonResult findRoleList(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){
        JsonResult result;
        PageHelper.startPage(pageNum,pageSize);

        List<Role> roles = roleService.queryAllRows();
        PageInfo<Role> rolePageInfo = new PageInfo<>(roles);
        List roleMessages = new ArrayList<>();
        for (Role role : roles) {
            JsonResult singleRole = findSingleRole(role);
            roleMessages.add(singleRole.getMessage());
        }
        rolePageInfo.setList(roleMessages);

        //PageInfo<RoleVo> roleVoPageInfo = new PageInfo<>(roleMessages);

        result = JsonResult.getSuccess(rolePageInfo);
        return result;
    }

    @ApiOperation("查询单个角色详情接口")
    @PostMapping("/role/findSingleRole")
    public JsonResult findSingleRole(@RequestBody Role varRole){
        JsonResult result;
        Integer roleId = varRole.getId();
        if (roleId!=null){
            HashMap resultObject = new HashMap<>();
            Role role = roleService.queryRoleById(roleId);
            Set<Menu> menus = roleMenuService.queryMenusByRole(roleId);
            resultObject.put("role",role);
            resultObject.put("menus",menus);
            result = JsonResult.getSuccess(resultObject);
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("新增角色接口(角色信息不要传id，数据库自动生成)")
    @ApiImplicitParam()
    @PostMapping("/role/addRole")
    public JsonResult addRole(@RequestBody RoleVo roleVo){
        JsonResult result;
        if (roleVo!=null){
            Role role = roleVo;
            String roleName = role.getRoleName();
            Set<Integer> menuIdSet = roleVo.getMenuIdSet();
            if (roleService.queryRoleByName(roleName)==null){
                String currentTime = DateUtil.getDBDatetime();
                role.setCreatTime(currentTime);
                roleService.addRole(role);
                Role newRole = roleService.queryRoleByName(roleName);
                roleMenuService.addRoleMenu(newRole.getId(),menuIdSet);
                result = JsonResult.getSuccess(newRole);
            }else {
                result = JsonResult.getFail("角色名已存在");
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    //删除操作，物理删除
    @ApiOperation("删除角色接口(物理删除，直接从表中删除)")
    @PostMapping("/role/delRole")
    public JsonResult delRole(@RequestBody Role varRole){
        JsonResult result;
        Integer roleId = varRole.getId();
        if (roleId!=null){
            List<AdminRole> adminRoles = adminRoleService.queryAdminRoleByRoleId(roleId);
            //判断角色Id是否还有用户绑定关系
            if (adminRoles.size()!=0){
                result = JsonResult.getFail("该角色仍有关联角色");
            }else {
                roleService.delRole(roleId);
                roleMenuService.updateRoleMenu(roleId,null);
                result = JsonResult.getSuccess("success");
            }

        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("修改角色接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleVo",value ="封装roleId和menuId列表",paramType="body",dataType = "RoleVo",required=true )
    })
    @PostMapping("/role/UpdateRole")
    public JsonResult updateRole(@RequestBody RoleVo roleVo){
        JsonResult result;
        if(roleVo!=null){
            Integer roleId = roleVo.getId();
            Set<Integer> menuIdSet = roleVo.getMenuIdSet();
            Role newRole = roleVo;
            roleService.updateRole(newRole);
            roleMenuService.updateRoleMenu(roleId,menuIdSet);
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    @ApiOperation("查询菜单列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum",value ="页数",paramType="query",dataType = "int",required=false ),
            @ApiImplicitParam(name="pageSize",value ="每页条数",paramType="query",dataType = "int",required=false )
    })
    @PostMapping("/role/findMenuList/{pageNum}/{pageSize}")
    public JsonResult findMenuList(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){
        JsonResult result;
        PageHelper.startPage(pageNum,pageSize);

        PageInfo<Menu> menuPageInfo = new PageInfo<>(menuService.queryAllMenus());
        result = JsonResult.getSuccess(menuPageInfo);
        return result;
    }

    //修改用户角色
    @ApiOperation("修改管理员角色接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="adminVo",value ="封装adminId和roleId列表",paramType="body",dataType = "AdminVo",required=true )
    })
    @PostMapping("/role/updateAdminRoles")
    public JsonResult updateAdminRoles(
            @RequestBody AdminVo adminVo){
        JsonResult result;
        if(adminVo!=null){
            Integer adminId = adminVo.getAdminId();
            Set<Role> roleSet = adminVo.getRoleSet();
            if (adminId!=null&&roleSet!=null){
                AdminRole tmpAdminRole = new AdminRole();
                //删除用户所有角色
                if (roleSet.size()!=0){
                    List<AdminRole> adminRoles = adminRoleService.queryAdminRoleByAdminId(adminId);
                    for (AdminRole adminRole : adminRoles) {
                        adminRoleService.delAdminRole(adminRole);
                    }
                }
                //新增用户现有角色
                for (Role role : roleSet) {
                    tmpAdminRole.setAdminId(adminId);
                    tmpAdminRole.setRoleId(role.getId());
                    adminRoleService.addAdminRole(tmpAdminRole);
                }
                result = JsonResult.getSuccess("success");
            }else {
                result = JsonResult.getFail("未获取到用户或角色");
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

    //根据角色绑定或解绑用户
    @ApiOperation("修改角色绑定管理员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleVo",value ="封装roleId和adminId列表",paramType="body",dataType ="RoleVo" ,required=true )
    })
    @PostMapping("/role/updateRoleAdmins")
    public JsonResult updateRoleAdmins(@RequestBody RoleVo roleVo){
        JsonResult result;
        if(roleVo!=null){
            Integer roleId = roleVo.getId();
            Set<Admin> adminSet = roleVo.getAdminSet();
            if (roleId!=null&&adminSet!=null){
                AdminRole tmpAdminRole = new AdminRole();
                //删除用户所有角色
                if (adminSet.size()!=0){
                    List<AdminRole> adminRoles = adminRoleService.queryAdminRoleByRoleId(roleId);
                    for (AdminRole adminRole : adminRoles) {
                        adminRoleService.delAdminRole(adminRole);
                    }
                }
                //新增用户现有角色
                for (Admin admin : adminSet) {
                    tmpAdminRole.setRoleId(roleId);
                    tmpAdminRole.setAdminId(admin.getAdminId());
                    adminRoleService.addAdminRole(tmpAdminRole);
                }
                result = JsonResult.getSuccess("success");
            }else {
                result = JsonResult.getFail("未获取到用户或角色");
            }
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

}
