package com.zte.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zte.common.utils.AESUtil;
import com.zte.common.utils.AuthUtils;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.RedisKey;
import com.zte.common.utils.RedisOperUtils;
import com.zte.common.utils.SystemUtils;

/**
* <p>心跳检测<／p>
*/

@RestController
@RequestMapping("/system")
public class HeartbeatRest {
	
	@Autowired
	HttpServletRequest req;

	@RequestMapping(value="/heartbeat",produces="application/json;charset=UTF-8")
	@ResponseBody  
	public String heartbeat(){		
		return "APPOK";
	}

//	/**
//	 * 测试用 现网由运维提供
//	 * 初始化敏感词
//	 */
//	@RequestMapping("/init/sensitive")
//	public JsonResult initSensitive() {
//		if(!"1".equals(SystemUtils.getSysconfig("test.auth"))) {
//			return JsonResult.getFail("没有调用该接口的权限");
//		}
//		String key=RedisKey.SENSITIVE_WORDS;
//		String str="法0功|法一轮一功|轮子功|车仑工力|法lun|法lg|大法弟子|明慧网|明慧周报|阴茎增大|阴茎助勃|陰唇|陰道|陰戶|淫荡视频|淫荡照片|淫靡|淫魔舞|淫情女|淫肉|淫騷妹|淫兽|淫兽学|射精|兽奸|兽交|獸交|淫电影|淫河|淫教师|淫媚|淫虐|淫妻|淫兽学园|淫书|淫术炼金士|淫威|淫亵|淫样|淫液|淫照|强jian|江、胡十年|九评共产党|江流氓|江则民|江贼民|江折民|胡紧掏|胡锦滔|胡锦淘|胡錦濤|法輪功|法轮|法轮功|李洪志|明惠|转法轮|淫妇|淫叫|淫浪|淫流|淫乱|淫亂|淫糜|淫魔|淫母|淫妞|淫奴|淫女|zang人|dalai|dl喇嘛|zangdu|lasa|la萨|xizang|xi藏|x藏|tibet|x民党|taiwan|taidu|twdl|7大军区|mayingjiu|a扁|bao炸|baozha|zha药|zha弹|zhadan|zhayao|hmtd|jieyan|jie严|8的平方事件|2o年|20和谐年|198964|5月35|89年春夏之交|64惨案|64时期|64运动|4事件|xuechao|jing坐|you行|youxing|di制|dizhi|boycott|baoluan|baodong|tamoxifen|androst|mdma|heroin|cocain|k粉|ketamine|cannabis|9评|9ping|jiuping|zi杀|suicide|titor|fa轮|flg|fl功|falungong|dajiyuan|tuidang|AV111|anal111|bigcock|freesex|fuck|G点高潮|sex111|SM|cao你|18禁|99bb|a4u|a4y|adult|amateur|anal|a片|gay片|g点|g片|hardcore|h动画|h动漫|incest|porn|secom|sexinsex|sm女王|xiao77|xing伴侣|tokyohot|yin荡|@snda.com|bookspice|xs8|hongxiu|jjwxc|.net111|qidian|<111|>111|ps：111|1113.1111|3。01111|17K111|台湾应该独立|台湾有权独立|天灭中共|步qiang|爆zha|炸dan|炸yao|三硝基甲苯|六氟化铀|戒yan|知道64|六河蟹四|六百度四|六和谐四|陆四|陆肆|北京风波|学潮|学chao|学百度潮|坦克压大学生|静zuo|示wei|抵zhi|社会主义灭亡|打倒中国|打倒共产党|打倒共产主义|打倒胡锦涛|打倒江泽民|打倒江主席|打倒李鹏|打倒罗干|打倒温家宝|打倒中共|打倒朱镕|抵制共产党|抵制共产主义|抵制胡锦涛|抵制江泽民|抵制江主席|抵制李鹏|抵制罗干|抵制温家宝|抵制中共|抵制朱镕基|安眠酮|代血浆|普萘洛尔|呋塞米|西布曲明|地西泮|尼可刹米|甲睾酮|地奈德|莫达非尼|氯噻嗪|苯巴比妥|促性腺激素|泼尼松|麻黄草|雄烯二醇|地塞米松|海luo因|鸦片|阿芙蓉|咖啡因|三唑仑|美沙酮|麻古|凯他敏|冰毒|苯丙胺|大麻|氯胺酮|甲基安非他明|安非他命|售步枪|售纯度|售单管|售弹簧刀|售防身|售狗子|售虎头|售火药|售假币|售健卫|售军用|售猎枪|售氯胺|售麻醉|售枪支|售热武|售三棱|售手枪|售五四|售一元硬|售子弹|售左轮|亚砷（酸）酐|亚砷酸钾|亚砷酸钠|亚硒酸|亚硒酸二钠|亚硒酸镁|亚硒酸钠|亚硒酸氢钠|亚硝酸乙酯|亚硝酰乙氧|氧化二丁基锡|氧化汞|氧化铊|氧化亚铊|氧氯化磷|自fen|九评|九ping|逢8必灾|逢八必灾|逢9必乱|逢九必乱|志洪李|轮功|轮法功|三去车仑|氵去车仑|发论工|法x功|法o功|淫片|淫情|淫色|阴门|阴屄|阴唇|阴道|阴缔|阴阜|阴核|阴户|阴茎|阴精|阴茎插小穴|阴毛|阴囊|阴水|阳 具|成人dv|成人电影|成人论坛|成人小说|成人电|成人卡通|成人聊|成人片|成人视|成人图|成人文|成人小|成人色情|成人网站|成人文学|扌由插|抽一插|嫩屄|嫩逼|鸡奸|鷄巴|鸡巴|鸡巴暴胀|吸精|洗Ｂ|插逼|插阴|潮喷|肏你|肏死|屄|爆草|暴奸|爆乳|漏乳|集体淫|国产av|豪乳|黑逼|食精|美穴|美幼|密穴|蜜液|摸奶|色b|少年阿宾|骚比|肉逼|揉乳|乳爆|人兽|阳具|小xue|舔阴|性虐|阴部|扒穴|掰穴|艹你|暴奶|暴乳|暴淫|操穴|操b|操屄|操逼|大花逼|大鸡巴|大力抽送|插穴|大逼|吹 萧|潮吹|吃精|肉棒|肉 棍|喷精|噴精|骚逼|骚洞|骚妇|骚货|骚妹|骚女|骚女叫春|骚水|骚穴|肉穴|肉欲|性伙伴|性交视频|性交图片|性奴集中营|色电影|色妹妹|色情表演|色情电影|色情服务|色情图片|色情小说|色情影片|色情片|色视频|色小说|迷幻药|迷幻藥|迷昏口|迷昏药|迷昏藥|迷魂香|迷魂药|迷魂藥|迷奸粉|迷奸药|迷情粉|迷情水|迷情药|淫荡|阴蒂|色情|testoster|adrenalin|erythropo|strychnin|diamorphi|diacetylm|benzodiaz|";
//		RedisOperUtils.set(key, str);
//		return JsonResult.getSuccess(str);
//	}

}
