package house;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Legend91325 on 2019/6/27.
 */
public class BeiKeConstants {

    public static final String house_东城="东城";
    public static final String house_西城="西城";
    public static final String house_朝阳="朝阳";
    public static final String house_海淀="海淀";
    public static final String house_丰台="丰台";
    public static final String house_石景山="石景山";
    public static final String house_亦庄开发区="亦庄开发区";
    public static final String house_通州="通州";
    public static final String house_大兴="大兴";
    public static final String house_顺义="顺义";
    public static final String house_房山="房山";
    public static final String house_门头沟="门头沟";
    public static final String house_昌平="昌平";

    public static final String room_一室 = "l1";
    public static final String room_二室 = "l2";
    public static final String room_三室 = "l3";
    public static final String room_四室 = "l4";
    public static final String room_五室及以上 = "l5";

    public static final String feature_必看好房 = "tt9";
    public static final String feature_满五 = "mw1";
    public static final String feature_满两年 = "ty1";
    public static final String feature_近地铁 = "su1";
    public static final String feature_七日新上 = "tt2";
    public static final String feature_随时看房 = "tt4";
    public static final String feature_VR房源 = "tt8";

    public static final String 朝向_朝东 = "f1";
    public static final String 朝向_朝南= "f2";
    public static final String 朝向_朝西 = "f3";
    public static final String 朝向_朝北 = "f4";
    public static final String 朝向_南北 = "f5";



    public static final String 楼层_底楼层 = "lc1";
    public static final String 楼层_中楼层 = "lc2";
    public static final String 楼层_高楼层 = "lc3";

    public static final String 楼龄_5年以内 = "y1";
    public static final String 楼龄_10年以内 = "y2";
    public static final String 楼龄_15年以内 = "y3";
    public static final String 楼龄_20年以内 = "y4";
    public static final String 楼龄_20年以上 = "y5";

    public static final String 装修_精装修 = "de1";
    public static final String 装修_普通装修 = "de2";
    public static final String 装修_毛坯房 = "de3";

    public static final String 用途_普通住宅 = "sf1";
    public static final String 用途_商业类 = "sf2";
    public static final String 用途_别墅 = "sf3";
    public static final String 用途_四合院 = "sf4";
    public static final String 用途_车位 = "sf6";
    public static final String 用途_其他 = "sf5";

    public static final String 电梯_有电梯 = "ie2";
    public static final String 电梯_无电梯 = "ie1";


    public static final String 供暖_集体供暖 = "hy1";
    public static final String 供暖_自供暖 = "hy2";

    public static final String 房屋权属_商品房 = "dp1";
    public static final String 房屋权属_公房 = "dp2";
    public static final String 房屋权属_经济适用房 = "dp3";
    public static final String 房屋权属_其他 = "dp4";


    public static final String 房屋建筑结构_塔楼 = "bt1";
    public static final String 房屋建筑结构_板楼 = "bt2";
    public static final String 房屋建筑结构_板塔结合 = "bt3";

    public static final  Map<String,String> postionMap = new HashMap<String,String>(){{
        put("东城","dongcheng");
        put("西城","xicheng");
        put("朝阳","chaoyang");
        put("海淀","haidian");
        put("丰台","fengtai");
        put("石景山","shijingshan");
        put("亦庄开发区","yizhuangkaifaqu");
        put("通州","tongzhou");
        put("大兴","daxing");
        put("顺义","shunyi");
        put("昌平","changping");
        put("房山","fangshan");
        put("门头沟","mentougou");
    }};
}
