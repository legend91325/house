package house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Legend91325 on 2019/6/28.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeiKeHouseInfo {

    private String houseUrl;
    private String totalPrice;
    private String unitPrice;

    private String roomMainInfo;
    private String roomSubInfo;

    private String typeMainInfo;
    private String typeSubInfo;

    private String areaMainInfo;
    private String areaSubInfo;

    private String communityName;
    private String areaName;

    private String introContentBase;
    private String introContentTransaction;

    private String introContentBaseMore;

    @Override
    public String toString() {
        StringBuffer showString = new StringBuffer("贝壳房屋信息 :");
        showString.append("总价：").append(totalPrice).append("\n");
        showString.append("单价：").append(unitPrice).append("\n");
        showString.append("区域：").append(areaName).append("\n");
        showString.append("房屋/楼层：").append(roomMainInfo).append("（").append(roomSubInfo).append("）").append("\n");
        showString.append("朝向/装修：").append(typeMainInfo).append("（").append(typeSubInfo).append("）").append("\n");
        showString.append("面积/楼龄：").append(areaMainInfo).append("（").append(areaSubInfo).append("）").append("\n");
        showString.append("小区：").append(communityName).append("\n\n");
//        showString.append("房源基本信息：").append("\n").append(introContentBase).append("\n");
        showString.append("房源交易信息：").append("\n").append(introContentTransaction).append("\n");
        showString.append("房源特色：").append("\n").append(introContentBaseMore).append("\n");
        showString.append("房源地址：").append(houseUrl).append("\n");
        return showString.toString();
    }
}
