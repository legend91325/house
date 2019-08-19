package house;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Legend91325 on 2019/6/27.
 */
@Slf4j
public class HouseMain {
    public static void main(String[] args) throws IOException {
        BeiKeQuery beiKeQuery = BeiKeQuery.builder().build();
        //房屋挂牌价
        beiKeQuery.setStartMoney("300");
        beiKeQuery.setEndMoney("360");

        //房屋面积
        beiKeQuery.setStartArea("70");
        beiKeQuery.setEndArea("90");

        beiKeQuery.setHouseFeatures(Lists.newArrayList(BeiKeConstants.feature_满五));

        //房屋位置
        beiKeQuery.setHousePostion(BeiKeConstants.house_昌平);

        //楼层要求
        beiKeQuery.setFloors(Lists.newArrayList(BeiKeConstants.楼层_底楼层,BeiKeConstants.楼层_中楼层,BeiKeConstants.楼层_高楼层));

        //电梯要求
        beiKeQuery.setHosueDianTis(Lists.newArrayList(BeiKeConstants.电梯_有电梯,BeiKeConstants.电梯_无电梯));

        //楼龄要求
        beiKeQuery.setHouseAges(Lists.newArrayList(BeiKeConstants.楼龄_15年以内));

        //装修要求
        beiKeQuery.setHouseDecoration(Lists.newArrayList(BeiKeConstants.装修_精装修,BeiKeConstants.装修_普通装修));



        //供暖要求
        beiKeQuery.setHouseGongNuans(Lists.newArrayList(BeiKeConstants.供暖_自供暖,BeiKeConstants.供暖_集体供暖));

        //塔楼 板楼
        beiKeQuery.setHouseJieGous(Lists.newArrayList(BeiKeConstants.房屋建筑结构_塔楼,BeiKeConstants.房屋建筑结构_板楼,BeiKeConstants.房屋建筑结构_板塔结合));

        //房屋属性
        beiKeQuery.setHouseQuanShus(Lists.newArrayList(BeiKeConstants.房屋权属_商品房,BeiKeConstants.房屋权属_公房));

        beiKeQuery.setHouseUsings(Lists.newArrayList(BeiKeConstants.用途_普通住宅));

        //朝向
        beiKeQuery.setOrientations(Lists.newArrayList(BeiKeConstants.朝向_南北,BeiKeConstants.朝向_朝南));

        //几居
        beiKeQuery.setRooms(Lists.newArrayList(BeiKeConstants.room_二室,BeiKeConstants.room_三室));

        BeiKeUtil beiKeUtil = new BeiKeUtil();
        beiKeUtil.handle(beiKeQuery);

    }
}
